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
import { workspace, ExtensionContext, Disposable } from 'vscode';
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
        lc = new LanguageClient('Xtext Server', serverOptions, clientOptions);

        // Enable tracing (.Off, .Messages, Verbose)
        lc.setTrace(Trace.Verbose);
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