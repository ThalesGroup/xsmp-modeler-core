/**
 * *******************************************************************************
 * * Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
 * *
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License 2.0
 * * which accompanies this distribution, and is available at
 * * https://www.eclipse.org/legal/epl-2.0/
 * *
 * * SPDX-License-Identifier: EPL-2.0
 * ******************************************************************************
 */
package org.eclipse.xsmp.xcatalogue.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.xsmp.documentation.Documentation;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

public class MetadatumImplCustom extends MetadatumImpl
{
  private Documentation xsmpcatdoc = new Documentation((String) null);

  @Override
  public Documentation getXsmpcatdoc()
  {
    return new Documentation(xsmpcatdoc);

  }

  @Override
  public void setDocumentation(String newDocumentation)
  {
    final var oldDocumentation = getDocumentation();
    xsmpcatdoc = new Documentation(newDocumentation);
    if (eDeliver())
    {
      eNotify(new ENotificationImpl(this, Notification.SET,
              XcataloguePackage.Literals.METADATUM__DOCUMENTATION, oldDocumentation,
              newDocumentation));
    }
  }

  @Override
  public String getDocumentation()
  {
    return xsmpcatdoc.toString();
  }

} // MetadatumImplCustom
