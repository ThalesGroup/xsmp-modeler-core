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
package org.eclipse.xsmp.ui.contentassist;

import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Integer;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.ValueReference;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

public class DefaultValueProvider
{

  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  private XsmpUtil typeUtil;

  public java.lang.String getDefautValue(Type t)
  {

    switch (t.eClass().getClassifierID())
    {
      case XcataloguePackage.VALUE_REFERENCE:
        return getDefautValue(((ValueReference) t).getType());
      case XcataloguePackage.ARRAY:
        return "{" + java.lang.String.join(", ",
                Collections.nCopies(((Array) t).getSize().getInteger(null).intValue(),
                        getDefautValue(((Array) t).getItemType())))
                + "}";
      case XcataloguePackage.ENUMERATION:
        return ((Enumeration) t).getLiteral().stream()
                .map(l -> qualifiedNameProvider.getFullyQualifiedName(l).toString()).findFirst()
                .orElse(null);

      case XcataloguePackage.FLOAT:
        return "0.0";
      case XcataloguePackage.INTEGER:
        final var integer = (Integer) t;
        if (integer.getMinimum() != null)
        {
          return integer.getMinimum().getInteger(null).toString();
        }
        else
          if (integer.getMaximum() != null && 0 > integer.getMaximum().getInteger(null).intValue())
        {
          return integer.getMaximum().getInteger(null).toString();
        }
          else
        {
          return "0";
        }
      case XcataloguePackage.PRIMITIVE_TYPE:
        switch (typeUtil.getPrimitiveType((PrimitiveType) t))
        {
          case Bool:
            return "false";
          case Float32:
          case Float64:
            return "0.0";
          case DateTime:
          case Duration:
          case Int16:
          case Int32:
          case Int64:
          case Int8:
          case UInt16:
          case UInt32:
          case UInt64:
          case UInt8:
            return "0";
          case Char8:
          case String8:
            return "\"\"";
          default:
            break;
        }
        break;
      case XcataloguePackage.STRING:
        return "\"\"";
      case XcataloguePackage.STRUCTURE:
        return "{" + java.lang.String.join(", ",
                ((Structure) t).getMember().stream().filter(Field.class::isInstance)
                        .map(m -> getDefautValue(((Field) m).getType()))
                        .collect(Collectors.joining(", ")))
                + "}";
      default:
        break;

    }

    return null;
  }

}
