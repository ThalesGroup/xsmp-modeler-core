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
package org.eclipse.xsmp.profile.esa_cdk_legacy.ui.wizard

import java.time.Instant
import java.time.temporal.ChronoUnit
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
class EsaCdkProjectTemplateProvider implements IProjectTemplateProvider {
	override getProjectTemplates() {
		#[new EsaCdkProject()]
	}
}

@ProjectTemplate(label="ESA CDK Legacy Project", icon="project_template.png", description="<p><b>Create a new ESA CDK Legacy Project</b></p>")
final class EsaCdkProject {
	val advancedGroup = group("Properties")
	val cName = "<<catalogue_name>>"
	val name = text("Catalogue Name:", cName, "The Catalogue name", advancedGroup)
	


	override protected updateVariables() {
		if (name.value == cName)
			name.value = projectInfo.projectName.replaceAll("\\.", "_")
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

		generator.generate(new XsmpcatProjectFactory() {

			override protected addCdtNatures() {
				// do not add cdt natures
			}

		} => [
			projectName = projectInfo.projectName
			location = projectInfo.locationPath
			
			val extensionManager = new ExtensionManager()
			profile = extensionManager.getProfile("org.eclipse.xsmp.profile.esa-cdk-legacy")
			tools += extensionManager.getTool("org.eclipse.xsmp.tool.smp")

			addFile('''smdl/«name».xsmpcat''', '''
				// Copyright ${year} ${user}. All rights reserved.
				//
				// YOUR NOTICE
				//
				// Generation date:  ${date} ${time}
				
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
				file(GLOB_RECURSE SRC CONFIGURE_DEPENDS src/*.cpp src-gen/*.cpp)
				
				simulus_library(
				    MAIN
				    SOURCES
				        ${SRC}
				    LIBRARIES
				        esa.ecss.smp.cdk
				)
				target_include_directories(«name» PUBLIC src src-gen)
			''')

			addFile("README.md", '''
				# «name»
			''')

		])
	}
}
