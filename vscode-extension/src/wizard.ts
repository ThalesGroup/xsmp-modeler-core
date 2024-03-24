/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/

'use strict';

import * as vscode from 'vscode';
import * as path from 'path';
import * as fs from 'fs';
import * as os from "os";


export async function createProjectWizard() {

    const destinationFolders = await vscode.window.showOpenDialog({
        canSelectFiles: false,
        canSelectFolders: true,
        canSelectMany: false,
        openLabel: "Select project destination folder"
    });

    if (!destinationFolders || destinationFolders.length === 0)
        return;

    const destinationFolder = destinationFolders[0].fsPath;

    // Project name input
    let projectName: string;
    while (true) {
        projectName = await vscode.window.showInputBox({
            prompt: "Enter project name"
        });

        if (/^[a-zA-Z]\w*$/.test(projectName)) {
            break;
        } else {
            vscode.window.showErrorMessage("Project name must follow the format [a-zA-z]\\w*");
        }
    }

    // Select profile
    const profiles = [
        { label: "xsmp-sdk", description: "Xsmp SDK" },
        { label: "esa-cdk", description: "ESA CDK" }
    ];

    const profile = await vscode.window.showQuickPick(profiles, {
        placeHolder: "Select a profile"
    });

    if (!profile)
        return; // If user cancels selection

    // Select tools
    const tools = [
        { label: "smp", description: "SMP tool", "picked": true },
        { label: "python", description: "Python generator", "picked": profile.label === "xsmp-sdk" }
    ];


    const selectedTools = await vscode.window.showQuickPick(tools, {
        canPickMany: true,
        placeHolder: "Select tools to enable"
    });

    if (!selectedTools)
        return; // If user cancels selection

    // Create specific files
    const projectFolderPath = path.join(destinationFolder, projectName);

    createTemplateProject(projectName, projectFolderPath, profile.label, selectedTools.map(tool => tool.label))

    // Add project to workspace
    const uri = vscode.Uri.file(projectFolderPath);
    vscode.workspace.updateWorkspaceFolders(vscode.workspace.workspaceFolders ? vscode.workspace.workspaceFolders.length : 0, null, { uri });
    
    const xsmpcatFilePath = path.join(projectFolderPath, "smdl", `${projectName}.xsmpcat`);
    const xsmpcatDocument = await vscode.workspace.openTextDocument(xsmpcatFilePath);
    await vscode.window.showTextDocument(xsmpcatDocument);
}


async function createTemplateProject(projectName: string, dirPath: string, profile: string, tools: string[]) {
    fs.mkdirSync(dirPath); // Create project directory

    const smdlPath = path.join(dirPath, 'smdl');
    fs.mkdirSync(smdlPath);

    // Create the catalog file
    const creator = os.userInfo().username;
    const currentDate = new Date().toISOString();

    await fs.promises.writeFile(path.join(smdlPath, `${projectName}.xsmpcat`), `// Copyright \${year} \${user}. All rights reserved.
//
// YOUR NOTICE
//
// Generation date:  \${date} \${time}
                
/**
 * Catalogue ${projectName}
 * 
 * @creator ${creator}
 * @date ${currentDate}
 */
catalogue ${projectName}

namespace ${projectName}
{
    
} // namespace ${projectName}
`);


    fs.mkdirSync(path.join(dirPath, 'src'));
    fs.mkdirSync(path.join(dirPath, 'src-gen'));

    if (profile === 'xsmp-sdk') {
        await fs.promises.writeFile(path.join(dirPath, 'CMakeLists.txt'), `
cmake_minimum_required(VERSION 3.14)

project(
    ${projectName}
#   VERSION 1.0.0
#   DESCRIPTION ""
#   HOMEPAGE_URL ""
    LANGUAGES CXX)

find_package(xsmp-sdk QUIET)
if(NOT xsmp-sdk_FOUND ) 
    message(STATUS "xsmp-sdk is not installed, downloading it.")
    include(FetchContent)
    FetchContent_Declare(
        xsmp-sdk
        GIT_REPOSITORY https://github.com/ThalesGroup/xsmp-sdk.git
        GIT_TAG        main # replace with a specific tag
    )
    FetchContent_MakeAvailable(xsmp-sdk)
    list(APPEND CMAKE_MODULE_PATH "\${xsmp-sdk_SOURCE_DIR}/cmake")
endif()

file(GLOB_RECURSE SRC CONFIGURE_DEPENDS src/*.cpp src-gen/*.cpp)

add_library(${projectName} SHARED \${SRC})
target_include_directories(${projectName} PUBLIC src src-gen)
target_link_libraries(${projectName} PUBLIC Xsmp::Cdk)


# --------------------------------------------------------------------

if(CMAKE_PROJECT_NAME STREQUAL PROJECT_NAME)
    include(CTest)
    include(Pytest)
    pytest_discover_tests()
endif()


`);
        await fs.promises.writeFile(path.join(dirPath, 'README.md'), `
# ${projectName}

Project description.

## System Requirements

Check [xsmp-sdk system requirements](https://thalesgroup.github.io/xsmp-sdk/requirements.html).

## How to Build

\`\`\`bash
cmake -B ./build -DCMAKE_BUILD_TYPE=Release
cmake --build ./build --config Release
\`\`\`

## How to Test

\`\`\`bash
cd build && ctest -C Release --output-on-failure
\`\`\`
                
`);
        if (tools.includes("python")) {
            let py_path = path.join(dirPath, 'python')
            fs.mkdirSync(py_path);
            await fs.promises.writeFile(path.join(py_path, `pytest.ini`), `
# pytest.ini
[pytest]
pythonpath = .`);

            let py_projectPath = path.join(py_path, projectName)
            fs.mkdirSync(py_projectPath);
            await fs.promises.writeFile(path.join(py_projectPath, `test_${projectName}.py`), `
import ecss_smp
import xsmp
import ${projectName}

class Test${projectName}(xsmp.unittest.TestCase):
    try:
        sim: ${projectName}._test_${projectName}.Simulator
    except AttributeError:
        pass
    
    def loadAssembly(self, sim: ecss_smp.Smp.ISimulator):
        sim.LoadLibrary("${projectName}")
        #TODO create instances, configuration, connections, ...

    def test_${projectName}(self):
        # TODO write unit-test
        pass`);
        }
    }
    else if (profile === 'esa-cdk') {
        await fs.promises.writeFile(path.join(dirPath, 'CMakeLists.txt'), `
file(GLOB_RECURSE SRC CONFIGURE_DEPENDS src/*.cpp src-gen/*.cpp)

simulus_library(
    MAIN
    SOURCES
        \${SRC}
    LIBRARIES
        esa.ecss.smp.cdk
)
target_include_directories(cdk PUBLIC src src-gen)
`);
    }

    // Create the settings.json file
    const xsmpPath = path.join(dirPath, '.xsmp');
    fs.mkdirSync(xsmpPath);

    const settingsFilePath = path.join(xsmpPath, 'settings.json');
    const settingsContent = JSON.stringify({
        generate_automatically: true,
        profile,
        sources: ["smdl"],
        dependencies: [],
        tools
    }, null, 2);

    await fs.promises.writeFile(settingsFilePath, settingsContent);
}
