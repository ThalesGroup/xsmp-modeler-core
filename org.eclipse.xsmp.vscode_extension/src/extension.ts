/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
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

import { Trace } from 'vscode-jsonrpc';
import { commands, window, Uri, workspace, ExtensionContext, Disposable } from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient/node';

let lc: LanguageClient;

export function activate(context: ExtensionContext) {
    let launcher = 'org.eclipse.xsmp.ide-ls.jar';
    let script = context.asAbsolutePath(path.join('target', 'language-server', launcher));
    let defaultJavaPath = process.platform === 'win32' ? 'java.exe' : 'java';


    const startServer = () => {
        const javaPath = workspace.getConfiguration('xsmp').get<string>('javaPath') || defaultJavaPath;

        const serverOptions: ServerOptions = {
            run: { command: javaPath, args: ['-jar', script] },
            debug: { command: javaPath, args: ['-jar', script], options: { env: createDebugEnv() } },
        };

        const clientOptions: LanguageClientOptions = {
            documentSelector: ['xsmpcat'],
            synchronize: {
                fileEvents: workspace.createFileSystemWatcher('**/*.*'),
            },
        };
        // Create the language client and start the client.
        lc = new LanguageClient('Xsmp Server', serverOptions, clientOptions);

        // Enable tracing (.Off, .Messages, Verbose)
        lc.setTrace(Trace.Off);
        lc.start();

    };

    // Watch for changes in the 'xsmp.javaPath' setting
    const disposables: Disposable[] = [];
    disposables.push(
        workspace.onDidChangeConfiguration((event) => {
            if (event.affectsConfiguration('xsmp.javaPath')) {
                // The 'xsmp.javaPath' setting has changed, so stop the existing server and start a new one.
                if (lc) {
                    lc.stop().then(() => {
                        startServer();
                    });
                }
            }
        })
    );
    disposables.push(
        commands.registerCommand("xsmp.python.proxy", async () => {
            let activeEditor = window.activeTextEditor;
            if (activeEditor?.document?.languageId === 'xsmpcat' && activeEditor.document.uri instanceof Uri) {
                commands.executeCommand("xsmp.python", activeEditor.document.uri.toString());
            }
        }));
    disposables.push(
        commands.registerCommand("xsmp.smdl.proxy", async () => {
            let activeEditor = window.activeTextEditor;
            if (activeEditor?.document?.languageId === 'xsmpcat' && activeEditor.document.uri instanceof Uri) {
                commands.executeCommand("xsmp.smdl", activeEditor.document.uri.toString());
            }
        }));
    disposables.push(
        commands.registerCommand("xsmp.xsmp_sdk.proxy", async () => {
            let activeEditor = window.activeTextEditor;
            if (activeEditor?.document?.languageId === 'xsmpcat' && activeEditor.document.uri instanceof Uri) {
                commands.executeCommand("xsmp.xsmp_sdk", activeEditor.document.uri.toString());
            }
        }));
    disposables.push(
        commands.registerCommand("xsmp.esa_cdk.proxy", async () => {
            let activeEditor = window.activeTextEditor;
            if (activeEditor?.document?.languageId === 'xsmpcat' && activeEditor.document.uri instanceof Uri) {
                commands.executeCommand("xsmp.esa_cdk", activeEditor.document.uri.toString());
            }
        }));
    disposables.push(commands.registerCommand('xsmp.createNewProject', () => {
        // Prompt the user for a folder to create the files
        window.showOpenDialog({ canSelectFolders: true, canSelectFiles: false }).then((uri) => {
            if (!uri || uri.length === 0) {
                window.showErrorMessage('No folder selected. Aborting file creation.');
                return;
            }

            window.showInformationMessage('Not implemented yet.');
        });
    }));

    context.subscriptions.push(...disposables);


    // Start the server when the extension activates
    startServer();

}

export function deactivate() {
    return lc.stop();
}

function createDebugEnv() {
    return { JAVA_OPTS: "-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n,quiet=y", ...process.env }
}