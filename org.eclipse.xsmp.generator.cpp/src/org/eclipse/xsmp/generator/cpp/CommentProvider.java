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
package org.eclipse.xsmp.generator.cpp;

import java.util.stream.Collectors;

import org.eclipse.xsmp.documentation.TagElement;
import org.eclipse.xsmp.documentation.TextElement;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.Operation;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

public class CommentProvider
{

  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  public String get(NamedElement fElement)
  {
    final var xsmpcatdoc = fElement.getMetadatum().getXsmpcatdoc();
    if (xsmpcatdoc == null)
    {
      return null;
    }

    final var sb = new StringBuilder();
    final var tags = xsmpcatdoc.tags();
    var newLine = false;
    for (final TagElement tag : tags)
    {
      final var tagName = tag.getTagName();
      if (tagName == null)
      {
        tag.fragments().forEach(t -> sb.append("/// ").append(t.getText()).append("\n"));
        newLine = true;
      }
      else if (!"@uuid".equals(tagName))
      {

        if (newLine)
        {
          sb.append("/// \n");
          newLine = false;
        }
        sb.append("/// ").append(tag.getTagName()).append(" ");
        sb.append(tag.fragments().stream().map(TextElement::getText)
                .collect(Collectors.joining("\n///         ")));
        sb.append("\n");
      }
    }
    if (fElement instanceof final Operation fMethod)
    {
      for (final var p : fMethod.getRaisedException())
      {
        if (newLine)
        {
          sb.append("/// \n");
          newLine = false;
        }
        handleExceptionTag(p, sb);
      }
    }
    return sb.toString();
  }

  protected void handleExceptionTag(Type p, StringBuilder sb)
  {
    final var fqn = qualifiedNameProvider.getFullyQualifiedName(p);
    if (fqn != null)
    {
      sb.append("/// @throws ::").append(fqn.toString("::")).append("\n");
    }
  }

}
