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
package org.eclipse.xsmp.tool.smp.core.types.impl;

import org.eclipse.xsmp.tool.smp.core.elements.NamedElement;

public class ElementReferenceImplCustom<T extends NamedElement> extends ElementReferenceImpl<T>
{

  @SuppressWarnings("unchecked")
  @Override
  public T getRef()
  {
    try
    {
      return (T) getReference();
    }
    catch (final ClassCastException e)
    {
      return null;
    }
  }

  @Override
  public void setRef(T newTypedRef)
  {

    setReference(newTypedRef);

  }

} // TypeReferenceImpl
