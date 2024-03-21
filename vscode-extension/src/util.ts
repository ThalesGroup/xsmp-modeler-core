/*******************************************************************************
* Copyright (C) 2023-2024 THALES ALENIA SPACE FRANCE.
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


    await ensureDir(path.join(dirPath, 'src'));
    await ensureDir(path.join(dirPath, 'src-gen'));

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
            await ensureDir(py_path, { mode: 0o775 });
            await fs.promises.writeFile(path.join(py_path, `pytest.ini`), `
# pytest.ini
[pytest]
pythonpath = .`);

            let py_projectPath = path.join(py_path, projectName)
            await ensureDir(py_projectPath, { mode: 0o775 });
            await fs.promises.writeFile(path.join(py_projectPath,`test_${projectName}.py`), `
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
    await ensureDir(xsmpPath, { mode: 0o775 });

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

export function dirExistPromise(dirPath: string) {
    return new Promise<boolean>((resolve) => {
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