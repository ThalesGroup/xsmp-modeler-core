package org.eclipse.xsmp.ui.wizard.template;

import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtext.ui.wizard.template.ProjectTemplateProcessor;
import org.eclipse.xtext.xbase.lib.Extension;

public class XsmpProjectTemplateProcessor extends ProjectTemplateProcessor
{
  @Override
  public void doTransform(MutableClassDeclaration annotatedClass, @Extension TransformationContext context) {
    annotatedClass.setExtendedClass(context.newTypeReference(AbstractXsmpProjectTemplate.class));
  }
}
