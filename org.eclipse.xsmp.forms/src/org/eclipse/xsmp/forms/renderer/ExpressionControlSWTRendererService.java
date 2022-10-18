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
package org.eclipse.xsmp.forms.renderer;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

/**
 * UuidControlSWTRendererService which provides the UuidControlRenderer.
 */
public class ExpressionControlSWTRendererService extends AbstractRendererService
{

  @Override
  protected double isApplicable(Object valueType)
  {
    if (valueType instanceof EReference)
    {
      final var eReference = EReference.class.cast(valueType);
      if (XcataloguePackage.Literals.EXPRESSION.isSuperTypeOf(eReference.getEReferenceType()))
      {
        return 10;
      }
    }
    return NOT_APPLICABLE;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#getRendererClass()
   */
  @Override
  public Class< ? extends AbstractSWTRenderer<VControl>> getRendererClass()
  {
    return ExpressionControlRenderer.class;
  }

}
