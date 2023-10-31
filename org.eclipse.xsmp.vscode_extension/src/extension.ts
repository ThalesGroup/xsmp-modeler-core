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
import * as net from 'net';
import { Trace } from 'vscode-jsonrpc';
import { commands, window, Uri, workspace, ExtensionContext, Disposable, languages, TextDocument, WebviewPanel, CancellationToken, ProgressLocation, Progress } from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions, StreamInfo } from 'vscode-languageclient/node';
import * as fs from 'fs';

import { XsmpSettingsEditorProvider } from './settings/xsmpSettingsEditor';
import { NewProjectPanel } from './newProject/newProjectPanel';

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
			build_automatically: true,
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
	let launcher = 'org.eclipse.xsmp.ide-lsp.jar';
	let script = context.asAbsolutePath(path.join('target', 'language-server', launcher));
	let defaultJavaPath = process.platform === 'win32' ? 'java.exe' : 'java';

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
			const javaPath = workspace.getConfiguration('xsmp').get<string>('javaPath') || defaultJavaPath;
			serverOptions = {
				run: { command: javaPath, args: ['-jar', script,/* '-log', '-trace'*/], },
				debug: { command: javaPath, args: ['-jar', script, '-log', '-trace'], options: { env: createDebugEnv() } },
			};
		}
		const clientOptions: LanguageClientOptions = {
			documentSelector: [{ language: 'xsmpcat' }],
			synchronize: {
				fileEvents: workspace.createFileSystemWatcher('**/*.xsmpcat'),
			},
		};
		// Create the language client and start the client.
		lc = new LanguageClient('Xsmp Server', serverOptions, clientOptions);


		// Enable tracing (.Off, .Messages, Verbose)
		lc.setTrace(Trace.Verbose);
		lc.start();

	};

	context.subscriptions.push(XsmpSettingsEditorProvider.register(context));

	context.subscriptions.push(
		commands.registerCommand('xsmp.openSettingsEditor', openSettingsEditor)
	);

	// New project Wizard
	context.subscriptions.push(
		commands.registerCommand('xsmp.wizard.xsmp-sdk', () => {
			window.withProgress(
				{
					cancellable: false,
					location: ProgressLocation.Notification,
					title: "XSMP-SDK: New Project"
				},
				async (
					progress: Progress<{ increment: number; message: string }>,
					cancelToken: CancellationToken
				) => {
					try {
						NewProjectPanel.createOrShow(context.extensionPath);
					} catch (error) {
						// Mettre des logs
					}
				}
			)
		})
	);

	context.subscriptions.push(
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
	context.subscriptions.push(
		workspace.createFileSystemWatcher('**/.xsmp/settings.json').onDidChange(() => {
			lc.sendNotification(`workspace/didChangeConfiguration`, undefined)
		})
	);

	context.subscriptions.push(
		commands.registerCommand("xsmp.generator.python.proxy", async () => {
			if (await isValidXsmpcatCommand()) {
				commands.executeCommand("xsmp.generator.python", window.activeTextEditor.document.uri.toString());
			}
		}));
	context.subscriptions.push(
		commands.registerCommand("xsmp.generator.smp.proxy", async () => {
			if (await isValidXsmpcatCommand()) {
				commands.executeCommand("xsmp.generator.smp", window.activeTextEditor.document.uri.toString());
			}
		}));
	context.subscriptions.push(
		commands.registerCommand("xsmp.generator.xsmp-sdk.proxy", async () => {
			if (await isValidXsmpcatCommand()) {
				commands.executeCommand("xsmp.generator.xsmp-sdk", window.activeTextEditor.document.uri.toString());
			}
		}));
	context.subscriptions.push(
		commands.registerCommand("xsmp.generator.esa-cdk.proxy", async () => {
			if (await isValidXsmpcatCommand()) {
				commands.executeCommand("xsmp.generator.esa-cdk", window.activeTextEditor.document.uri.toString());
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
	context.subscriptions.push(commands.registerCommand('xsmp.createNewProject', () => {
		// Prompt the user for a folder to create the files
		window.showOpenDialog({ canSelectFolders: true, canSelectFiles: false }).then((uri) => {
			if (!uri || uri.length === 0) {
				window.showErrorMessage('No folder selected. Aborting file creation.');
				return;
			}

			window.showInformationMessage('Not implemented yet.');
		});
	}));



	// Start the server when the extension activates
	startServer();

}

export function deactivate() {
	return lc.stop();
}

function createDebugEnv() {
	return { JAVA_OPTS: "-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n,quiet=y", ...process.env }
}