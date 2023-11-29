/*******************************************************************************
 * Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.profile.xsmp_sdk.ui.wizard

import java.time.Instant
import java.time.Year
import java.time.temporal.ChronoUnit
import java.util.Random
import org.eclipse.core.runtime.Status
import org.eclipse.xsmp.ui.wizard.XsmpcatProjectFactory
import org.eclipse.xtext.ui.wizard.template.IProjectGenerator
import org.eclipse.xtext.ui.wizard.template.IProjectTemplateProvider
import org.eclipse.xtext.ui.wizard.template.ProjectTemplate

import static org.eclipse.core.runtime.IStatus.*

/**
 * Create a list with all project templates to be shown in the template new project wizard.
 * 
 * Each template is able to generate one or more projects. Each project can be configured such that any number of files are included.
 */
class XsmpSdkProjectTemplateProvider implements IProjectTemplateProvider {
    override getProjectTemplates() {
        #[new XsmpProject()]
    }
}

@ProjectTemplate(label="Xsmp SDK Project", icon="project_template.png", description="<p><b>Create a new Xsmp SDK Project</b></p>")
final class XsmpProject {
    val advancedGroup = group("Properties")
    val cName = "<<catalogue_name>>"
    val name = text("Catalogue Name:", cName, "The Catalogue name", advancedGroup)
    Random random = new Random()

    override protected updateVariables() {
        if (name.value == cName)
            name.value = projectInfo.projectName.replaceAll("\\.","_")
        super.updateVariables()
    }

    override protected validate() {
        if (!name.value.matches('[a-zA-Z][A-Za-z0-9_]*'))
            new Status(ERROR, "Wizard", "'" + name + "' is not a valid document name")
        else
            null
    }

