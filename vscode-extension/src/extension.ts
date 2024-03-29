/*******************************************************************************
* Copyright (C) 2023-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
'use strict';
import * as path from 'path';
import * as net from 'net';
import * as os from 'os';
import { commands, window, Uri, workspace, ExtensionContext, languages} from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions, StreamInfo } from 'vscode-languageclient/node';
import * as fs from 'fs';
import * as wizard from './wizard';


let lc: LanguageClient;

async function isValidXsmpcatCommand(): Promise<boolean> {
    const activeEditor = window.activeTextEditor;

    if (activeEditor?.document?.languageId === 'xsmpcat' && activeEditor.document.uri instanceof Uri) {
        const diagnostics = languages.getDiagnostics(activeEditor.document.uri);

        if (diagnostics.length === 0) {
            return true;
        } else {
            const userResponse = await window.showInformationMessage(
                'The document contains issues. Do you want to continue the generation?',
                'Yes', 'No'
            );

            if (userResponse === 'Yes') {
                return true;
            } else {
                return false;
            }
        }
    }

    return false;
}

async function openSettingsEditor() {
    // Get the currently opened workspaceFolder (if any)
    const activeEditor = window.activeTextEditor;
    if (!activeEditor) {
        window.showErrorMessage("No project files open, project context unknown.");
        return;
    }

    const documentUri = activeEditor.document.uri;
    if (!documentUri) {
        window.showErrorMessage("No document is open.");
        return;
    }

    const activeWorkspaceFolder = workspace.getWorkspaceFolder(documentUri);
    if (!activeWorkspaceFolder) {
        window.showErrorMessage("No workspace folder is open.");
        return;
    }

    // Build the full path to the .xsmp/settings.json file
    const xsmpFolderPath = Uri.joinPath(activeWorkspaceFolder.uri, '.xsmp');
    const settingsPath = Uri.joinPath(activeWorkspaceFolder.uri, '.xsmp', 'settings.json');

    // Ensure that the .xsmp directory exists
    try {
        await fs.promises.mkdir(xsmpFolderPath.fsPath, { recursive: true });
    } catch (err) {
        window.showErrorMessage(`Error creating .xsmp directory: ${err.message}`);
        return;
    }

    try {
        await fs.promises.access(settingsPath.fsPath, fs.constants.F_OK);
    } catch (err) {
        // If the file doesn't exist, create it with default content
        const defaultSettings = {
            generate_automatically: true,
            profile: "",
            sources: [],
            dependencies: [],
            tools: []
        };
        await fs.promises.writeFile(settingsPath.fsPath, JSON.stringify(defaultSettings, null, 2), 'utf-8');
    }

    // Open the settings file
    commands.executeCommand('vscode.openWith', settingsPath, 'xsmp.settingsEditor');
}

export function activate(context: ExtensionContext) {
    let launcher = os.platform() === 'win32' ? 'org.eclipse.xsmp.ide.bat' : 'org.eclipse.xsmp.ide';
    let script = context.asAbsolutePath(path.join('dist', 'bin', launcher));

    const startServer = () => {
        let serverOptions: ServerOptions;
        const remoteServer = false;
        if (remoteServer) {
            let connectionInfo = {
                port: 5007
            };
            serverOptions = () => {
                // Connect to language server via socket
                let socket = net.connect(connectionInfo);
                let result: StreamInfo = {
                    writer: socket,
                    reader: socket
                };
                return Promise.resolve(result);
            };
        }
        else {
            const javaHome = workspace.getConfiguration('xsmp').get<string>('java.home');
            serverOptions = {
                run: {
                    command: script, options: {
                        env: {
                            ...process.env, ...(javaHome ? { JAVA_HOME: javaHome } : {})
                        }
                    }
                },
                debug: {
                    command: script, options: {
                        env: {
                            JAVA_OPTS: "-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n,quiet=y",
                            ...(javaHome ? { JAVA_HOME: javaHome } : {}), ...process.env
                        }
                    }
                }
            };
        }
        const clientOptions: LanguageClientOptions = {
            documentSelector: [{ language: 'xsmpcat' }],
            synchronize: {
                fileEvents: workspace.createFileSystemWatcher('**/*.xsmpcat'),
            },
            markdown: {
                isTrusted: true, supportHtml: true
            }
        };

        // Create the language client and start the client.
        lc = new LanguageClient('Xsmp Server', serverOptions, clientOptions);

        // Enable tracing (.Off, .Messages, Verbose)
        //lc.setTrace(Trace.Verbose);
        lc.start();

    };

    // context.subscriptions.push(XsmpSettingsEditorProvider.register(context));

    context.subscriptions.push(
        commands.registerCommand('xsmp.openSettingsEditor', openSettingsEditor)
    );

    // New project Wizard
    context.subscriptions.push(
        commands.registerCommand('xsmp.wizard', wizard.createProjectWizard)
    );

    context.subscriptions.push(
        workspace.onDidChangeConfiguration((event) => {
            if (event.affectsConfiguration('xsmp.java.home')) {
                // The 'xsmp.java.home' setting has changed, so stop the existing server and start a new one.
                if (lc) {
                    lc.stop().then(() => {
                        startServer();
                    });
                }
            }

        })
    );
    context.subscriptions.push(
        workspace.createFileSystemWatcher('**/.xsmp/settings.json').onDidChange(() => {
            lc.sendNotification(`workspace/didChangeConfiguration`, undefined)
        })
    );

    context.subscriptions.push(
        commands.registerCommand("xsmp.generate.proxy", async () => {
            if (await isValidXsmpcatCommand()) {
                commands.executeCommand("xsmp.generate", window.activeTextEditor.document.uri.toString());
            }
        }));

    context.subscriptions.push(
        commands.registerCommand("xsmp.import.smpcat.proxy", async () => {
            // Get the active text editor
            let activeEditor = window.activeTextEditor;

            // Check if the active editor has the languageId "xsmpcat" and a valid URI
            if (activeEditor?.document?.uri instanceof Uri) {
                // Check if the file extension is ".smpcat"
                const filePath = activeEditor.document.uri.fsPath;
                if (filePath.endsWith(".smpcat")) {
                    // Execute the "xsmp.smp.import" command with the document's URI
                    commands.executeCommand("xsmp.import.smpcat", activeEditor.document.uri.toString());
                } else {
                    // Show a message that the command can only be executed for files with the ".smpcat" extension
                    window.showInformationMessage("This command can only be executed for files with the '.smpcat' extension.");
                }
            }
        })
    );

    // Start the server when the extension activates
    startServer();

}

export function deactivate() {
    return lc.stop();
}

