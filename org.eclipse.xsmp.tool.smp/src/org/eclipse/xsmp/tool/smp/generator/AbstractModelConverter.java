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
package org.eclipse.xsmp.tool.smp.generator;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;

/**
 * @author daveluy
 */
public abstract class AbstractModelConverter extends AbstractGenerator
{

  private static final ElementCreator elementCreator = new ElementCreator();

  private final PolymorphicDispatcher<Void> copyDispatcher = createCopyPolymorhicDispatcher();

  // Error handler dispatches to default method in case of a missing method in concrete formatter
  protected class EObjectErrorHandler implements ErrorHandler<Void>
  {
    @Override
    public Void handle(Object[] params, Throwable e)
    {
      if (e instanceof NoSuchMethodException && params.length == 2 && params[0] instanceof EObject
              && params[1] instanceof EObject)
      {
        copy((EObject) params[0], (EObject) params[1]);
        return null;
      }
      return Exceptions.throwUncheckedException(e);
    }
  }

  protected void copyProxyURI(EObject eObject, EObject copyEObject)
  {
    if (eObject.eIsProxy())
    {
      ((InternalEObject) copyEObject).eSetProxyURI(((InternalEObject) eObject).eProxyURI());
    }
  }

  /**
   * Convert an EObject from xsmpcat to smpcat model
   *
   * @param eObject
   *          the object to convert
   * @return the converted object
   */
  public EObject copy(final EObject eObject)
  {
    if (eObject == null)
    {
      return null;
    }
    final var copyEObject = elementCreator.doSwitch(eObject);
    if (copyEObject != null)
    {
      copyDispatcher.invoke(eObject, copyEObject);
      copyProxyURI(eObject, copyEObject);
    }
    return copyEObject;
  }

  /**
   * Fall-back for types that are not handled by a subclasse's dispatch method.
   */
  protected void copy(EObject src, EObject dest)
  {

  }

  /**
   * Override if you like to specify formatting methods in different way then default (using
   * annotations or similar).
   */
  protected PolymorphicDispatcher<Void> createCopyPolymorhicDispatcher()
  {
    return new PolymorphicDispatcher<>(Collections.singletonList(this),
            m -> "copy".equals(m.getName()) && m.getParameterCount() == 2
                    && m.getParameterTypes()[0] != EObject.class
                    && m.getParameterTypes()[1] != EObject.class,
            new EObjectErrorHandler());
  }
}
