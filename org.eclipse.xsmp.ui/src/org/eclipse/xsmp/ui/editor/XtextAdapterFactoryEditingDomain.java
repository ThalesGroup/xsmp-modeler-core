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
package org.eclipse.xsmp.ui.editor;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;

class XtextAdapterFactoryEditingDomain extends AdapterFactoryEditingDomain
{

  public XtextAdapterFactoryEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack)
  {
    super(adapterFactory, commandStack, (ResourceSet) null);
    resourceSet = new XtextAdapterFactoryEditingDomainResourceSet();
  }

  protected class XtextAdapterFactoryEditingDomainResourceSet extends SynchronizedXtextResourceSet
          implements IEditingDomainProvider
  {
    @Override
    public EditingDomain getEditingDomain()
    {
      return XtextAdapterFactoryEditingDomain.this;
    }
  }

  /**
   * Only the first resource is editable
   */
  @Override
  public boolean isReadOnly(Resource resource)
  {
    final var first = getResourceSet().getResources().get(0);
    return first != resource || super.isReadOnly(resource);
  }
}