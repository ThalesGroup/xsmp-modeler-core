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
package org.eclipse.xsmp.ide.labeling;

import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.Array;
import org.eclipse.xsmp.model.xsmp.Association;
import org.eclipse.xsmp.model.xsmp.AttributeType;
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
import org.eclipse.xsmp.model.xsmp.ProfileReference;
import org.eclipse.xsmp.model.xsmp.ProjectReference;
import org.eclipse.xsmp.model.xsmp.Property;
import org.eclipse.xsmp.model.xsmp.Reference;
import org.eclipse.xsmp.model.xsmp.SourcePath;
import org.eclipse.xsmp.model.xsmp.ToolReference;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.ValueReference;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpLabelProvider
{
  private final PolymorphicDispatcher<String> textDispatcher = new PolymorphicDispatcher<>("text",
          Collections.singletonList(this));

  public String getLabel(EObject element)
  {
    return textDispatcher.invoke(element);
  }

  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  private XsmpUtil xsmpUtil;

  protected StringBuilder text(NamedElement elem, Type type)
  {
    return text(elem, type, null);
  }

  protected StringBuilder text(NamedElement elem, Type type, QualifiedName qn)
  {
    final var label = elem.getName();
    final var sb = new StringBuilder();
    if (label != null)
    {
      sb.append(label);
    }
    if (type != null && !type.eIsProxy())
    {
      sb.append(" : " + qualifiedNameProvider.getFullyQualifiedName(type).toString("::"));
    }
    else if (qn != null)
    {
      sb.append(" : " + qn.toString("::"));
    }
    return sb;
  }

  protected StringBuilder text(NamedElementWithMultiplicity elem, Type type)
  {

    final var text = text((NamedElement) elem, type);

    final var lower = elem.getLower();
    final var upper = elem.getUpper();
    if (lower == 0 && upper == 1)
    {
      text.append("?");
    }
    else if (lower == 1 && upper == 1)
    {
      // keep as is
    }
    else if (lower == upper)
    {
      text.append("[" + lower + "]");
    }
    else if (upper < 0)
    {
      if (lower == 0)
      {
        text.append("[*]");
      }
      else if (lower == 1)
      {
        text.append("[+]");
      }
      else
      {
        text.append("[" + lower + " ... *]");
      }
    }
    else
    {
      text.append("[" + lower + " ... " + upper + "]");
    }

    return text;
  }

  /**
   * @param elem
   *          the object
   * @return the default label for EString
   */
  protected String text(EObject elem)
  {
    return "";
  }

  protected String text(NamedElement elem)
  {
    return elem.getName();
  }

  protected String text(Association elem)
  {
    return text(elem, elem.getType()).toString();
  }

  protected String text(Constant elem)
  {
    return text(elem, elem.getType()).toString();
  }

  protected String text(Container elem)
  {

    return text(elem, elem.getType()).toString();
  }

  protected String text(EventSink elem)
  {
    return text(elem, elem.getType()).toString();
  }

  protected String text(EventSource elem)
  {
    return text(elem, elem.getType()).toString();
  }

  protected String text(Field elem)
  {
    return text(elem, elem.getType()).toString();
  }

  protected String text(AttributeType elem)
  {
    return text(elem, elem.getType()).toString();
  }

  protected String text(EventType elem)
  {
    return text(elem, elem.getEventArgs(), QualifiedName.create("void")).toString();
  }

  protected String text(Float elem)
  {
    return text(elem, elem.getPrimitiveType(), QualifiedNames.Smp_Float64).toString();
  }

  protected String text(Integer elem)
  {
    return text(elem, elem.getPrimitiveType(), QualifiedNames.Smp_Int32).toString();
  }

  protected String text(ValueReference elem)
  {
    return text(elem, elem.getType()).append("*").toString();
  }

  protected String text(Array elem)
  {
    final var label = elem.getName();
    final var sb = new StringBuilder();
    if (label != null)
    {
      sb.append(label);
    }
    final var type = elem.getItemType();
    if (type != null && !type.eIsProxy())
    {
      sb.append(" : " + qualifiedNameProvider.getFullyQualifiedName(type).toString("::"));

      sb.append("[");

      final var size = elem.getSize();
      if (size != null)
      {
        try
        {
          sb.append(Long.toString(xsmpUtil.getInt64(size)));
        }
        catch (final Exception ex)
        {
          // ignore

        }
      }
      sb.append("]");
    }
    return sb.toString();
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

  protected String text(Operation elem)
  {
    final var label = elem.getName();
    final var sb = new StringBuilder();
    if (label != null)
    {
      sb.append(label);
    }
    sb.append("(");

    sb.append(elem.getParameter().stream().map(this::getParameterType)
            .collect(Collectors.joining(", ")));

    sb.append(")");
    sb.append(" : " + (elem.getReturnParameter() == null ? "void"
            : getParameterType(elem.getReturnParameter())));

    return sb.toString();

  }

  protected String text(Parameter elem)
  {
    return text(elem, elem.getType()).toString();
  }

  protected String text(Property elem)
  {
    return text(elem, elem.getType()).toString();
  }

  protected String text(Reference elem)
  {
    return text(elem, elem.getInterface()).toString();
  }

  protected String text(SourcePath ele)
  {
    if (ele.getName() == null || ele.getName().isEmpty())
    {
      return ".";
    }
    return ele.getName();
  }

  protected String text(ProjectReference ele)
  {
    return ele.getName();
  }

  protected String text(ToolReference ele)
  {
    final var tool = ele.getTool();
    if (tool == null || tool.eIsProxy())
    {
      return ele.getName();
    }
    return tool.getDescription();
  }

  protected String text(ProfileReference ele)
  {
    final var profile = ele.getProfile();
    if (profile == null || profile.eIsProxy())
    {
      return ele.getName();
    }
    return profile.getDescription();
  }

}
