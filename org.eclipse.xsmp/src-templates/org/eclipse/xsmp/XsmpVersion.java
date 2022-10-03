package org.eclipse.xsmp;

/**
 * helper class to get the current version of this plugin
 */
public interface XsmpVersion
{
  /**
   * The current version of this plugin
   */
  String VERSION = "${project.version}".split("-")[0];
}
