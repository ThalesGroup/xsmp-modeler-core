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
		buildAutomatically: true,
		profile: "",
		sources: [],
		dependencies: [],
		tools: [],
		availableDependencies: [],
	},
	mutations: {
		setBuildAutomatically(state, buildAutomatically) {
			state.buildAutomatically = buildAutomatically;
		},
		setProfile(state, profile) {
			state.profile = profile;
		},
		setSources(state, sources) {
			state.sources = sources;
		},
		setDependencies(state, dependencies) {
			state.dependencies = dependencies;
		},
		setTools(state, tools) {
			state.tools = tools;
		},
		addDependency(state, dependency) {
			if (!state.dependencies.includes(dependency)) {
				state.dependencies.push(dependency);
				const index = state.availableDependencies.indexOf(dependency);
				if (index !== -1) {
					state.availableDependencies.splice(index, 1);
				}
			}
		},
		removeDependency(state, index) {
			const removedDependency = state.dependencies.splice(index, 1)[0];
			state.availableDependencies.push(removedDependency);
		},
		addSourceFolder(state, folder) {
			if (!state.sources.includes(folder)) {
				state.sources.push(folder);
			}
		},
		removeSourceFolder(state, index) {
			state.sources.splice(index, 1);
		},
		setAvailableDependencies(state, dependencies) {
			state.availableDependencies = dependencies;
		}
	},
	actions: {
		saveSettings({ state }) {
			vscode.postMessage({
				command: 'save',
				settings: {
					build_automatically: state.buildAutomatically,
					profile: state.profile,
					sources: JSON.stringify(state.sources),
					dependencies: JSON.stringify(state.dependencies),
					tools: JSON.stringify(state.tools)
				}
			});
		},
		updateSettings({ commit }, settings) {
			commit('setBuildAutomatically', settings.build_automatically);
			commit('setProfile', settings.profile);
			commit('setSources', settings.sources);
			commit('setDependencies', settings.dependencies);
			commit('setTools', settings.tools);
		},
	}
})
