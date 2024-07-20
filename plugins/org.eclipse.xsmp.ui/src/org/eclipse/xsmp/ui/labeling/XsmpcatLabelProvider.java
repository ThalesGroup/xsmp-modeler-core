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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.xsmp.model.xsmp.Array;
import org.eclipse.xsmp.model.xsmp.Association;
import org.eclipse.xsmp.model.xsmp.AttributeType;
import org.eclipse.xsmp.model.xsmp.Class;
import org.eclipse.xsmp.model.xsmp.Component;
import org.eclipse.xsmp.model.xsmp.Constant;
import org.eclipse.xsmp.model.xsmp.Container;
import org.eclipse.xsmp.model.xsmp.EventSink;
import org.eclipse.xsmp.model.xsmp.EventSource;
import org.eclipse.xsmp.model.xsmp.EventType;
import org.eclipse.xsmp.model.xsmp.Field;
import org.eclipse.xsmp.model.xsmp.Float;
import org.eclipse.xsmp.model.xsmp.Integer;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.NamedElementWithMultiplicity;
import org.eclipse.xsmp.model.xsmp.Operation;
import org.eclipse.xsmp.model.xsmp.Parameter;
import org.eclipse.xsmp.model.xsmp.Property;
import org.eclipse.xsmp.model.xsmp.Reference;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.ValueReference;
import org.eclipse.xsmp.model.xsmp.VisibilityElement;
import org.eclipse.xsmp.model.xsmp.VisibilityKind;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class XsmpcatLabelProvider extends XsmpLabelProvider
{
  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  private XsmpUtil xsmpUtil;

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
        try
        {
          styledLabel.append(Long.toString(xsmpUtil.getInt64(size)),
                  StyledString.DECORATIONS_STYLER);
        }
        catch (final Exception ex)
        {
          // ignore

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

    if (xsmpUtil.isByPointer(p))
    {
      label.append("*");
    }
    else if (xsmpUtil.isByReference(p))
    {
      label.append("&");
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

  public Object image(Field elem)
  {
    final var image = getImage(
            elem.eClass().getName() + "_" + elem.getRealVisibility().getLiteral() + ".png");

    if (elem.isInput() || elem.isOutput() || elem.isDeprecated())
    {
      final List<ImageDescriptor> overlay = new ArrayList<>();

      if (elem.isDeprecated())
      {
        overlay.add(getDeprecatedImage());
      }

      if (elem.isInput())
      {
        overlay.add(getOverlay("input.png"));
      }

      if (elem.isOutput())
      {
        overlay.add(getOverlay("output.png"));
      }
      return new DecorationOverlayIcon(image, overlay.toArray(new ImageDescriptor[0]));
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
      final var overlay = new ImageDescriptor[1];
      overlay[0] = getDeprecatedImage();
      return new DecorationOverlayIcon(image, overlay);
    }
    return image;

  }

  public Object image(Property elem)
  {

    final List<ImageDescriptor> overlay = new ArrayList<>(4);
    if (elem.isDeprecated())
    {
      overlay.add(getDeprecatedImage());
    }
    final var image = getImage(
            elem.eClass().getName() + "_" + elem.getRealVisibility().getLiteral() + ".png");

    switch (elem.getAccess())
    {
      case READ_ONLY:
        overlay.add(getOverlay("read.png"));
        break;
      case WRITE_ONLY:
        overlay.add(getOverlay("write.png"));
        break;
      default:
        overlay.add(getOverlay("read.png"));
        overlay.add(getOverlay("write.png"));
        break;
    }

    return new DecorationOverlayIcon(image, overlay.toArray(new ImageDescriptor[0]));
  }

  private Object image(VisibilityElement elem, boolean isAbstract)
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
    if (isAbstract)
    {
      overlay.add(getOverlay("abstract.png"));
    }
    if (overlay.isEmpty())
    {
      return image;
    }
    return new DecorationOverlayIcon(image, overlay.toArray(new ImageDescriptor[0]));
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
