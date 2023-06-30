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
import { workspace, ExtensionContext } from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient/node';

let lc: LanguageClient;

export function activate(context: ExtensionContext) {
	let launcher = 'org.eclipse.xsmp.ide-ls.jar';
	let script = context.asAbsolutePath(path.join('target', 'language-server', launcher));

	let serverOptions: ServerOptions = {
		run: { command: 'java', args: ['-jar', script] },
		debug: { command: 'java', args: ['-jar', script], options: { env: createDebugEnv() } }
	};

	let clientOptions: LanguageClientOptions = {
		documentSelector: ['xsmpcat'],
		synchronize: {
			fileEvents: workspace.createFileSystemWatcher('**/*.*')
		}
	};

	// Create the language client and start the client.
	lc = new LanguageClient('Xtext Server', serverOptions, clientOptions);

	// enable tracing (.Off, .Messages, Verbose)
	lc.setTrace(Trace.Verbose);
	lc.start();
}

export function deactivate() {
	return lc.stop();
}

function createDebugEnv() {
	return Object.assign({
		JAVA_OPTS: "-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n,quiet=y"
	}, process.env)
}