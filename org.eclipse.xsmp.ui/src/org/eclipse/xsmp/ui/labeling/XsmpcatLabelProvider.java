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
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.Class;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Container;
import org.eclipse.xsmp.xcatalogue.EventSink;
import org.eclipse.xsmp.xcatalogue.EventSource;
import org.eclipse.xsmp.xcatalogue.EventType;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Float;
import org.eclipse.xsmp.xcatalogue.Integer;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMultiplicity;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Reference;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.ValueReference;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class XsmpcatLabelProvider extends DefaultEObjectLabelProvider
{
  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  private XsmpUtil xsmpUtil;

  @Inject
  protected IImageHelper imageHelper;

  public XsmpcatLabelProvider()
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

  public Object text(NamedElementWithMultiplicity elem, Type type)
  {

    final var text = text((NamedElement) elem, type);

    final var lower = elem.getLower();
    final var upper = elem.getUpper();
    if (lower == 0 && upper == 1)
    {
      text.append("?", StyledString.DECORATIONS_STYLER);
    }
    else if (lower == 1 && upper == 1)
    {
      // keep as is
    }
    else if (lower == upper)
    {
      text.append("[" + lower + "]", StyledString.DECORATIONS_STYLER);
    }
    else if (upper < 0)
    {
      if (lower == 0)
      {
        text.append("[*]", StyledString.DECORATIONS_STYLER);
      }
      else if (lower == 1)
      {
        text.append("[+]", StyledString.DECORATIONS_STYLER);
      }
      else
      {
        text.append("[" + lower + " ... *]", StyledString.DECORATIONS_STYLER);
      }
    }
    else
    {
      text.append("[" + lower + " ... " + upper + "]", StyledString.DECORATIONS_STYLER);
    }

    return text;
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

  public Object text(Association elem)
  {
    return text(elem, elem.getType());
  }

  public Object text(Constant elem)
  {
    return text(elem, elem.getType());
  }

  public Object text(Container elem)
  {

    return text(elem, elem.getType());
  }

  public Object text(EventSink elem)
  {
    return text(elem, elem.getType());
  }

  public Object text(EventSource elem)
  {
    return text(elem, elem.getType());
  }

  public Object text(Field elem)
  {
    return text(elem, elem.getType());
  }

  public Object text(AttributeType elem)
  {
    return text(elem, elem.getType());
  }

  public Object text(EventType elem)
  {
    return text(elem, elem.getEventArgs(), QualifiedName.create("void"));
  }

  public Object text(Float elem)
  {
    return text(elem, elem.getPrimitiveType(), QualifiedNames.Smp_Float64);
  }

  public Object text(Integer elem)
  {
    return text(elem, elem.getPrimitiveType(), QualifiedNames.Smp_Int32);
  }

  public Object text(ValueReference elem)
  {
    return text(elem, elem.getType()).append("*", StyledString.DECORATIONS_STYLER);
  }

  public Object text(Array elem)
  {
    final var label = elem.getName();
    final var styledLabel = new StyledString();
    if (label != null)
    {
      styledLabel.append(label);
    }
    final var type = elem.getItemType();
    if (type != null && !type.eIsProxy())
    {
      styledLabel.append(" : " + qualifiedNameProvider.getFullyQualifiedName(type).toString("::"),
              StyledString.DECORATIONS_STYLER);

      styledLabel.append("[", StyledString.DECORATIONS_STYLER);

      final var size = elem.getSize();
      if (size != null)
      {
        final var value = xsmpUtil.getInteger(size);
        if (value != null)
        {
          styledLabel.append(value.toString(), StyledString.DECORATIONS_STYLER);
        }
      }
      styledLabel.append("]", StyledString.DECORATIONS_STYLER);
    }
    return styledLabel;
  }

  private java.lang.String getParameterType(Parameter p)
  {
    final var label = new StringBuilder();
    if (xsmpUtil.isConst(p))
    {
      label.append("const ");
    }
    final var type = p.getType();
    if (type != null && !type.eIsProxy())
    {
      label.append(qualifiedNameProvider.getFullyQualifiedName(p.getType()).toString("::"));
    }

    switch (xsmpUtil.kind(p))
    {
      case BY_PTR:
        label.append("*");
        break;
      case BY_REF:
        label.append("&");
        break;
      default:
        break;
    }

    return label.toString();

  }

  public Object text(Operation elem)
  {
    final var label = elem.getName();
    final var styledLabel = new StyledString();
    if (label != null)
    {
      styledLabel.append(label);
    }
    styledLabel.append("(");

    styledLabel.append(elem.getParameter().stream().map(this::getParameterType)
            .collect(Collectors.joining(", ")));

    styledLabel.append(")");
    styledLabel.append(
            " : " + (elem.getReturnParameter() == null ? "void"
                    : getParameterType(elem.getReturnParameter())),
            StyledString.DECORATIONS_STYLER);

    return styledLabel;

  }

  public Object text(Parameter elem)
  {
    return text(elem, elem.getType());
  }

  public Object text(Property elem)
  {
    return text(elem, elem.getType());
  }

  public Object text(Reference elem)
  {
    return text(elem, elem.getInterface());
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

  public Object image(Field elem)
  {
    final var image = getImage(
            elem.eClass().getName() + "_" + elem.getRealVisibility().getLiteral() + ".png");

    if (elem.isInput() || elem.isOutput() || elem.isDeprecated())
    {
      final List<Object> images = new ArrayList<>();

      if (elem.isDeprecated())
      {
        images.add(getDeprecatedImage());
      }

      images.add(image);

      if (elem.isInput())
      {
        images.add(getOverlay("input.png"));
      }

      if (elem.isOutput())
      {
        images.add(getOverlay("output.png"));
      }
      return new ComposedImage(images);
    }
    return image;
  }

  public Object image(Constant elem)
  {

    return getImage(elem.eClass().getName() + "_" + elem.getRealVisibility().getLiteral() + ".png");

  }

  public Object image(Operation elem)
  {

    final var image = getImage(
            elem.eClass().getName() + "_" + elem.getRealVisibility().getLiteral() + ".png");

    if (elem.isDeprecated())
    {
      final List<Object> images = new ArrayList<>(2);
      images.add(getDeprecatedImage());
      images.add(image);
      return new ComposedImage(images);
    }
    return image;

  }

  public Object image(Property elem)
  {

    final List<Object> images = new ArrayList<>(4);
    if (elem.isDeprecated())
    {
      images.add(getDeprecatedImage());
    }
    images.add(getImage(
            elem.eClass().getName() + "_" + elem.getRealVisibility().getLiteral() + ".png"));

    switch (elem.getAccess())
    {
      case READ_ONLY:
        images.add(getOverlay("read.png"));
        break;
      case WRITE_ONLY:
        images.add(getOverlay("write.png"));
        break;
      default:
        images.add(getOverlay("read.png"));
        images.add(getOverlay("write.png"));
        break;
    }
    return new ComposedImage(images);
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

  public Object image(VisibilityElement elem)
  {
    final var image = image((NamedElement) elem);

    final var visibility = elem.getRealVisibility();
    if (visibility != VisibilityKind.PUBLIC)
    {
      final var visibilityImage = getOverlay(visibility.getLiteral() + ".png");
      if (image instanceof ComposedImage)
      {
        ((ComposedImage) image).getImages().add(visibilityImage);
        return image;
      }
      final List<Object> images = new ArrayList<>(2);
      images.add(image);
      images.add(visibilityImage);
      return new ComposedImage(images);
    }
    return image;

  }

  private Object image(VisibilityElement elem, boolean isAbstract)
  {
    final var image = image(elem);
    if (isAbstract)
    {
      final var abstractImage = getOverlay("abstract.png");
      if (image instanceof ComposedImage)
      {
        ((ComposedImage) image).getImages().add(abstractImage);
        return image;
      }
      final List<Object> images = new ArrayList<>(2);
      images.add(image);
      images.add(abstractImage);
      return new ComposedImage(images);
    }
    return image;
  }

  public Object image(Class elem)
  {
    return image(elem, elem.isAbstract());
  }

  public Object image(Component elem)
  {
    return image(elem, elem.isAbstract());
  }
}
