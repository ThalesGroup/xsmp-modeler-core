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
package org.eclipse.xsmp.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsmp.extension.IProfile;
import org.eclipse.xsmp.extension.ITool;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.workspace.IProjectConfigProvider;

import com.google.inject.Inject;

/**
 * An Xsmp validator that is active only when the extension is enabled
 *
 * @author yannick
 */
public abstract class AbstractXsmpContextValidator extends AbstractDeclarativeValidator
{
  private final String isResponsibleKey = getClass().getCanonicalName() + ".isResponsible";

  @Inject(optional = true)
  private IProjectConfigProvider configProvider;

  @Inject(optional = true)
  IProfile profile;

  @Inject(optional = true)
  ITool tool;

  @Override
  protected List<EPackage> getEPackages()
  {
    final List<EPackage> result = new ArrayList<>();
    result.add(EPackage.Registry.INSTANCE.getEPackage("http://org.eclipse.xsmp/xsmp"));
    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/emf/2002/Ecore"));
    return result;
  }

  protected boolean isEnabledFor(IXsmpProjectConfig config)
  {
    if (profile != null)
    {
      return profile.getId().equals(config.getProfile());
    }
    if (tool != null)
    {
      return config.getTools().contains(tool.getId());
    }
    return true;
  }

  protected boolean isResponsible(ResourceSet context)
  {
    if (configProvider != null
            && configProvider.getProjectConfig(context) instanceof final IXsmpProjectConfig config)
    {
      return isEnabledFor(config);
    }
    return true;
  }

  /**
   * Cache the result in the context map
   */
  @Override
  protected boolean isResponsible(Map<Object, Object> context, EObject eObject)
  {

    var responsible = context != null ? (Boolean) context.get(isResponsibleKey) : null;
    if (responsible == null)
    {

      responsible = super.isResponsible(context, eObject)
              && isResponsible(EcoreUtil2.getResourceSet(eObject));

      if (context != null)
      {
        context.put(isResponsibleKey, responsible);
      }
    }
    return responsible;
  }

}
