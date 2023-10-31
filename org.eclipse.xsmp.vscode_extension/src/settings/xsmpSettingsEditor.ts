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

import * as vscode from 'vscode';

export class XsmpSettingsEditorProvider implements vscode.CustomTextEditorProvider {

	public static register(context: vscode.ExtensionContext): vscode.Disposable {
		const provider = new XsmpSettingsEditorProvider(context);
		const providerRegistration = vscode.window.registerCustomEditorProvider(XsmpSettingsEditorProvider.viewType, provider);
		return providerRegistration;
	}

	private static readonly viewType = 'xsmp.settingsEditor';

	public static documentUri = null;

	constructor(
		private readonly context: vscode.ExtensionContext
	) { }

	/**
	 * Called when our custom editor is opened.
	 */
	public async resolveCustomTextEditor(
		document: vscode.TextDocument,
		webviewPanel: vscode.WebviewPanel,
		_token: vscode.CancellationToken
	): Promise<void> {
		XsmpSettingsEditorProvider.documentUri = document.uri;

		// Setup initial content for the webview
		webviewPanel.webview.options = {
			enableScripts: true,
		};

		webviewPanel.webview.html = this.getHtmlForWebview(webviewPanel.webview);

		// Load available dependencies
		webviewPanel.webview.postMessage({
			command: "setAvailableDependencies",
			dependencies: this.getWorkspaceFolderNames(),
		})

		function updateWebview() {
			webviewPanel.webview.postMessage({
				command: 'update',
				settings: document.getText(),
			});
		}

		// Receive message from the webview.
		webviewPanel.webview.onDidReceiveMessage(e => {
			switch (e.command) {
				case 'save':
					this.save(document, e.settings);
					break;
			}
		});

		updateWebview();
	}

	/**
	 * Get the static html used for the editor webviews.
	 */
	private getHtmlForWebview(webview: vscode.Webview): string {
		const scriptPath = webview.asWebviewUri(vscode.Uri.joinPath(
			this.context.extensionUri, 'dist', 'views', 'settings-bundle.js'));

		return `
			<!DOCTYPE html>
			<html lang="en">
			<head>
			    <meta charset="UTF-8">
			    <meta name="viewport" content="width=device-width, initial-scale=1.0">
			    <title>XSMP-SDK Project</title>
			    <script type="module" crossorigin src="${scriptPath}"></script>
			</head>
			<body>
			<div id="app"></div>
			</body>
			</html>`;
	}

	private getWorkspaceFolderNames() {
		return (vscode.workspace.workspaceFolders?.map(folder => folder.name) || []);
	}

	private save(document: vscode.TextDocument, data: any) {
		return this.updateTextDocument(document, data);
	}

	/**
	 * Write out the json to a given document.
	 */
	private updateTextDocument(document: vscode.TextDocument, jsonString: any) {
		const edit = new vscode.WorkspaceEdit();

		const json = {
			build_automatically: jsonString.build_automatically,
			profile: jsonString.profile,
			sources: JSON.parse(jsonString.sources),
			dependencies: JSON.parse(jsonString.dependencies),
			tools: JSON.parse(jsonString.tools)
		}

		// Just replace the entire document every time for this example extension.
		// A more complete extension should compute minimal edits instead.
		edit.replace(
			document.uri,
			new vscode.Range(0, 0, document.lineCount, 0),
			JSON.stringify(json, null, 2));

		return vscode.workspace.applyEdit(edit);
	}
}