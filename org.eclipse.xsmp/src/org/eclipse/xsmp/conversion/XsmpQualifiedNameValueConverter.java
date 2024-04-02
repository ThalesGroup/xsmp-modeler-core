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
package org.eclipse.xsmp.conversion;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.QualifiedNameValueConverter;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Singleton;

/**
 * Qualified name value converter
 *
 * @author daveluy
 */
@Singleton
public class XsmpQualifiedNameValueConverter extends QualifiedNameValueConverter
{

  @Override
  protected String getDelegateRuleName()
  {
    return "ValidID";
  }

  @Override
  public String toValue(String string, INode node) throws ValueConverterException
  {
    final var buffer = new StringBuilder();
    var isFirst = true;
    if (node != null)
    {
      for (final INode child : node.getAsTreeIterable())
      {
        final var grammarElement = child.getGrammarElement();
        if (isDelegateRuleCall(grammarElement) || isWildcardLiteral(grammarElement))
        {
          if (!isFirst)
          {
            buffer.append(getValueNamespaceDelimiter());
          }
          isFirst = false;
          if (isDelegateRuleCall(grammarElement))
          {
            for (final ILeafNode leafNode : child.getLeafNodes())
            {
              if (!leafNode.isHidden())
              {
                buffer.append(delegateToValue(leafNode));
              }
            }
          }
          else
          {
            buffer.append(getWildcardLiteral());
          }
        }
      }
    }
    else
    {
      for (final String segment : Strings.split(string, getStringNamespaceDelimiter()))
      {
        if (!isFirst)
        {
          buffer.append(getValueNamespaceDelimiter());
        }
        isFirst = false;
        if (getWildcardLiteral().equals(segment))
        {
          buffer.append(getWildcardLiteral());
        }
        else
        {
          buffer.append(
                  (String) valueConverterService.toValue(segment, getDelegateRuleName(), null));
        }
      }
    }
    return buffer.toString();
  }
}
