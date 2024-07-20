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
package org.eclipse.xsmp.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.xsmp.model.xsmp.XsmpFactory;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.ui.IImageHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

@ExtendWith(InjectionExtension.class)
@InjectWith(XsmpUiInjectorProvider.class)
class LabelProviderTest
{
  @Inject
  private ILabelProvider labelProvider;

  @Inject
  private IImageHelper imageHelper;

  private final XsmpFactory factory = XsmpFactory.eINSTANCE;

  @Test
  void catalogue()
  {
    final var elem = factory.createCatalogue();
    elem.setName("name");
    hasLabel(elem, "name");
    hasImage(elem, "Catalogue.png");
  }

  @Test
  void namespace()
  {
    final var elem = factory.createNamespace();
    elem.setName("name");
    hasLabel(elem, "name");
    hasImage(elem, "Namespace.png");
  }

  @Test
  void enumeration()
  {
    final var elem = factory.createEnumeration();
    elem.setName("name");
    hasLabel(elem, "name");
    hasImage(elem, "Enumeration.png");
  }

  private void hasImage(EObject eObject, String image)
  {
    final var actual = labelProvider.getImage(eObject);
    final var expected = imageHelper.getImage("full/obj16/" + image);
    assertEquals(expected, actual);
  }

  private void hasLabel(EObject eObject, String expected)
  {
    final var actual = labelProvider.getText(eObject);
    assertEquals(expected, actual);
  }
}
