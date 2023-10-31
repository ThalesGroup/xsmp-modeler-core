/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.workspace;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.FileProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class XsmpProjectConfig extends FileProjectConfig implements IXsmpProjectConfig
{
  private static final boolean defaultIsBuildAutomatically = true;

  private static final String defaultProfile = "xsmp-sdk";

  private String profile = defaultProfile;

  private final List<String> dependencies = new ArrayList<>();

  private final List<String> tools = new ArrayList<>();

  private boolean isBuildAutomatically = defaultIsBuildAutomatically;

  public static final URI configUri = URI.createURI(".xsmp").appendSegment("settings.json");

  public XsmpProjectConfig(URI uri, String uniqueProjectName, IWorkspaceConfig workspaceConfig)
  {
    super(uri, uniqueProjectName, workspaceConfig);
    refresh();
  }

  @Override
  public String getProfile()
  {
    return profile;
  }

  @Override
  public boolean shouldGenerate()
  {
    return isBuildAutomatically;
  }

  @Override
  public List<String> getDependencies()
  {
    return dependencies;
  }

  @Override
  public List<String> getTools()
  {
    return tools;
  }

  @Override
  public void refresh()
  {

    try
    {
      final var root = JsonParser
              .parseReader(
                      new FileReader(getPath().appendSegments(configUri.segments()).toFileString()))
              .getAsJsonObject();
      if (root.has("profile"))
      {
        profile = root.get("profile").getAsString();
      }
      if (root.has("build_automatically"))
      {
        isBuildAutomatically = root.get("build_automatically").getAsBoolean();
      }

      if (root.has("sources"))
      {
        getSourceFolders().clear();
        for (final var source : root.get("sources").getAsJsonArray())
        {
          addSourceFolder(source.getAsString());
        }
      }
      else
      {
        addSourceFolder("smdl");
      }
      if (root.has("dependencies"))
      {
        dependencies.clear();
        for (final var dependency : root.get("dependencies").getAsJsonArray())
        {
          dependencies.add(dependency.getAsString());
        }
      }
      if (root.has("tools"))
      {
        tools.clear();
        for (final var tool : root.get("tools").getAsJsonArray())
        {
          tools.add(tool.getAsString());
        }
      }
      else
      {
        tools.clear();
        tools.add("smp");
      }
    }
    catch (final FileNotFoundException e)
    {
      addSourceFolder("smdl");
      e.printStackTrace();
    }
    catch (final JsonIOException | JsonSyntaxException e)
    {
      e.printStackTrace();
    }

  }

}
