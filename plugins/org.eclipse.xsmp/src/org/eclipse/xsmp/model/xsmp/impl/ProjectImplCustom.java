/**
 * ******************************************************************************
 * Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * *****************************************************************************
 */
package org.eclipse.xsmp.model.xsmp.impl;

import java.util.Objects;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xsmp.model.xsmp.IncludePath;
import org.eclipse.xsmp.model.xsmp.ProfileReference;
import org.eclipse.xsmp.model.xsmp.ProjectReference;
import org.eclipse.xsmp.model.xsmp.SourcePath;
import org.eclipse.xsmp.model.xsmp.ToolReference;

public class ProjectImplCustom extends ProjectImpl
{

  @Override
  public String getProfile()
  {
    return getMember().stream().filter(ProfileReference.class::isInstance)
            .map(m -> ((ProfileReference) m).getName()).filter(Objects::nonNull).findFirst()
            .orElse("");
  }

  @Override
  public EList<String> getTools()
  {
    return new BasicEList<>(getMember().stream().filter(ToolReference.class::isInstance)
            .map(m -> ((ToolReference) m).getName()).filter(Objects::nonNull).toList());
  }

  @Override
  public EList<String> getSources()
  {
    return new BasicEList<>(getMember().stream().filter(SourcePath.class::isInstance)
            .map(m -> ((SourcePath) m).getName()).filter(Objects::nonNull).toList());
  }

  @Override
  public EList<String> getReferencedProjects()
  {
    return new BasicEList<>(getMember().stream().filter(ProjectReference.class::isInstance)
            .map(m -> ((ProjectReference) m).getName()).filter(Objects::nonNull).toList());
  }

  @Override
  public EList<String> getIncludes()
  {
    return new BasicEList<>(getMember().stream().filter(IncludePath.class::isInstance)
            .map(m -> ((IncludePath) m).getName()).filter(Objects::nonNull).toList());
  }

} // ProjectImplCustom
