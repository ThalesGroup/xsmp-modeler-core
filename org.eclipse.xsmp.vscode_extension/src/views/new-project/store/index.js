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

import { createStore } from 'vuex'

let vscode;
try {
	vscode = acquireVsCodeApi();
} catch (error) {
	console.error(error);
}

export default createStore({
	state: {
		projectName: "",
		containerDirectory: "",
		profile: "",
		tools: []
	},
	mutations: {
		setProjectName(state, projectName) {
			state.projectName = projectName;
		},
		setContainerDirectory(state, containerDir) {
			state.containerDirectory = containerDir;
		},
		setProfile(state, profile) {
			state.profile = profile;
		},
		setTools(state, tools) {
			state.tools = tools;
		},
	},
	actions: {
		requestInitialValues() {
			vscode.postMessage({
				command: "requestInitValues",
			});
		},
		createProject({ state }) {
			vscode.postMessage({
				command: "createProject",
				projectName: state.projectName,
				containerFolder: state.containerDirectory,
				profile: state.profile,
				tools: JSON.stringify(state.tools)
			})
		},
		openProjectDirectory() {
			vscode.postMessage({
				command: "openProjectDirectory",
			});
		},
	}
})