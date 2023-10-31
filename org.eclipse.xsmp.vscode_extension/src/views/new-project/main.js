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

import './assets/main.scss'

import { createApp } from 'vue'
import App from './App.vue'
import store from './store'

createApp(App).use(store).mount('#app')

window.addEventListener("message", (event) => {
	const msg = event.data;

	switch (msg.command) {
		case "initialLoad":
			if (msg.projectName) {
				store.commit('setProjectName', msg.projectName);
			}
			if (msg.containerDirectory) {
				store.commit('setContainerDirectory', msg.containerDirectory);
			}
			if (msg.profile) {
				store.commit('setProfile', msg.profile);

			}
			if (msg.tools) {
				store.commit('setTools', msg.tools);
			}
			break;
		case "setContainerDirectory":
			if (msg.projectDirectory) {
				store.commit('setContainerDirectory', msg.projectDirectory);
			}
			break;
		default:
			break;
	}
});