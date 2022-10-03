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
import org.eclipse.xsmp.util.ExpressionSolver;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;

import com.google.inject.Singleton;

/**
 * Specialized documentation provider that return the description of a NamedElement
 *
 * @author daveluy
 */
@Singleton
public class XsmpcatEObjectDocumentationProvider extends MultiLineCommentDocumentationProvider
{

  /**
   * Return the description in case of a NamedElement or a BuiltIn
   */
  @Override
  public String getDocumentation(EObject o)
  {
    if (o instanceof Parameter)
    {
      return ((Parameter) o).getDescription();
    }
    if (o instanceof NamedElement)
    {
      return XsmpcatdocContentAccess.getHTMLContent((NamedElement) o);
    }
    if (o instanceof BuiltInConstant)
    {
      final var cst = (BuiltInConstant) o;

      final var mapping = ExpressionSolver.constantMappings.get(cst.getName());

      if (mapping != null)
      {
        return mapping.getDocumentation();
      }
    }
    else if (o instanceof BuiltInFunction)
    {
      final var cst = (BuiltInFunction) o;

      final var mapping = ExpressionSolver.functionMappings.get(cst.getName());

      if (mapping != null)
      {
        return mapping.getDocumentation();
      }
    }
    // return super in case of an other object
    return super.getDocumentation(o);
  }

}
