/**
 * -------------------------------------------------------------------------
 * Copyright (C) 2021 THALES ALENIA SPACE FRANCE. All rights reserved
 * -------------------------------------------------------------------------
 */
package org.eclipse.xsmp.profile.tas_mdk.ui.wizard

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Random
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Status
import org.eclipse.xsmp.ui.^extension.ExtensionManager
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
class TasMdkProjectTemplateProvider implements IProjectTemplateProvider {
	override getProjectTemplates() {
		#[new GramCatalogueProject()]
	}
}

@ProjectTemplate(label="TasMdk Project", icon="project_template.png", description="<p><b>Create a new TasMdk Project</b></p>")
final class GramCatalogueProject {
	val advancedGroup = group("Properties")
	val cName = "<<catalogue_name>>"
	val name = text("Catalogue Name:", cName, "The Catalogue name", advancedGroup)
	val generateTestComponent = check("Generated test sub Component", true, "Generate the test sub project")

	override protected updateVariables() {
		if (name.value == cName)
			name.value = projectInfo.projectName
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
			projectNatures += "com.thalesaleniaspace.compilechain.compileChainNature"
			builderIds += "org.python.pydev.PyDevBuilder"
			builderIds.add(0, "org.eclipse.cdt.managedbuilder.core.compilechainbuilder")

            val extensionManager = new ExtensionManager()
            profile = extensionManager.getProfile("org.eclipse.xsmp.profile.tas-mdk")
            tools += extensionManager.getTool("org.eclipse.xsmp.tool.smp")
			requiredBundles.clear
			requiredBundles += "TasMdk"

			addLink("bin", new Path("ROOT_OBJ/" + projectInfo.projectName))
			
			folders += "helpers"

			addFile('''.pydevproject''', '''
				<?xml version="1.0" encoding="UTF-8" standalone="no"?>
				<?eclipse-pydev version="1.0"?><pydev_project>
				    <pydev_property name="org.python.pydev.PYTHON_PROJECT_VERSION">python interpreter</pydev_property>
				    <pydev_property name="org.python.pydev.PYTHON_PROJECT_INTERPRETER">python</pydev_property>
				    <pydev_pathproperty name="org.python.pydev.PROJECT_EXTERNAL_SOURCE_PATH">
				        <path>${ROOT_OBJ}/${PROJECT_DIR_NAME}/PY</path>
				    </pydev_pathproperty>
				    <pydev_pathproperty name="org.python.pydev.PROJECT_SOURCE_PATH">
				        <path>/${PROJECT_DIR_NAME}/helpers</path>
				    </pydev_pathproperty>
				</pydev_project>
			''')
			addFile('''smdl/«name».xsmpcat''', '''
				// Copyright (C) ${year} THALES ALENIA SPACE. All rights reserved
				
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

			addFile('''Makefile''', '''
				COMPONENT_NAME=«projectInfo.projectName»
				
				ifndef COMPILE_CHAIN
				$(error Variable COMPILE_CHAIN is not defined)
				endif
				
				-include $(COMPILE_CHAIN)/component_declaration.mk
				
				CXX_STANDARD=11
				
				LIBRARY_NAME=lib«projectInfo.projectName.toLowerCase».so
				
				include $(COMPILE_CHAIN)/rules_libs.mk
				
				«IF generateTestComponent.value»
					$(COMPONENT_NAME)_tests:
						$(MAKE) -C tests
						$(MAKE) -C tests tu
				«ENDIF»
			''')

			if (generateTestComponent.value) {
				addFile('''tests/Makefile''', '''
					COMPONENT_NAME=«projectInfo.projectName»--tests
					
					ifndef COMPILE_CHAIN
					$(error Variable COMPILE_CHAIN is not defined)
					endif
					
					-include $(COMPILE_CHAIN)/component_declaration.mk
					
					include $(COMPILE_CHAIN)/rules_variants.mk
					
					$(COMPONENT_NAME)_tests: $(COMPONENT_NAME)_tests_python
					
					$(COMPONENT_NAME)_tests_python:
						COMPONENT_NAME=$(COMPONENT_NAME) \
						VT_STDOUT=$(VT_STDOUT) \
						PATH=${ROOT_OBJ}/gram_addons--simulator_launcher/BIN:${PATH} \
						PYTHONPATH=${PYTHONPATH} \
						LD_LIBRARY_PATH=${LD_LIBRARY_PATH} \
						python3 -m gram_addons__python_test_suite.runtests $(TEST_ARGS)
				''')
			}

			val rg = new Random()
			val random = Math.abs(rg.nextInt())
			val randomDebug = Math.abs(rg.nextInt())
			val randomRelease = Math.abs(rg.nextInt())
			addFile('''.settings/language.settings.xml''', '''
				<?xml version="1.0" encoding="UTF-8" standalone="no"?>
				<project>
				    <configuration id="cdt.managedbuild.toolchain.gnu.base.«random».«randomDebug»" name="Debug">
				    	<extension point="org.eclipse.cdt.core.LanguageSettingsProvider">
				    		<provider class="com.thalesaleniaspace.compilechain.CompileChainSpecsDetector" console="true" id="com.thalesaleniaspace.compilechain.CompileChainSpecsDetector" keep-relative-paths="false" name="CompileChain Compiler Settings" prefer-non-shared="true">
				    			<language-scope id="org.eclipse.cdt.core.gcc"/>
				    			<language-scope id="org.eclipse.cdt.core.g++"/>
				    		</provider>
				    	</extension>
				    </configuration>
				    <configuration id="cdt.managedbuild.toolchain.gnu.base.«random».«randomRelease»" name="Release">
				    	<extension point="org.eclipse.cdt.core.LanguageSettingsProvider">
				    		<provider class="com.thalesaleniaspace.compilechain.CompileChainSpecsDetector" console="true" id="com.thalesaleniaspace.compilechain.CompileChainSpecsDetector" keep-relative-paths="false" name="CompileChain Compiler Settings" prefer-non-shared="true">
				    			<language-scope id="org.eclipse.cdt.core.gcc"/>
				    			<language-scope id="org.eclipse.cdt.core.g++"/>
				    		</provider>
				    	</extension>
				    </configuration>
				</project>
			''')

			addFile('''component.conf''', '''
			__LIBRARIES__=TasMdk
			__REFERENCED_LIBRARIES__=
			__INCLUDE_FOLDERS__=include,include-gen
			__SOURCE_FOLDERS__=src,src-gen
			__OPERATIONAL_PYTHON_TOOLS_FOLDER__=helpers
			__VERSION__=1''')

			if (generateTestComponent.value) {
				addFile('''tests/component.conf''', '''
					__LIBRARIES__=\
					gram_addons--python_test_suite,\
					gram_addons--simulator_launcher,\
					«projectInfo.projectName»
					__COMPONENTS_ROOT_PATH__=.
				''');
			}

			val randomScanner = Math.abs(rg.nextInt())
			addFile('''.cproject''', '''	
				<?xml version="1.0" encoding="UTF-8" standalone="no"?>
				<?fileVersion 4.0.0?><cproject storage_type_id="org.eclipse.cdt.core.XmlProjectDescriptionStorage">
					<storageModule moduleId="org.eclipse.cdt.core.settings">
						<cconfiguration id="cdt.managedbuild.toolchain.gnu.base.«random».«randomDebug»">
							<storageModule buildSystemId="org.eclipse.cdt.managedbuilder.core.configurationDataProvider" id="cdt.managedbuild.toolchain.gnu.base.«random».«randomDebug»" moduleId="org.eclipse.cdt.core.settings" name="Debug">
								<externalSettings/>
								<extensions>
									<extension id="org.eclipse.cdt.core.GNU_ELF" point="org.eclipse.cdt.core.BinaryParser"/>
									<extension id="org.eclipse.cdt.core.GASErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
									<extension id="org.eclipse.cdt.core.GLDErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
									<extension id="org.eclipse.cdt.core.GCCErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
								</extensions>
							</storageModule>
							<storageModule moduleId="cdtBuildSystem" version="4.0.0">
								<configuration artifactName="${ProjName}" buildProperties="" description="Debug mode" id="cdt.managedbuild.toolchain.gnu.base.«random».«randomDebug»" name="Debug" optionalBuildProperties="org.eclipse.cdt.docker.launcher.containerbuild.property.selectedvolumes=,org.eclipse.cdt.docker.launcher.containerbuild.property.volumes=" parent="org.eclipse.cdt.build.core.emptycfg">
									<folderInfo id="cdt.managedbuild.toolchain.gnu.base.«random».«randomDebug»." name="/" resourcePath="">
										<toolChain id="com.thalesaleniaspace.compilechain.toolChain.«Math.abs(rg.nextInt())»" name="CompileChain" superClass="com.thalesaleniaspace.compilechain.toolChain">
											<targetPlatform binaryParser="org.eclipse.cdt.core.GNU_ELF" id="com.thalesaleniaspace.compilechain.targetPlatform.«Math.abs(rg.nextInt())»" isAbstract="false" name="CompileChain Target Platform" superClass="com.thalesaleniaspace.compilechain.targetPlatform"/>
											<builder id="com.thalesaleniaspace.compilechain.builder.«Math.abs(rg.nextInt())»" keepEnvironmentInBuildfile="false" managedBuildOn="false" name="CompileChain Builder" superClass="com.thalesaleniaspace.compilechain.builder"/>
											<tool id="cdt.managedbuild.tool.gnu.archiver.base.«Math.abs(rg.nextInt())»" name="GCC Archiver" superClass="cdt.managedbuild.tool.gnu.archiver.base"/>
											<tool id="cdt.managedbuild.tool.gnu.cpp.compiler.base.«Math.abs(rg.nextInt())»" name="GCC C++ Compiler" superClass="cdt.managedbuild.tool.gnu.cpp.compiler.base">
												<inputType id="cdt.managedbuild.tool.gnu.cpp.compiler.input.«Math.abs(rg.nextInt())»" superClass="cdt.managedbuild.tool.gnu.cpp.compiler.input"/>
											</tool>
											<tool id="cdt.managedbuild.tool.gnu.c.compiler.base.«Math.abs(rg.nextInt())»" name="GCC C Compiler" superClass="cdt.managedbuild.tool.gnu.c.compiler.base">
												<inputType id="cdt.managedbuild.tool.gnu.c.compiler.input.«Math.abs(rg.nextInt())»" superClass="cdt.managedbuild.tool.gnu.c.compiler.input"/>
											</tool>
											<tool id="cdt.managedbuild.tool.gnu.c.linker.base.«Math.abs(rg.nextInt())»" name="GCC C Linker" superClass="cdt.managedbuild.tool.gnu.c.linker.base"/>
											<tool id="cdt.managedbuild.tool.gnu.cpp.linker.base.«Math.abs(rg.nextInt())»" name="GCC C++ Linker" superClass="cdt.managedbuild.tool.gnu.cpp.linker.base">
												<inputType id="cdt.managedbuild.tool.gnu.cpp.linker.input.«Math.abs(rg.nextInt())»" superClass="cdt.managedbuild.tool.gnu.cpp.linker.input">
													<additionalInput kind="additionalinputdependency" paths="$(USER_OBJS)"/>
													<additionalInput kind="additionalinput" paths="$(LIBS)"/>
												</inputType>
											</tool>
											<tool id="cdt.managedbuild.tool.gnu.assembler.base.«Math.abs(rg.nextInt())»" name="GCC Assembler" superClass="cdt.managedbuild.tool.gnu.assembler.base">
												<inputType id="cdt.managedbuild.tool.gnu.assembler.input.«Math.abs(rg.nextInt())»" superClass="cdt.managedbuild.tool.gnu.assembler.input"/>
											</tool>
										</toolChain>
									</folderInfo>
									<sourceEntries>
										<entry flags="VALUE_WORKSPACE_PATH" kind="sourcePath" name="include"/>
										<entry flags="VALUE_WORKSPACE_PATH" kind="sourcePath" name="include-gen"/>
										<entry flags="VALUE_WORKSPACE_PATH" kind="sourcePath" name="src"/>
										<entry flags="VALUE_WORKSPACE_PATH" kind="sourcePath" name="src-gen"/>
									</sourceEntries>
								</configuration>
							</storageModule>
							<storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
						</cconfiguration>
						<cconfiguration id="cdt.managedbuild.toolchain.gnu.base.«random».«randomRelease»">
							<storageModule buildSystemId="org.eclipse.cdt.managedbuilder.core.configurationDataProvider" id="cdt.managedbuild.toolchain.gnu.base.«random».«randomRelease»" moduleId="org.eclipse.cdt.core.settings" name="Release">
								<externalSettings/>
								<extensions>
									<extension id="org.eclipse.cdt.core.GNU_ELF" point="org.eclipse.cdt.core.BinaryParser"/>
									<extension id="org.eclipse.cdt.core.GASErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
									<extension id="org.eclipse.cdt.core.GLDErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
									<extension id="org.eclipse.cdt.core.GCCErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
								</extensions>
							</storageModule>
							<storageModule moduleId="cdtBuildSystem" version="4.0.0">
								<configuration artifactName="${ProjName}" buildProperties="" description="Release mode" id="cdt.managedbuild.toolchain.gnu.base.«random».«randomRelease»" name="Release" optionalBuildProperties="org.eclipse.cdt.docker.launcher.containerbuild.property.selectedvolumes=,org.eclipse.cdt.docker.launcher.containerbuild.property.volumes=" parent="org.eclipse.cdt.build.core.emptycfg">
									<folderInfo id="cdt.managedbuild.toolchain.gnu.base.«random».«randomRelease»." name="/" resourcePath="">
										<toolChain id="com.thalesaleniaspace.compilechain.toolChain.«Math.abs(rg.nextInt())»" name="CompileChain" superClass="com.thalesaleniaspace.compilechain.toolChain">
											<targetPlatform binaryParser="org.eclipse.cdt.core.GNU_ELF" id="com.thalesaleniaspace.compilechain.targetPlatform.«Math.abs(rg.nextInt())»" isAbstract="false" name="CompileChain Target Platform" superClass="com.thalesaleniaspace.compilechain.targetPlatform"/>
											<builder id="com.thalesaleniaspace.compilechain.builder.«Math.abs(rg.nextInt())»" keepEnvironmentInBuildfile="false" managedBuildOn="false" name="CompileChain Builder" superClass="com.thalesaleniaspace.compilechain.builder"/>
											<tool id="cdt.managedbuild.tool.gnu.archiver.base.«Math.abs(rg.nextInt())»" name="GCC Archiver" superClass="cdt.managedbuild.tool.gnu.archiver.base"/>
											<tool id="cdt.managedbuild.tool.gnu.cpp.compiler.base.«Math.abs(rg.nextInt())»" name="GCC C++ Compiler" superClass="cdt.managedbuild.tool.gnu.cpp.compiler.base">
												<inputType id="cdt.managedbuild.tool.gnu.cpp.compiler.input.«Math.abs(rg.nextInt())»" superClass="cdt.managedbuild.tool.gnu.cpp.compiler.input"/>
											</tool>
											<tool id="cdt.managedbuild.tool.gnu.c.compiler.base.«Math.abs(rg.nextInt())»" name="GCC C Compiler" superClass="cdt.managedbuild.tool.gnu.c.compiler.base">
												<inputType id="cdt.managedbuild.tool.gnu.c.compiler.input.«Math.abs(rg.nextInt())»" superClass="cdt.managedbuild.tool.gnu.c.compiler.input"/>
											</tool>
											<tool id="cdt.managedbuild.tool.gnu.c.linker.base.«Math.abs(rg.nextInt())»" name="GCC C Linker" superClass="cdt.managedbuild.tool.gnu.c.linker.base"/>
											<tool id="cdt.managedbuild.tool.gnu.cpp.linker.base.«Math.abs(rg.nextInt())»" name="GCC C++ Linker" superClass="cdt.managedbuild.tool.gnu.cpp.linker.base">
												<inputType id="cdt.managedbuild.tool.gnu.cpp.linker.input.«Math.abs(rg.nextInt())»" superClass="cdt.managedbuild.tool.gnu.cpp.linker.input">
													<additionalInput kind="additionalinputdependency" paths="$(USER_OBJS)"/>
													<additionalInput kind="additionalinput" paths="$(LIBS)"/>
												</inputType>
											</tool>
											<tool id="cdt.managedbuild.tool.gnu.assembler.base.«Math.abs(rg.nextInt())»" name="GCC Assembler" superClass="cdt.managedbuild.tool.gnu.assembler.base">
												<inputType id="cdt.managedbuild.tool.gnu.assembler.input.«Math.abs(rg.nextInt())»" superClass="cdt.managedbuild.tool.gnu.assembler.input"/>
											</tool>
										</toolChain>
									</folderInfo>
									<sourceEntries>
										<entry flags="VALUE_WORKSPACE_PATH" kind="sourcePath" name="include"/>
										<entry flags="VALUE_WORKSPACE_PATH" kind="sourcePath" name="include-gen"/>
										<entry flags="VALUE_WORKSPACE_PATH" kind="sourcePath" name="src"/>
										<entry flags="VALUE_WORKSPACE_PATH" kind="sourcePath" name="src-gen"/>
									</sourceEntries>
								</configuration>
							</storageModule>
							<storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
						</cconfiguration>
					</storageModule>
					<storageModule moduleId="cdtBuildSystem" version="4.0.0">
						<project id="Tutu.null.«Math.abs(rg.nextInt())»" name="Tutu"/>
					</storageModule>
					<storageModule moduleId="org.eclipse.cdt.core.LanguageSettingsProviders"/>
					<storageModule moduleId="refreshScope" versionNumber="2">
						<configuration configurationName="Default">
							<resource resourceType="PROJECT" workspacePath="/Tutu"/>
						</configuration>
					</storageModule>
					<storageModule moduleId="org.eclipse.cdt.make.core.buildtargets"/>
					<storageModule moduleId="org.eclipse.cdt.core.pathentry"/>
					<storageModule moduleId="scannerConfiguration">
						<autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
						<scannerConfigBuildInfo instanceId="cdt.managedbuild.toolchain.gnu.base.«random»;cdt.managedbuild.toolchain.gnu.base.«random».«randomScanner»;cdt.managedbuild.tool.gnu.c.compiler.base.«Math.abs(rg.nextInt())»;cdt.managedbuild.tool.gnu.c.compiler.input.«Math.abs(rg.nextInt())»">
							<autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
						</scannerConfigBuildInfo>
						<scannerConfigBuildInfo instanceId="cdt.managedbuild.toolchain.gnu.base.«random»;cdt.managedbuild.toolchain.gnu.base.«random».«randomScanner»;cdt.managedbuild.tool.gnu.cpp.compiler.base.«Math.abs(rg.nextInt())»;cdt.managedbuild.tool.gnu.cpp.compiler.input.«Math.abs(rg.nextInt())»">
							<autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
						</scannerConfigBuildInfo>
					</storageModule>
					<storageModule moduleId="org.eclipse.cdt.make.core.buildtargets">
						<buildTargets>
							<target name="clean" path="" targetID="org.eclipse.cdt.build.CompileChainTargetBuilder">
								<buildCommand>make</buildCommand>
								<buildArguments>-j8</buildArguments>
								<buildTarget>clean</buildTarget>
								<stopOnError>true</stopOnError>
								<useDefaultCommand>true</useDefaultCommand>
								<runAllBuilders>false</runAllBuilders>
							</target>
							<target name="make" path="" targetID="org.eclipse.cdt.build.CompileChainTargetBuilder">
								<buildCommand>make</buildCommand>
								<buildArguments>-j8</buildArguments>
								<buildTarget/>
								<stopOnError>true</stopOnError>
								<useDefaultCommand>true</useDefaultCommand>
								<runAllBuilders>false</runAllBuilders>
							</target>
							<target name="test" path="" targetID="org.eclipse.cdt.build.CompileChainTargetBuilder">
								<buildCommand>make</buildCommand>
								<buildArguments>-j8</buildArguments>
								<buildTarget>test</buildTarget>
								<stopOnError>true</stopOnError>
								<useDefaultCommand>true</useDefaultCommand>
								<runAllBuilders>false</runAllBuilders>
							</target>
						</buildTargets>
					</storageModule>
				</cproject>
			''')
			addFile('''.gitignore''', '''
				__pycache__/
			''')
		])
	}
}
