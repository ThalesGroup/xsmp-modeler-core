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
        generateAutomatically: true,
        profile: "",
        sources: [],
        dependencies: [],
        tools: [],
        availableDependencies: [],
    },
    mutations: {
        setGenerateAutomatically(state, generateAutomatically) {
            state.generateAutomatically = generateAutomatically;
        },
        setProfile(state, profile) {
            state.profile = profile;
        },
        setSources(state, sources) {
            state.sources = sources;
        },
        setDependencies(state, dependencies) {
            for (const dependency of dependencies) {
                this.commit("addDependency", dependency);
            }
        },
        setTools(state, tools) {
            state.tools = tools;
        },
        addDependency(state, dependency) {
            if (dependency && dependency !== "" && !state.dependencies.includes(dependency)) {
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
            if (folder && folder !== "" && !state.sources.includes(folder)) {
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
            const settings = {
                generate_automatically: state.generateAutomatically,
                profile: state.profile === "<no-profile>" ? "" : state.profile,
                sources: state.sources,
                dependencies: state.dependencies,
                tools: state.tools
            };

            vscode.postMessage({
                command: 'save',
                settings: JSON.stringify(settings)
            });
        },
        updateSettings({ commit }, settings) {
            commit('setGenerateAutomatically', settings.generate_automatically);
            commit('setProfile', settings.profile === "" ? "<no-profile>" : settings.profile);
            commit('setSources', settings.sources);
            commit('setDependencies', settings.dependencies);
            commit('setTools', settings.tools);
        },
    }
})
