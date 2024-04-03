package org.eclipse.xsmp.ui.tests;

import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.addNature;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.createFile;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.testing.util.JavaProjectSetupUtil;

class XsmpcatProjectUtil
{
  private static String newLine = System.lineSeparator();

  public static IJavaProject createProject(String projectName)
    throws CoreException, InvocationTargetException, InterruptedException
  {

    final var javaProject = JavaProjectSetupUtil.createJavaProject(projectName);
    createFile(projectName, "xsmp", "project",
            new StringBuilder().append("project \"").append(projectName).append("\"")
                    .append(newLine).append("source \"smdl\"").append(newLine).toString());
    addNature(javaProject.getProject(), XtextProjectHelper.NATURE_ID);
    return javaProject;
  }
}
