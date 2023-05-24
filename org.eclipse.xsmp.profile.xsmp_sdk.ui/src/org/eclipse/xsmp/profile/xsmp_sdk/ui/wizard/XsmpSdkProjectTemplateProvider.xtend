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
            name.value = projectInfo.projectName.split("\\.").last
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

            profile = "org.eclipse.xsmp.profile.xsmp_sdk"
            defaultOutput = "_build"

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
                    
                }
            ''')

            addFile('''CMakeLists.txt''', '''
                cmake_minimum_required(VERSION 3.14)
                
                # set the project name and version
                project(«name» LANGUAGES CXX)
                
                # specify the C++ standard
                set(CMAKE_CXX_STANDARD 11)
                set(CMAKE_CXX_STANDARD_REQUIRED True)
                
                include(FetchContent)
                FetchContent_Declare(
                  xsmp-sdk
                  GIT_REPOSITORY https://github.com/ydaveluy/xsmp-sdk.git
                  GIT_TAG        main
                )
                
                FetchContent_MakeAvailable(xsmp-sdk)
                        
                file(GLOB_RECURSE SRC CONFIGURE_DEPENDS src/*.cpp src-gen/*.cpp)
                
                add_library(«name» SHARED ${SRC})
                target_include_directories(«name» PUBLIC  src src-gen)
                target_link_libraries(«name» Xsmp::Cdk)
                
                
                # --------------------------------------------------------------------
                
                if(CMAKE_PROJECT_NAME STREQUAL PROJECT_NAME)
                    include(CTest)
                endif()
                
                #if(CMAKE_PROJECT_NAME STREQUAL PROJECT_NAME AND BUILD_TESTING)
                #    add_subdirectory(tests)
                #endif()
                
            ''')
            addFile('''.gitignore''', '''
                /_build/
            ''')
            addFile('''.gitattributes''', '''
                * text=auto eol=lf
            ''')

            addFile("README.md", '''
                # «name»
            ''')
            val debugConfId = Math.abs(random.nextInt())
            val releaseConfId = Math.abs(random.nextInt())
            val debugCmakeId = Math.abs(random.nextInt())
            val releaseCmakeId = Math.abs(random.nextInt())

            val debugCId = Math.abs(random.nextInt())
            val releaseCId = Math.abs(random.nextInt())
            val debugCppId = Math.abs(random.nextInt())
            val releaseCppId = Math.abs(random.nextInt())

            addFile('''.cproject''', '''
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <?fileVersion 4.0.0?><cproject storage_type_id="org.eclipse.cdt.core.XmlProjectDescriptionStorage">
                    <storageModule moduleId="org.eclipse.cdt.core.settings">
                        <cconfiguration id="cmake4eclipse.mbs.config.debug.«debugConfId»">
                            <storageModule buildSystemId="org.eclipse.cdt.managedbuilder.core.configurationDataProvider" id="cmake4eclipse.mbs.config.debug.«debugConfId»" moduleId="org.eclipse.cdt.core.settings" name="Debug">
                                <externalSettings/>
                                <extensions>
                                    <extension id="org.eclipse.cdt.core.PE64" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.GNU_ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.GmakeErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.GLDErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.CWDLocator" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.GCCErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                </extensions>
                            </storageModule>
                            <storageModule moduleId="cdtBuildSystem" version="4.0.0">
                                <configuration artifactName="${ProjName}" buildArtefactType="cmake4eclipse.mbs.buildArtefactType.cmake" buildProperties="org.eclipse.cdt.build.core.buildArtefactType=cmake4eclipse.mbs.buildArtefactType.cmake,org.eclipse.cdt.build.core.buildType=org.eclipse.cdt.build.core.buildType.debug" description="" id="cmake4eclipse.mbs.config.debug.«debugConfId»" name="Debug" optionalBuildProperties="org.eclipse.cdt.docker.launcher.containerbuild.property.selectedvolumes=,org.eclipse.cdt.docker.launcher.containerbuild.property.volumes=" parent="cmake4eclipse.mbs.config.debug">
                                    <folderInfo id="cmake4eclipse.mbs.config.debug.«debugConfId»." name="/" resourcePath="">
                                        <toolChain id="cmake4eclipse.mbs.toolchain.cmake.«Math.abs(random.nextInt())»" name="CMake driven" superClass="cmake4eclipse.mbs.toolchain.cmake">
                                            <targetPlatform id="cmake4eclipse.mbs.targetPlatform.cmake.«Math.abs(random.nextInt())»" name="Any Platform" superClass="cmake4eclipse.mbs.targetPlatform.cmake"/>
                                            <builder buildPath="/«name»/_build/Debug" id="cmake4eclipse.mbs.builder.«Math.abs(random.nextInt())»" keepEnvironmentInBuildfile="false" name="CMake Builder" superClass="cmake4eclipse.mbs.builder"/>
                                            <tool id="cmake4eclipse.mbs.toolchain.tool.dummy.«debugCmakeId»" name="CMake" superClass="cmake4eclipse.mbs.toolchain.tool.dummy">
                                                <inputType id="cmake4eclipse.mbs.inputType.c.«debugCId»" superClass="cmake4eclipse.mbs.inputType.c"/>
                                                <inputType id="cmake4eclipse.mbs.inputType.cpp.«debugCppId»" superClass="cmake4eclipse.mbs.inputType.cpp"/>
                                            </tool>
                                        </toolChain>
                                    </folderInfo>
                                </configuration>
                            </storageModule>
                            <storageModule buildDir="_build/${ConfigName}" moduleId="de.marw.cmake4eclipse.mbs.settings" rootDir="">
                                <options/>
                            </storageModule>
                            <storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
                        </cconfiguration>
                        <cconfiguration id="cmake4eclipse.mbs.config.release.«releaseConfId»">
                            <storageModule buildSystemId="org.eclipse.cdt.managedbuilder.core.configurationDataProvider" id="cmake4eclipse.mbs.config.release.«releaseConfId»" moduleId="org.eclipse.cdt.core.settings" name="Release">
                                <externalSettings/>
                                <extensions>
                                    <extension id="org.eclipse.cdt.core.PE64" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.GNU_ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                                    <extension id="org.eclipse.cdt.core.GmakeErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.GLDErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.CWDLocator" point="org.eclipse.cdt.core.ErrorParser"/>
                                    <extension id="org.eclipse.cdt.core.GCCErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                                </extensions>
                            </storageModule>
                            <storageModule moduleId="cdtBuildSystem" version="4.0.0">
                                <configuration artifactName="${ProjName}" buildArtefactType="cmake4eclipse.mbs.buildArtefactType.cmake" buildProperties="org.eclipse.cdt.build.core.buildArtefactType=cmake4eclipse.mbs.buildArtefactType.cmake,org.eclipse.cdt.build.core.buildType=org.eclipse.cdt.build.core.buildType.release" description="" id="cmake4eclipse.mbs.config.release.«releaseConfId»" name="Release" optionalBuildProperties="" parent="cmake4eclipse.mbs.config.release">
                                    <folderInfo id="cmake4eclipse.mbs.config.release.«releaseConfId»." name="/" resourcePath="">
                                        <toolChain id="cmake4eclipse.mbs.config.release.toolChain.«Math.abs(random.nextInt())»" name="CMake driven" superClass="cmake4eclipse.mbs.config.release.toolChain">
                                            <targetPlatform id="cmake4eclipse.mbs.targetPlatform.cmake.«Math.abs(random.nextInt())»" name="Any Platform" superClass="cmake4eclipse.mbs.targetPlatform.cmake"/>
                                            <builder buildPath="/«name»/build/Release" id="cmake4eclipse.mbs.builder.«Math.abs(random.nextInt())»" keepEnvironmentInBuildfile="false" managedBuildOn="true" name="CMake Builder" superClass="cmake4eclipse.mbs.builder"/>
                                            <tool id="cmake4eclipse.mbs.toolchain.tool.dummy.«releaseCmakeId»" name="CMake" superClass="cmake4eclipse.mbs.toolchain.tool.dummy">
                                                <inputType id="cmake4eclipse.mbs.inputType.c.«releaseCId»" superClass="cmake4eclipse.mbs.inputType.c"/>
                                                <inputType id="cmake4eclipse.mbs.inputType.cpp.«releaseCppId»" superClass="cmake4eclipse.mbs.inputType.cpp"/>
                                            </tool>
                                        </toolChain>
                                    </folderInfo>
                                </configuration>
                            </storageModule>
                            <storageModule buildDir="_build/${ConfigName}" moduleId="de.marw.cmake4eclipse.mbs.settings" rootDir="">
                                <options/>
                            </storageModule>
                            <storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
                        </cconfiguration>
                    </storageModule>
                    <storageModule moduleId="cdtBuildSystem" version="4.0.0">
                        <project id="«name».cmake4eclipse.mbs.projectType.«Math.abs(random.nextInt())»" name="Cmake4eclipse" projectType="cmake4eclipse.mbs.projectType"/>
                    </storageModule>
                    <storageModule moduleId="org.eclipse.cdt.core.LanguageSettingsProviders"/>
                    <storageModule moduleId="refreshScope" versionNumber="2">
                        <configuration configurationName="Debug">
                            <resource resourceType="PROJECT" workspacePath="/«name»"/>
                        </configuration>
                        <configuration configurationName="Release">
                            <resource resourceType="PROJECT" workspacePath="/«name»"/>
                        </configuration>
                    </storageModule>
                    <storageModule moduleId="org.eclipse.cdt.make.core.buildtargets"/>
                    <storageModule moduleId="scannerConfiguration">
                        <autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
                        <scannerConfigBuildInfo instanceId="cmake4eclipse.mbs.config.debug.«debugConfId»;cmake4eclipse.mbs.config.debug.«debugConfId».;cmake4eclipse.mbs.toolchain.tool.dummy.«debugCmakeId»;cmake4eclipse.mbs.inputType.c.«debugCId»">
                            <autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
                        </scannerConfigBuildInfo>
                        <scannerConfigBuildInfo instanceId="cmake4eclipse.mbs.config.debug.«debugConfId»;cmake4eclipse.mbs.config.debug.«debugConfId».;cmake4eclipse.mbs.toolchain.tool.dummy.«debugCmakeId»;cmake4eclipse.mbs.inputType.cpp.«debugCppId»">
                            <autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
                        </scannerConfigBuildInfo>
                        <scannerConfigBuildInfo instanceId="cmake4eclipse.mbs.config.release.«releaseConfId»;cmake4eclipse.mbs.config.release.«releaseConfId».;cmake4eclipse.mbs.toolchain.tool.dummy.«releaseCmakeId»;cmake4eclipse.mbs.inputType.c.«releaseCId»">
                            <autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
                        </scannerConfigBuildInfo>
                        <scannerConfigBuildInfo instanceId="cmake4eclipse.mbs.config.release.«releaseConfId»;cmake4eclipse.mbs.config.release.«releaseConfId».;cmake4eclipse.mbs.toolchain.tool.dummy.«releaseCmakeId»;cmake4eclipse.mbs.inputType.cpp.«releaseCppId»">
                            <autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
                        </scannerConfigBuildInfo>
                    </storageModule>
                </cproject>
            ''')
        ])
    }
}