    override generateProjects(IProjectGenerator generator) {
        updateVariables()

        generator.generate(new XsmpcatProjectFactory() => [
            projectName = projectInfo.projectName
            location = projectInfo.locationPath

            projectNatures += "org.python.pydev.pythonNature"
            projectNatures += "de.marw.cmake4eclipse.mbs.cmake4eclipsenature"
            builderIds += "de.marw.cmake4eclipse.mbs.genscriptbuilder"

            profile = "org.eclipse.xsmp.profile.xsmp_sdk"
            tools += "org.eclipse.xsmp.tool.python"
            defaultOutput = "_build/smdl"

            addFile('''smdl/«name».xsmpcat''', '''
                // Copyright «Year.now.value» YOUR ORGANIZATION. All rights reserved.
                //
                // YOUR NOTICE
                // 
                
                /**
                 * Catalogue «name»
                 * 
                 * @creator «System.getProperty("user.name")»
                 * @date «Instant.now().truncatedTo(ChronoUnit.SECONDS)»
                 */
                catalogue «name»
                
                namespace «name»
                {
                    
                } // namespace «name»
            ''')

            addFile('''CMakeLists.txt''', '''
                cmake_minimum_required(VERSION 3.14)
                
                project(
                    «name»
                #   VERSION 1.0.0
                #   DESCRIPTION ""
                #   HOMEPAGE_URL ""
                    LANGUAGES CXX)
                
                include(FetchContent)
                FetchContent_Declare(
                    xsmp-sdk
                    GIT_REPOSITORY https://github.com/ThalesGroup/xsmp-sdk.git
                    GIT_TAG        main # replace with a specific tag
                )
                
                FetchContent_MakeAvailable(xsmp-sdk)
                set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${xsmp-sdk_SOURCE_DIR}/cmake)
                
                file(GLOB_RECURSE SRC CONFIGURE_DEPENDS src/*.cpp src-gen/*.cpp)
                
                add_library(«name» SHARED ${SRC})
                target_include_directories(«name» PUBLIC src src-gen)
                target_link_libraries(«name» PUBLIC Xsmp::Cdk)
                
                
                # --------------------------------------------------------------------
                
                if(CMAKE_PROJECT_NAME STREQUAL PROJECT_NAME)
                    include(CTest)
                    include(Pytest)
                    pytest_discover_tests(«name»Test)
                endif()
                
                
            ''')

            addFile('''python/«name»/test_«name».py''', '''
                import ecss_smp
                import xsmp
                import «name»
                
                class «name»Test(xsmp.unittest.TestCase):
                    try:
                        sim: «name»._test_«name».Simulator
                    except AttributeError:
                        pass
                    
                    def loadAssembly(self, sim: ecss_smp.Smp.ISimulator):
                        sim.LoadLibrary("«name»")
                        #TODO create instances, configuration, connections, ...
                
                    def test_«name»(self):
                        # TODO write unit-test
                        pass
            ''')

            addFile('''.gitignore''', '''
                /_build/
            ''')
            addFile('''.gitattributes''', '''
                * text=auto eol=lf
            ''')

            addFile("README.md", '''
                # «name»
                
                Project description.
                
                ## System Requirements
                
                - Linux, Windows or MacOS
                - A C++ 17 compiler: Clang 5+, GCC 7+ and MSVC 2019+ are officially supported
                - CMake 3.14+ (3.20+ for Python tests)
                - Python 3.7+ and pytest
                
                ## Plugins for Eclipse integration
                
                Install from Marketplace:
                
                - Eclipse C/C++ IDE CDT
                - CMake4Eclipse
                - PyDev
                
                ## How to Build with CMake
                
                ```bash
                cmake -B ./build -DCMAKE_BUILD_TYPE=Release
                cmake --build ./build --config Release
                ```
                
            ''')

            addFile(".pydevproject", '''
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <?eclipse-pydev version="1.0"?><pydev_project>
                    <pydev_property name="org.python.pydev.PYTHON_PROJECT_INTERPRETER">Default</pydev_property>
                    <pydev_property name="org.python.pydev.PYTHON_PROJECT_VERSION">python interpreter</pydev_property>
                    <pydev_pathproperty name="org.python.pydev.PROJECT_SOURCE_PATH">
                        <path>/${PROJECT_DIR_NAME}/python</path>
                        <path>/${PROJECT_DIR_NAME}/_build/Debug/lib</path>
                        <path>/${PROJECT_DIR_NAME}/_build/Debug/_deps/xsmp-sdk-src/python</path>
                    </pydev_pathproperty>
                </pydev_project>
            ''')

            val debugConfId = Math.abs(random.nextInt())
            val releaseConfId = Math.abs(random.nextInt())

            addFile('''.cproject''', '''
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <?fileVersion 4.0.0?><cproject storage_type_id="org.eclipse.cdt.core.XmlProjectDescriptionStorage">
                    <storageModule cmakelistsFolder="" moduleId="de.marw.cmake4eclipse.mbs.settings">
                        <targets>
                            <target name="all"/>
                            <target name="clean"/>
                            <target name="test"/>
                        </targets>
                    </storageModule>
                    <storageModule moduleId="org.eclipse.cdt.core.settings">
                        <cconfiguration id="cmake4eclipse.mbs.config.debug.«debugConfId»">
                            <storageModule buildSystemId="de.marw.cmake4eclipse.mbs.cmake4eclipse" id="cmake4eclipse.mbs.config.debug.«debugConfId»" moduleId="org.eclipse.cdt.core.settings" name="Debug">
                                <externalSettings/>
                                <extensions>
                                    <extension id="org.eclipse.cdt.core.PE64" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.GNU_ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.GmakeErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.GLDErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.GCCErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                </extensions>
                            </storageModule>
                            <storageModule moduleId="cdtBuildSystem" version="4.0.0">
                                <configuration buildArtefactType="cmake4eclipse.mbs.buildArtefactType.cmake" buildProperties="org.eclipse.cdt.build.core.buildArtefactType=cmake4eclipse.mbs.buildArtefactType.cmake,org.eclipse.cdt.build.core.buildType=org.eclipse.cdt.build.core.buildType.debug" description="" id="cmake4eclipse.mbs.config.debug.«debugConfId»" name="Debug" optionalBuildProperties="" parent="cmake4eclipse.mbs.config.debug">
                                    <folderInfo id="cmake4eclipse.mbs.config.debug.«debugConfId»." name="/" resourcePath="">
                                        <toolChain id="cmake4eclipse.mbs.config.debug.toolChain.«Math.abs(random.nextInt())»" name="CMake driven" superClass="cmake4eclipse.mbs.config.debug.toolChain">
                                            <targetPlatform id="cmake4eclipse.mbs.targetPlatform.cmake.«Math.abs(random.nextInt())»" name="Any Platform" superClass="cmake4eclipse.mbs.targetPlatform.cmake"/>
                                            <builder autoBuildTarget="all" buildPath="/«name»/_build/Debug" cleanBuildTarget="clean" enableAutoBuild="false" enableCleanBuild="true" enabledIncrementalBuild="true" id="cmake4eclipse.mbs.builder.«Math.abs(random.nextInt())»" incrementalBuildTarget="all" keepEnvironmentInBuildfile="false" managedBuildOn="true" name="CMake Builder" parallelBuildOn="true" parallelizationNumber="optimal" superClass="cmake4eclipse.mbs.builder"/>
                                            <tool id="cmake4eclipse.mbs.toolchain.tool.dummy.«Math.abs(random.nextInt())»" name="CMake" superClass="cmake4eclipse.mbs.toolchain.tool.dummy">
                                                <inputType id="cmake4eclipse.mbs.inputType.c.«Math.abs(random.nextInt())»" superClass="cmake4eclipse.mbs.inputType.c"/>
                                                <inputType id="cmake4eclipse.mbs.inputType.cpp.«Math.abs(random.nextInt())»" superClass="cmake4eclipse.mbs.inputType.cpp"/>
                                            </tool>
                                        </toolChain>
                                    </folderInfo>
                                </configuration>
                            </storageModule>
                            <storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
                            <storageModule buildDir="_build/${ConfigName}" dirtyTs="1693321018059" moduleId="de.marw.cmake4eclipse.mbs.settings">
                                <options/>
                            </storageModule>
                        </cconfiguration>
                        <cconfiguration id="cmake4eclipse.mbs.config.release.«releaseConfId»">
                            <storageModule buildSystemId="de.marw.cmake4eclipse.mbs.cmake4eclipse" id="cmake4eclipse.mbs.config.release.«releaseConfId»" moduleId="org.eclipse.cdt.core.settings" name="Release">
                                <externalSettings/>
                                <extensions>
                                    <extension id="org.eclipse.cdt.core.PE64" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.GNU_ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.GmakeErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.GLDErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.GCCErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                </extensions>
                            </storageModule>
                            <storageModule moduleId="cdtBuildSystem" version="4.0.0">
                                <configuration buildArtefactType="cmake4eclipse.mbs.buildArtefactType.cmake" buildProperties="org.eclipse.cdt.build.core.buildArtefactType=cmake4eclipse.mbs.buildArtefactType.cmake,org.eclipse.cdt.build.core.buildType=org.eclipse.cdt.build.core.buildType.release" description="" id="cmake4eclipse.mbs.config.release.«releaseConfId»" name="Release" optionalBuildProperties="" parent="cmake4eclipse.mbs.config.release">
                                    <folderInfo id="cmake4eclipse.mbs.config.release.«releaseConfId»." name="/" resourcePath="">
                                        <toolChain id="cmake4eclipse.mbs.config.release.toolChain.«Math.abs(random.nextInt())»" name="CMake driven" superClass="cmake4eclipse.mbs.config.release.toolChain">
                                            <targetPlatform id="cmake4eclipse.mbs.targetPlatform.cmake.«Math.abs(random.nextInt())»" name="Any Platform" superClass="cmake4eclipse.mbs.targetPlatform.cmake"/>
                                            <builder autoBuildTarget="all" buildPath="/«name»/_build/Release" cleanBuildTarget="clean" enableAutoBuild="false" enableCleanBuild="true" enabledIncrementalBuild="true" id="cmake4eclipse.mbs.builder.«Math.abs(random.nextInt())»" incrementalBuildTarget="all" keepEnvironmentInBuildfile="false" managedBuildOn="true" name="CMake Builder" parallelBuildOn="true" parallelizationNumber="optimal" superClass="cmake4eclipse.mbs.builder"/>
                                            <tool id="cmake4eclipse.mbs.toolchain.tool.dummy.«Math.abs(random.nextInt())»" name="CMake" superClass="cmake4eclipse.mbs.toolchain.tool.dummy">
                                                <inputType id="cmake4eclipse.mbs.inputType.c.«Math.abs(random.nextInt())»" superClass="cmake4eclipse.mbs.inputType.c"/>
                                                <inputType id="cmake4eclipse.mbs.inputType.cpp.«Math.abs(random.nextInt())»" superClass="cmake4eclipse.mbs.inputType.cpp"/>
                                            </tool>
                                        </toolChain>
                                    </folderInfo>
                                </configuration>
                            </storageModule>
                            <storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
                            <storageModule buildDir="_build/${ConfigName}" dirtyTs="«Math.abs(random.nextInt())»" moduleId="de.marw.cmake4eclipse.mbs.settings">
                                <options/>
                            </storageModule>
                        </cconfiguration>
                    </storageModule>
                    <storageModule moduleId="cdtBuildSystem" version="4.0.0">
                        <project id="«name».cmake4eclipse.mbs.projectType.«Math.abs(random.nextInt())»" name="Cmake4eclipse" projectType="cmake4eclipse.mbs.projectType"/>
                    </storageModule>
                    <storageModule moduleId="org.eclipse.cdt.core.LanguageSettingsProviders"/>
                    <storageModule moduleId="refreshScope" versionNumber="2"/>
                    <storageModule moduleId="org.eclipse.cdt.make.core.buildtargets"/>
                    <storageModule moduleId="scannerConfiguration"/>
                    <storageModule moduleId="org.eclipse.cdt.internal.ui.text.commentOwnerProjectMappings"/>
                </cproject>
            ''')
        ])
    }
}
