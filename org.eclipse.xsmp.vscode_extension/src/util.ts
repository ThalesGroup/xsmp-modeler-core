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

import * as fs from "fs";
import * as os from "os";
import * as path from "path";
import {
	ensureDir,
} from "fs-extra";


export async function createTemplateProject(projectName: string, dirPath: string, profile: string, tools: string[]) {
	const smdlPath = path.join(dirPath, 'smdl');
	await ensureDir(smdlPath, { mode: 0o775 });

	// Create the catalog file
	const catalogFilePath = path.join(smdlPath, `${projectName}.xsmpcat`);
	const creator = os.userInfo().username;
	const currentDate = new Date().toISOString();
	const catalogContent = `/**
 * Catalogue ${projectName}
 * 
 * @creator ${creator}
 * @date ${currentDate}
 */
catalogue ${projectName}

namespace ${projectName}
{
    
}
`;

	await fs.promises.writeFile(catalogFilePath, catalogContent);

	// Create the settings.json file
	const xsmpPath = path.join(dirPath, '.xsmp');
	await ensureDir(xsmpPath, { mode: 0o775 });

	const settingsFilePath = path.join(xsmpPath, 'settings.json');
	const settingsContent = JSON.stringify({
		build_automatically: true,
		profile,
		sources: [],
		dependencies: [],
		tools
	}, null, 2);

	await fs.promises.writeFile(settingsFilePath, settingsContent);
}

export function dirExistPromise(dirPath: string) {
	return new Promise<boolean>((resolve, reject) => {
		if (!dirPath) {
			return resolve(false);
		}
		fs.stat(dirPath, (err, stats) => {
			if (err) {
				return resolve(false);
			} else {
				if (stats.isDirectory()) {
					return resolve(true);
				}
				return resolve(false);
			}
		});
	});
}