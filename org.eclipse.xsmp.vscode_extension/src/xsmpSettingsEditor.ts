import * as vscode from 'vscode';
import * as fs from 'fs';
import { JSONEditor } from '@json-editor/json-editor';
/**
 * Provider for cat scratch editors.
 * 
 * Cat scratch editors are used for `.cscratch` files, which are just json files.
 * To get started, run this extension and open an empty `.cscratch` file in VS Code.
 * 
 * This provider demonstrates:
 * 
 * - Setting up the initial webview for a custom editor.
 * - Loading scripts and styles in a custom editor.
 * - Synchronizing changes between a text document and a custom editor.
 */
export class XsmpSettingsEditorProvider implements vscode.CustomTextEditorProvider {

    public static register(context: vscode.ExtensionContext): vscode.Disposable {
        const provider = new XsmpSettingsEditorProvider(context);
        const providerRegistration = vscode.window.registerCustomEditorProvider(XsmpSettingsEditorProvider.viewType, provider);
        return providerRegistration;
    }

    private static readonly viewType = 'xsmp.settingsEditor';

    constructor(
        private readonly context: vscode.ExtensionContext
    ) { }

    /**
     * Called when our custom editor is opened.
     * 
     * 
     */
    public async resolveCustomTextEditor(
        document: vscode.TextDocument,
        webviewPanel: vscode.WebviewPanel,
        _token: vscode.CancellationToken
    ): Promise<void> {
        // Setup initial content for the webview
        webviewPanel.webview.options = {
            enableScripts: true,
        };
        /*
                const schema = {
                    type: 'object',
                    properties: {
                        sources: {
                            type: 'array',
                            items: {
                                type: 'string'
                            }
                        },
                        dependencies: {
                            type: 'array',
                            items: {
                                type: 'string'
                            }
                        },
                        tools: {
                            type: 'array',
                            items: {
                                type: 'string'
                            }
                        },
                        profile: {
                            type: 'string'
                        },
                        'build.automatically': {
                            type: 'boolean'
                        }
                    },
                    required: ['sources', 'dependencies', 'tools', 'profile', 'build.automatically']
                };
        
                webviewPanel.webview.html = `
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="utf-8"/>
                        <title>JSONEditor with forms</title>
                    </head>
                    <body>
                      <h1>Basic JSON Editor Example</h1>
                      <div id='json-editor-container'></div>
                    </body>
                    </html>`;
                    
                    
                    webviewPanel.webview.onDidReceiveMessage((message) => {
            if (message.command === 'webviewReady') {
                // The webview is ready, so create and attach the JSON Editor
        
                const jsonEditorContainer = document.getElementById('json-editor-container');
        
                if (jsonEditorContainer) {
                    const config = {
                        // Your JSON schema and configuration options here
                    };
        
                    const jsonEditor = new JSONEditor(jsonEditorContainer, config);
        
                    // Handle editor changes as needed
                    jsonEditor.on('change', function () {
                        // Handle changes
                    });
                }
            }
        });
        
        
                const jsonEditorContainer = webviewPanel.webview.find('#json-editor-container')[0]; // Use jQuery-like syntax to find the element
        
        
                // Create the JSON Editor with the schema
                const jsonEditor = new JSONEditor(jsonEditorContainer, {
                    schema, // Pass the schema as an option
        
                    theme: 'bootstrap4'
                });
        
                // Chargez les données JSON depuis le document
                const documentText = document.getText();
                const jsonSchema = JSON.parse(documentText);
        
                // Configurez JSON Editor avec le schéma JSON
                jsonEditor.setValue(jsonSchema);
        
        
                // Enregistrez les modifications de JSON Editor
                jsonEditor.on('change', () => {
                    const updatedData = jsonEditor.getValue();
                    // Mettez à jour le fichier .xsmp/settings.json avec les données mises à jour
                    fs.writeFileSync(document.uri.fsPath, JSON.stringify(updatedData, null, 2), 'utf-8');
                });
        */

        webviewPanel.webview.html = this.getHtmlForWebview(webviewPanel.webview);

        function updateWebview() {
            webviewPanel.webview.postMessage({
                type: 'update',
                text: document.getText(),
            });
        }

        // Hook up event handlers so that we can synchronize the webview with the text document.
        //
        // The text document acts as our model, so we have to sync change in the document to our
        // editor and sync changes in the editor back to the document.
        // 
        // Remember that a single text document can also be shared between multiple custom
        // editors (this happens for example when you split a custom editor)

        const changeDocumentSubscription = vscode.workspace.onDidChangeTextDocument(e => {
            if (e.document.uri.toString() === document.uri.toString()) {
                updateWebview();
            }
        });

        // Make sure we get rid of the listener when our editor is closed.
        webviewPanel.onDidDispose(() => {
            changeDocumentSubscription.dispose();
        });



        updateWebview();
    }

