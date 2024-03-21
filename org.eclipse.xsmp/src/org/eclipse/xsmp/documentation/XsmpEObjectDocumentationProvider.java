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
import org.eclipse.xsmp.model.xsmp.BuiltInConstant;
import org.eclipse.xsmp.model.xsmp.BuiltInFunction;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.Parameter;
import org.eclipse.xsmp.util.XsmpUtil;
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

  private String cleanHtml(String content)
  {
    // if the format is html, extract text between body tags
    while (true)
    {
      var start = content.indexOf("<html>");
      var end = content.indexOf("<body>");
      if (start == -1 || end == -1)
      {
        break;
      }
      content = content.replace(content.substring(start, end + 6), "");
      start = content.indexOf("</body>");
      end = content.indexOf("</html>");
      if (start != -1 && end != -1)
      {
        content = content.replace(content.substring(start, end + 7), "");
      }
    }

    return content;
  }

  /**
   * Return the description in case of a NamedElement or a BuiltIn
   */
  @Override
  public String getDocumentation(EObject o)
  {
    if (o instanceof Parameter)
    {
      return cleanHtml(((Parameter) o).getDescription());
    }
    if (o instanceof NamedElement)
    {
      return cleanHtml(XsmpdocContentAccess.getHTMLContent((NamedElement) o));
    }
    if (o instanceof BuiltInConstant)
    {
      final var mapping = xsmpUtil.getSolver().constantMappings
              .get(((BuiltInConstant) o).getName());

      if (mapping != null)
      {
        return mapping.getDocumentation();
      }
    }
    else if (o instanceof BuiltInFunction)
    {
      final var mapping = xsmpUtil.getSolver().functionMappings
              .get(((BuiltInFunction) o).getName());

      if (mapping != null)
      {
        return mapping.getDocumentation();
      }
    }
    if (o instanceof Expression)
    {
      final var field = xsmpUtil.getField((Expression) o);

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
