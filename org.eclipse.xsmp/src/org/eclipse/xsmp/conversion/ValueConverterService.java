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

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;
import org.eclipse.xtext.conversion.impl.AbstractIDValueConverter;
import org.eclipse.xtext.conversion.impl.KeywordAlternativeConverter;

import com.google.inject.Inject;

/**
 * @author daveluy
 */
public class ValueConverterService extends AbstractDeclarativeValueConverterService
{

  @Inject
  private XsmpcatQualifiedNameValueConverter qualifiedNameValueConverter;

  @Inject
  private KeywordAlternativeConverter validIDConverter;

  /**
   * Converter for QualifiedName
   *
   * @return the QualifiedName converter
   */
  @ValueConverter(rule = "QualifiedName")
  public IValueConverter<String> getQualifiedNameValueConverter()
  {
    return qualifiedNameValueConverter;
  }

  /**
   * Converter for ValidID
   *
   * @return the ValidID converter
   */
  @ValueConverter(rule = "ValidID")
  public IValueConverter<String> getValidIDConverter()
  {
    return validIDConverter;
  }

  @Inject
  private IntegerLiteralValueConverter integerLiteralValueConverter;

  /**
   * @return the INTEGER_LITERAL value converter
   */
  @ValueConverter(rule = "INTEGER_LITERAL")
  public IValueConverter<String> getIntegerLiteralConverter()
  {
    return integerLiteralValueConverter;
  }

  @Inject
  private IntegerLiteralValueConverter terminalsIntegerLiteralValueConverter;

  /**
   * @return the INTEGER_LITERAL value converter
   */
  @ValueConverter(rule = "org.eclipse.xsmp.Xsmpcat.INTEGER_LITERAL")
  public IValueConverter<String> getTerminalsIntegerLiteralConverter()
  {
    return terminalsIntegerLiteralValueConverter;
  }

  @Inject
  private AbstractIDValueConverter idValueConverter;

  /**
   * @return the ID value converter
   */
  @ValueConverter(rule = "ID")
  public IValueConverter<String> getIdConverter()
  {
    return idValueConverter;
  }

  @Inject
  private AbstractIDValueConverter terminalsIdValueConverter;

  /**
   * @return the ID value converter
   */
  @ValueConverter(rule = "org.eclipse.xsmp.Xsmpcat.ID")
  public IValueConverter<String> getTerminalsIdConverter()
  {
    return terminalsIdValueConverter;
  }
}
