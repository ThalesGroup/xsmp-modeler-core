package org.eclipse.xsmp.ui.wizard.template;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.xtext.ui.util.ProjectFactory;
import org.eclipse.xtext.ui.wizard.template.AbstractProjectTemplate;
import org.eclipse.xtext.ui.wizard.template.GroupTemplateVariable;
import org.eclipse.xtext.ui.wizard.template.IProjectGenerator;
import org.eclipse.xtext.ui.wizard.template.StringTemplateVariable;

public abstract class AbstractXsmpProjectTemplate2 extends AbstractProjectTemplate
{

  @Override
  public void generateProjects(IProjectGenerator generator)
  {
    updateVariables();
    generator.generate(createProjectFactory());
  }

  protected  ProjectFactory createProjectFactory()
  {
    
    
    //if(adoc.isSet()) createAdoc();
    
    return null;
  }

  protected  void customizeProjectFactory(ProjectFactory factory)
  {
    
  }
  protected void addTool(ProjectFactory factory, String toolId)
  {
  }
  
  
  
  

  GroupTemplateVariable advancedGroup = group("Properties");

  String cName = "<<catalogue_name>>";

  protected StringTemplateVariable name = text("Catalogue Name:", cName, "The Catalogue name",
          advancedGroup);

  protected void updateVariables()
  {
    if (name.getValue() == cName) name.setValue(getProjectInfo().getProjectName());
    super.updateVariables();
  }

  protected IStatus validate()
  {
    if (name.getValue().matches("[a-zA-Z][A-Za-z0-9_]*")) return null;
    else return new Status(Status.ERROR, "Wizard", "'" + name + "' is not a valid document name");
  }

  protected List<String> getTools(){
    return null;
    
  }
}