    /**
     * Get the static html used for the editor webviews.
     */
    private getHtmlForWebview(webview: vscode.Webview): string {
        //document.querySelector('#get-params').textContent = JSON.stringify(params, null, 2)
        /*                use_name_attributes: false,
                        theme: 'bootstrap4',
                        disable_edit_json: true,
                        disable_properties: true,
                        disable_collapse: true,*/
        return /* html */`
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="utf-8"/>
                <title>JSONEditor with forms</title>
            </head>
            <body>
              <h1>Basic JSON Editor Example</h1>
              <div id='editor_holder'></div>
            <script src="https://cdn.jsdelivr.net/npm/@json-editor/json-editor@latest/dist/jsoneditor.min.js"></script>
            <script>
              var params = {}
              for (const [key, value] of new URLSearchParams(window.location.search).entries()) {
                params[key] = value
                console.log(key, value)
              }
              
              var config = {

                schema: {
                  "type": "object",
                  "properties": {
                    "build.automatically": {
                      "type": "boolean",
                      "title": "Build automatically",
                      "default": true,
                      "required":true
                    },
                    "profile": {
                      "type": "string",
                      "default": "xsmp-sdk",
                      "title": "Selected Profile",
                      "enum": [
                        "",
                        "xsmp-sdk",
                        "esa-cdk"
                      ]
                    },
                    "source.folders": {
                      "type": "array",
                      "title": "Source folders",
                      "uniqueItems": true,
                      "items": {
                        "type": "string"
                      }
                    },
                    "dependencies": {
                      "type": "array",
                      "title": "Dependencies",
                      "uniqueItems": true,
                      "items": {
                        "type": "string"
                      }
                    },
                    "tools": {
                      "type": "array",
                      "title": "Tools",
                      "uniqueItems": true,
                      "items": {
                        "type": "string",
                        "enum": [
                          "smp",
                          "python"
                        ]
                      }
                    }
                  },
                  "title": "Xsmp settings"
                }
              }
            
              var editor = new JSONEditor(document.querySelector('#editor_holder'), config)
            
              editor.on('change', function () {
                document.querySelector('#input').value = JSON.stringify(editor.getValue())
              })
            </script>
            </body>
            </html>`;
    }

    /**
     * Try to get a current document as json text.
     */
    private getDocumentAsJson(document: vscode.TextDocument): any {
        const text = document.getText();
        if (text.trim().length === 0) {
            return {};
        }

        try {
            return JSON.parse(text);
        } catch {
            throw new Error('Could not get document as json. Content is not valid json');
        }
    }

    /**
     * Write out the json to a given document.
     */
    private updateTextDocument(document: vscode.TextDocument, json: any) {
        const edit = new vscode.WorkspaceEdit();

        // Just replace the entire document every time for this example extension.
        // A more complete extension should compute minimal edits instead.
        edit.replace(
            document.uri,
            new vscode.Range(0, 0, document.lineCount, 0),
            JSON.stringify(json, null, 2));

        return vscode.workspace.applyEdit(edit);
    }
}