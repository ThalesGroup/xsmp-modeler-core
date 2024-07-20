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
package org.eclipse.xsmp.tool.adoc.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xsmp.model.xsmp.CollectionLiteral;
import org.eclipse.xsmp.model.xsmp.DesignatedInitializer;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.util.Solver.SolverException;
import org.eclipse.xsmp.util.XsmpUtil;

public class ADocUtil extends XsmpUtil
{
  public String getShortValue(Expression e)
  {
    if (e instanceof final CollectionLiteral cl)
    {
      final var defaultValues = getShortValuesArray(cl);
      return removeLeadingEscape(defaultValues);
    }
    try
    {
      final var value = getValue(e);
      if (value != null)
      {
        return value.toString();
      }
    }
    catch (final SolverException se)
    {
      // ignore
    }

    return null;
  }

  public String getShortValuesArray(CollectionLiteral c)
  {
    final List<String> results = new ArrayList<>();

    for (final Expression expression : c.getElements())
    {
      if (expression instanceof final CollectionLiteral cl)
      {
        results.add(getShortValuesArray(cl));
      }
      else if (expression instanceof final DesignatedInitializer di)
      {
        results.add(getShortValue(di.getExpr()));
      }
      else
      {
        results.add(super.getValue(expression).toString());
      }
    }

    final var str = results.toString();
    if (str.startsWith("[["))
    {
      return "\\" + str;
    }

    return str;
  }

  private String removeLeadingEscape(String str)
  {
    if (str.startsWith("\\"))
    {
      return str.substring(1);
    }
    return str;
  }
}
