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
import { commands, window, Uri, workspace, ExtensionContext, languages, TextDocument } from 'vscode';
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

export function activate(context: ExtensionContext) {
    let launcher = os.platform() === 'win32' ? 'org.eclipse.xsmp.server.bat' : 'org.eclipse.xsmp.server';
    let script = context.asAbsolutePath(path.join('dist', 'bin', launcher));

    const startServer = () => {
        let serverOptions: ServerOptions;
        const remoteServer = workspace.getConfiguration('xsmp').get<boolean>('remote') || false;
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
            documentSelector: [{ language: 'xsmpcat' }, { language: 'xsmpproject' }],
            synchronize: {
                fileEvents: [workspace.createFileSystemWatcher('**/*.xsmpcat'), workspace.createFileSystemWatcher('**/xsmp.project')],
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
        workspace.createFileSystemWatcher('**/xsmp.project').onDidChange(() => {
            lc.sendNotification(`workspace/didChangeConfiguration`, undefined)
        }),
        workspace.createFileSystemWatcher('**/xsmp.project').onDidCreate(() => {
            lc.sendNotification(`workspace/didChangeConfiguration`, undefined)
        }),
        workspace.createFileSystemWatcher('**/xsmp.project').onDidDelete(() => {
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
    let disposable = workspace.onDidOpenTextDocument((document: TextDocument) => {
        if (document.languageId === 'xsmpcat') {
            const folder = workspace.getWorkspaceFolder(document.uri);
            if (folder) {
                const workspacePath = folder.uri.fsPath;
                const currentDirectory = path.dirname(document.fileName);

                if (!findProjectFile(currentDirectory, workspacePath)) {
                    createXsmpProjectFile(workspacePath, currentDirectory)
                }
            }
        }
    });

    context.subscriptions.push(disposable);

    // Start the server when the extension activates
    startServer();

}
async function createXsmpProjectFile(workspacePath: string, directory: string) {
    const createProject = await window.showQuickPick(
        [{ label: "Yes", isTrue: true }, { label: "No", isTrue: false }],
        { placeHolder: "XSMP Project configuration file doesn't exist. Do you want to create it ?" }
    );
    if (!createProject || !createProject.isTrue)
        return;

    const projectDirectory = await window.showQuickPick(getPossibleParentDirectories(directory, workspacePath),
        { placeHolder: "Select project root directory:" })

    if (!projectDirectory)
        return;

    const projectName = path.basename(projectDirectory)
    const defaultContent = `
/** ${projectName} project description */
project "${projectName}"


// project relative path(s) containing modeling file(s)
source "smdl"


// Uncomment one of the available profiles
//profile "org.eclipse.xsmp.profile.xsmp-sdk"
//profile "org.eclipse.xsmp.profile.esa-cdk"


// Comment/Uncomment the tools you would like to use
//tool "org.eclipse.xsmp.tool.smp"
//tool "org.eclipse.xsmp.tool.python"


// If your project needs types from outside sources,
// you can include them by adding project dependencies.
// For example: dependency "otherProject"
//              dependency "otherProject2"
`;
    fs.writeFileSync(path.join(projectDirectory, 'xsmp.project'), defaultContent);
    window.showInformationMessage("The xsmp.project file has been created successfully.");

    // Open the newly created xsmp.project file
    workspace.openTextDocument(path.join(projectDirectory, 'xsmp.project')).then((doc) => {
        window.showTextDocument(doc);
    });
}

function findProjectFile(directory: string, workspacePath: string): string | undefined {

    while (directory !== workspacePath) {
        const files = fs.readdirSync(directory);
        if (files.includes('xsmp.project')) {
            return path.join(directory, 'xsmp.project');
        }
        directory = path.dirname(directory);
    }
    const files = fs.readdirSync(directory);
    if (files.includes('xsmp.project')) {
        return path.join(directory, 'xsmp.project');
    }
    return undefined;
}

function getPossibleParentDirectories(directory: string, workspacePath: string): string[] {
    const possibleParentDirectories: string[] = [];
    while (directory !== workspacePath) {
        possibleParentDirectories.push(directory);
        directory = path.dirname(directory);
    }
    possibleParentDirectories.push(workspacePath);
    return possibleParentDirectories;
}

export function deactivate() {
    return lc.stop();
}

