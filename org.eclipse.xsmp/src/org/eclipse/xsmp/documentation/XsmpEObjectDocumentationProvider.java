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
package org.eclipse.xsmp.documentation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Specialized documentation provider that return the description of a NamedElement
 *
 * @author daveluy
 */
@Singleton
public class XsmpEObjectDocumentationProvider extends MultiLineCommentDocumentationProvider
{
  @Inject
  private XsmpUtil xsmpUtil;

  /**
   * Return the description in case of a NamedElement or a BuiltIn
   */
  @Override
  public String getDocumentation(EObject o)
  {
    if (o instanceof final Parameter parameter)
    {
      return parameter.getDescription();
    }
    if (o instanceof final NamedElement elem)
    {
      return XsmpdocContentAccess.getHTMLContent(elem);
    }
    if (o instanceof final BuiltInConstant cst)
    {
      final var mapping = xsmpUtil.getSolver().constantMappings.get(cst.getName());

      if (mapping != null)
      {
        return mapping.getDocumentation();
      }
    }
    else if (o instanceof final BuiltInFunction cst)
    {
      final var mapping = xsmpUtil.getSolver().functionMappings.get(cst.getName());

      if (mapping != null)
      {
        return mapping.getDocumentation();
      }
    }
    if (o instanceof final Expression exp)
    {
      final var field = xsmpUtil.getField(exp);

      if (field != null)
      {
        return "value of Field " + field.getName() + " with type "
                + xsmpUtil.fqn(field.getType()).toString();
      }

    }
    // return super in case of an other object
    return super.getDocumentation(o);
  }

}
