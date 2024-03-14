/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
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
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsmp.model.xsmp.AbstractPath;
import org.eclipse.xsmp.model.xsmp.Call;
import org.eclipse.xsmp.model.xsmp.ComponentTypeReference;
import org.eclipse.xsmp.model.xsmp.Configuration;
import org.eclipse.xsmp.model.xsmp.Connection;
import org.eclipse.xsmp.model.xsmp.FactoryTypeReference;
import org.eclipse.xsmp.model.xsmp.InitEntryPoint;
import org.eclipse.xsmp.model.xsmp.Instance;
import org.eclipse.xsmp.model.xsmp.LoadDirective;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.Schedule;
import org.eclipse.xsmp.model.xsmp.Subscribtion;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects.
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class XsmpasbLabelProvider extends DefaultEObjectLabelProvider
{
  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  private XsmpUtil xsmpUtil;

  @Inject
  protected IImageHelper imageHelper;

  public XsmpasbLabelProvider()
  {
    super(null);
  }

  protected StyledString text(NamedElement elem, Type type)
  {
    return text(elem, type, null);
  }

  protected StyledString text(NamedElement elem, Type type, QualifiedName qn)
  {
    final var label = elem.getName();
    final var styledLabel = new StyledString();
    if (label != null)
    {
      styledLabel.append(label);
    }
    if (type != null && !type.eIsProxy())
    {
      styledLabel.append(" : " + qualifiedNameProvider.getFullyQualifiedName(type).toString("::"),
              StyledString.DECORATIONS_STYLER);
    }
    else if (qn != null)
    {
      styledLabel.append(" : " + qn.toString("::"), StyledString.DECORATIONS_STYLER);
    }
    return styledLabel;
  }

  public String text(NamedElement elem)
  {
    return elem.getName();
  }

  public Object text(Configuration elem)
  {
    return text(elem.getField());
  }

  public Object text(Schedule elem)
  {
    return text(elem.getEntryPoint());
  }

  public Object text(InitEntryPoint elem)
  {
    return text(elem.getEntryPoint());
  }

  public Object text(Subscribtion elem)
  {
    return text(elem.getEntryPoint());

  }

  public Object text(Call elem)
  {
    return text(elem.getElement());
  }

  public Object text(Connection elem)
  {
    return text(elem.getFrom()).append(" => ").append(getText(elem.getTo()));

  }

  public Object text(LoadDirective elem)
  {
    final var styledLabel = new StyledString("load");

    final var label = elem.getName();
    if (label != null)
    {
      styledLabel.append(" : " + XsmpUtil.stringLiteralToString(label),
              StyledString.DECORATIONS_STYLER);

    }
    else
    {
      final var catalogue = elem.getCatalogue();
      if (catalogue != null && !catalogue.eIsProxy())
      {
        styledLabel.append(" : " + catalogue.getName(), StyledString.DECORATIONS_STYLER);

      }
    }

    return styledLabel;
  }

  public Object text(Instance elem)
  {
    final var styledLabel = new StyledString();
    final var fqn = xsmpUtil.fqn(elem);
    final var parentFqn = xsmpUtil
            .fqn(EcoreUtil2.getContainerOfType(elem.getDeclaration(), NamedElement.class));
    if (fqn.getSegmentCount() > parentFqn.getSegmentCount())
    {
      styledLabel.append(fqn.skipFirst(parentFqn.getSegmentCount()).toString("."));

    }
    else
    {
      styledLabel.append(fqn.toString("."));
    }

    final var type = elem.getType();
    if (type instanceof final ComponentTypeReference ref)
    {
      final var cmp = ref.getComponent();
      if (cmp != null && !cmp.eIsProxy())
      {
        styledLabel.append(" : " + xsmpUtil.fqn(ref.getCatalogue()) + "::" + xsmpUtil.fqn(cmp),
                StyledString.DECORATIONS_STYLER);
      }
    }
    if (type instanceof final FactoryTypeReference ref)
    {
      final var factory = ref.getFactory();
      if (factory != null && !factory.eIsProxy())
      {
        styledLabel.append(" : " + xsmpUtil.fqn(factory), StyledString.DECORATIONS_STYLER);
      }
    }
    return styledLabel;
  }

  public StyledString text(AbstractPath elem)
  {

    final var last = XsmpUtil.getLastSegment(elem);
    final var fqn = xsmpUtil.fqn(last).skipFirst(xsmpUtil
            .fqn(EcoreUtil2.getContainerOfType(elem, NamedElement.class)).getSegmentCount());

    final var styledLabel = new StyledString();

    if (fqn.isEmpty())
    {
      styledLabel.append("this");
    }
    else
    {
      styledLabel.append(fqn.toString());
    }

    // if (last instanceof final Path p)
    // {
    // final var type = xsmpUtil.getType(p.getSegment());
    // if (type != null && !type.eIsProxy())
    // {
    // styledLabel.append(" : " + qualifiedNameProvider.getFullyQualifiedName(type).toString("::"),
    // StyledString.DECORATIONS_STYLER);
    // }
    // }

    return styledLabel;

  }

  @Override
  protected Image convertToImage(Object imageDescription)
  {
    if (imageDescription instanceof ComposedImage)
    {
      try
      {
        return ExtendedImageRegistry.INSTANCE.getImage(imageDescription);
      }
      catch (final Exception e)
      {
        // ignore
      }
    }
    return super.convertToImage(imageDescription);
  }

  private Image doGetImage(String path)
  {
    var image = imageHelper.getImage(path);
    if (image == null)
    {
      image = imageHelper.getImage("platform:/plugin/"
              + org.eclipse.xsmp.ui.internal.XsmpActivator.PLUGIN_ID + "/" + path);
    }
    return image;
  }

  private Image getImage(String path)
  {
    return doGetImage("full/obj16/" + path);
  }

  private Image getOverlay(String path)
  {
    return doGetImage("full/ovr16/" + path);
  }

  private Image getDeprecatedImage()
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
      final List<Object> images = new ArrayList<>();
      images.add(getDeprecatedImage());
      images.add(image);
      return new ComposedImage(images);
    }

    return image;
  }

}
