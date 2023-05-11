package org.eclipse.xsmp.ui.tests;

import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.addBuilder;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.addNature;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.createFile;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.testing.util.JavaProjectSetupUtil;

class XsmpcatProjectUtil
{
  private static String newLine = System.getProperty("line.separator");

  public static IJavaProject createProject(String projectName)
    throws CoreException, InvocationTargetException, InterruptedException
  {

    final var javaProject = JavaProjectSetupUtil.createJavaProject(projectName);

    createFile(projectName, "META-INF/MANIFEST", "MF",

            new StringBuilder().append("Manifest-Version: 1.0").append(newLine)
                    .append("Automatic-Module-Name: ").append(projectName).append(newLine)
                    .append("Bundle-ManifestVersion: 2").append(newLine).append("Bundle-Name: ")
                    .append(projectName).append(newLine).append("Bundle-Vendor: Xsmp Test")
                    .append(newLine).append("Bundle-Version: 1.0.0.qualifier").append(newLine)
                    .append("Bundle-SymbolicName: ").append(projectName).append("; singleton:=true")
                    .append(newLine).append("Bundle-ActivationPolicy: lazy").append(newLine)
                    .append("Require-Bundle: org.eclipse.xsmp.lib").append(newLine)
                    .append("Bundle-RequiredExecutionEnvironment: JavaSE-17").append(newLine)
                    .toString()

    );

    addNature(javaProject.getProject(), "org.eclipse.pde.PluginNature");
    addBuilder(javaProject.getProject(), "org.eclipse.pde.ManifestBuilder");
    addNature(javaProject.getProject(), XtextProjectHelper.NATURE_ID);
    addBuilder(javaProject.getProject(), XtextProjectHelper.BUILDER_ID);

    JavaProjectSetupUtil.deleteSourceFolder(javaProject, "src");
    JavaProjectSetupUtil.addToClasspath(javaProject,
            JavaCore.newSourceEntry(new Path("/" + projectName)));

    JavaProjectSetupUtil.addToClasspath(javaProject,
            JavaCore.newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins")));

    return javaProject;
  }
}
