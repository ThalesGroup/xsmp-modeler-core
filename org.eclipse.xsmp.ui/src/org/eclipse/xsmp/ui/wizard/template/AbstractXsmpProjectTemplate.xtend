package org.eclipse.xsmp.ui.wizard.template

import java.util.List
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.eclipse.xtext.ui.util.ProjectFactory
import org.eclipse.xtext.ui.wizard.template.AbstractProjectTemplate
import org.eclipse.xtext.ui.wizard.template.IProjectGenerator

abstract class AbstractXsmpProjectTemplate extends AbstractProjectTemplate {

    val advancedGroup = group("Properties")
    val cName = "<<catalogue_name>>"
    protected val name = text("Catalogue Name:", cName, "The Catalogue name", advancedGroup)

    override generateProjects(IProjectGenerator generator) {
        updateVariables
        generator.generate(createProjectFactory)
    }

    override protected void updateVariables() {
        if(name.value == cName) name.setValue(getProjectInfo.getProjectName)
        super.updateVariables
    }

    override protected IStatus validate() {
        if (name.value.matches("[a-zA-Z][A-Za-z0-9_]*"))
            return null
        else
            return new Status(Status.ERROR, "Wizard", "'" + name + "' is not a valid document name")
    }

    def protected ProjectFactory createProjectFactory() {
        return null
    }

    def protected customizeProjectFactory(ProjectFactory factory) {
    }

    def protected addTool(ProjectFactory factory, String toolId) {
    }

    def protected List<String> getTools() {
        return null
    }
}