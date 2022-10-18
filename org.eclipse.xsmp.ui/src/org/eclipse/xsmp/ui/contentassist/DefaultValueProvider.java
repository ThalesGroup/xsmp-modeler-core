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

import org.eclipse.xsmp.util.Solver;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.Field;
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
                Collections.nCopies(Solver.INSTANCE.getInteger(((Array) t).getSize()).intValue(),
                        getDefautValue(((Array) t).getItemType())))
                + "}";
      case XcataloguePackage.ENUMERATION:
        return ((Enumeration) t).getLiteral().stream()
                .map(l -> qualifiedNameProvider.getFullyQualifiedName(l).toString()).findFirst()
                .orElse(null);

      case XcataloguePackage.FLOAT:
        return "0.0";
      case XcataloguePackage.INTEGER:
        return "0";
      case XcataloguePackage.PRIMITIVE_TYPE:
        switch (typeUtil.getPrimitiveType((PrimitiveType) t))
        {
          case BOOL:
            return "false";
          case FLOAT32:
            return "0.0f";
          case FLOAT64:
            return "0.0";
          case DATE_TIME:
          case DURATION:
          case INT16:
          case INT32:
          case INT64:
          case INT8:
          case UINT16:
          case UINT32:
          case UINT64:
          case UINT8:
            return "0";
          case CHAR8:
          case STRING8:
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
