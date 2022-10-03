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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.eclipse.jface.text.templates.SimpleTemplateVariableResolver;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContextType;

public class XsmpcatTemplateContextType extends XtextTemplateContextType
{

  /**
   * The uuid variable evaluates to a random UUID.
   */
  public static class Uuid extends SimpleTemplateVariableResolver
  {
    /**
     * Creates a new user name variable
     */
    public Uuid()
    {
      super("uuid", "A random UUID");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String resolve(TemplateContext context)
    {
      return UUID.randomUUID().toString();
    }
  }

  /**
   * The current gregorian date.
   */
  public static class GregorianDate extends SimpleTemplateVariableResolver
  {
    /**
     * Creates a new user name variable
     */
    public GregorianDate()
    {
      super("gdate", "The current gregorian date");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String resolve(TemplateContext context)
    {
      return Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addDefaultTemplateVariables()
  {
    super.addDefaultTemplateVariables();

    addResolver(new Uuid());
    addResolver(new GregorianDate());
  }
}
