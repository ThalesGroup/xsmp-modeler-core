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
            if (e.command === 'save') {
                let settings = JSON.parse(e.settings);
                this.save(document, settings);
            }
        });

        updateWebview();

        // Update the webview when it becomes visible
        webviewPanel.onDidChangeViewState((e) => {
            if (e.webviewPanel.visible) {
                updateWebview();
            }
        });
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
			    <title>XSMP Project</title>
			    <script type="module" crossorigin src="${scriptPath}"></script>
			</head>
			<body>
			<div id="app"></div>
			</body>
			</html>`;
    }

    private getWorkspaceFolderNames() {
        const documentUri = XsmpSettingsEditorProvider.documentUri;

        if (!documentUri) {
            vscode.window.showErrorMessage("No document is open.");
            return [];
        }

        const activeWorkspaceFolder = vscode.workspace.getWorkspaceFolder(documentUri);

        if (!activeWorkspaceFolder) {
            vscode.window.showErrorMessage("No workspace folder is open.");
            return [];
        }

        const allWorkspaceFolders = vscode.workspace.workspaceFolders || [];

        // Filter out the active workspace folder by its name
        const folderNames = allWorkspaceFolders.map(folder => folder.name);
        const activeFolderName = activeWorkspaceFolder.name;
        const filteredFolderNames = folderNames.filter(name => name !== activeFolderName);

        return filteredFolderNames;
    }

    private save(document: vscode.TextDocument, data: any) {
        return this.updateTextDocument(document, data);
    }

    /**
     * Write out the json to a given document.
     */
    private async updateTextDocument(document: vscode.TextDocument, settings: any) {
        const edit = new vscode.WorkspaceEdit();

        edit.replace(
            document.uri,
            new vscode.Range(0, 0, document.lineCount, 0),
            JSON.stringify(settings, null, 2));

        const success = await vscode.workspace.applyEdit(edit);
        if (success) {
            return document.save();
        }
    }
}