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
package org.eclipse.xsmp.ui.labeling;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.VisibilityElement;
import org.eclipse.xsmp.model.xsmp.VisibilityKind;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

/**
 * Provides labels for EObjects. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public abstract class XsmpLabelProvider extends DefaultEObjectLabelProvider
{

  protected XsmpLabelProvider()
  {
    super(null);
  }

  /**
   * @param elem
   *          the object
   * @return the default label for EObject
   */
  public Object text(EObject elem)
  {
    return "";
  }

  public Object text(NamedElement elem)
  {
    return elem.getName();
  }

  protected Image doGetImage(String path)
  {

    var image = convertToImage(path);
    if (image == null)
    {
      image = convertToImage("platform:/plugin/"
              + org.eclipse.xsmp.ui.internal.XsmpActivator.PLUGIN_ID + "/" + path);
    }
    return image;
  }

  protected ImageDescriptor doGetImageDescriptor(String path)
  {
    var image = convertToImageDescriptor(path);
    if (image == null)
    {
      image = convertToImageDescriptor("platform:/plugin/"
              + org.eclipse.xsmp.ui.internal.XsmpActivator.PLUGIN_ID + "/" + path);
    }
    return image;
  }

  protected Image getImage(String path)
  {

    return doGetImage("full/obj16/" + path);
  }

  protected ImageDescriptor getOverlay(String path)
  {
    return doGetImageDescriptor("full/ovr16/" + path);
  }

  protected ImageDescriptor getDeprecatedImage()
  {
    return getOverlay("deprecated.png");
  }

  public Image image(EObject elem)
  {
    return getImage(elem.eClass().getName() + ".png");

  }

  public Object image(NamedElement elem)
  {
    final var image = image((EObject) elem);
    if (elem.isDeprecated())
    {
      final var overlay = new ImageDescriptor[1];
      overlay[0] = getDeprecatedImage();
      return new DecorationOverlayIcon(image, overlay);
    }

    return image;
  }

  public Object image(VisibilityElement elem)
  {
    final var image = image((EObject) elem);
    final List<ImageDescriptor> overlay = new ArrayList<>();

    if (elem.isDeprecated())
    {
      overlay.add(getDeprecatedImage());
    }

    final var visibility = elem.getRealVisibility();
    if (visibility != VisibilityKind.PUBLIC)
    {
      overlay.add(getOverlay(visibility.getLiteral() + ".png"));
    }

    if (overlay.isEmpty())
    {
      return image;
    }
    return new DecorationOverlayIcon(image, overlay.toArray(new ImageDescriptor[0]));

  }

}
