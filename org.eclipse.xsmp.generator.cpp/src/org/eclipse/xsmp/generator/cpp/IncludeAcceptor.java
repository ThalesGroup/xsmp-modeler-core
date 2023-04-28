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
package org.eclipse.xsmp.generator.cpp;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.Type;

public class IncludeAcceptor
{
  Set<NamedElement> forwardedTypes = new HashSet<>();

  Set<NamedElement> includedTypes = new HashSet<>();

  Set<NamedElement> sourceTypes = new HashSet<>();

  Set<String> systemTypesHeader = new HashSet<>();

  Set<String> systemTypesSource = new HashSet<>();

  Set<String> userTypesHeader = new HashSet<>();

  Set<String> userTypesSource = new HashSet<>();

  public void systemHeader(String include)
  {
    systemTypesHeader.add(include);
  }

  public void userHeader(String include)
  {
    userTypesHeader.add(include);
  }

  public void systemSource(String include)
  {
    systemTypesSource.add(include);
  }

  public void userSource(String include)
  {
    userTypesSource.add(include);
  }

  public void include(NamedElement type)
  {
    includedTypes.add(type);
    forwardedTypes.remove(type);
    sourceTypes.remove(type);
  }

  public void forward(Type type)
  {
    if (!includedTypes.contains(type))
    {
      forwardedTypes.add(type);
    }

    sourceTypes.remove(type);
  }

  public void source(NamedElement type)
  {
    if (!forwardedTypes.contains(type))
    {
      sourceTypes.add(type);
    }
  }

}