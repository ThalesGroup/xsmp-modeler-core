/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;

/**
 * @author daveluy
 */
public class IntegerLiteralValueConverter extends AbstractLexerBasedConverter<String>
{

  private static final BigInteger MN = BigInteger.valueOf(60_000_000_000L);

  private static final BigInteger HOUR = MN.multiply(BigInteger.valueOf(60));

  private static final BigInteger DAY = HOUR.multiply(BigInteger.valueOf(24));

  private static final BigInteger YEAR = BigDecimal.valueOf(365.25).multiply(new BigDecimal(DAY))
          .toBigInteger();

  @Override
  public String toString(String value)
  {
    return value;
  }

  @Override
  public String toValue(String string, INode node) throws ValueConverterException
  {
    if (string.isEmpty())
    {
      return string;
    }
    final var length = string.length();

    final var firstChar = string.charAt(0);
    final var lastChar = string.charAt(length - 1);
    // check that integer is decimal (starts with 1 ... 9) and a suffix is present
    if (firstChar >= '0' && firstChar <= '9' && (lastChar < '0' || lastChar > '9'))
    {

      switch (lastChar)
      {
        case 's':
          switch (string.charAt(length - 2))
          {
            case 'n': // ns
              return string.substring(0, length - 2);
            case 'u': // us
              return string.substring(0, length - 2) + "'000L";
            case 'm': // ms
              return string.substring(0, length - 2) + "'000'000L";
            default: // s
              return string.substring(0, length - 1) + "'000'000'000L";
          }
        case 'n': // 'mn'
          return new BigInteger(string.substring(0, length - 2).replace("'", "")).multiply(MN)
                  .toString() + "L";
        case 'h': // h
          return new BigInteger(string.substring(0, length - 1).replace("'", "")).multiply(HOUR)
                  .toString() + "L";
        case 'd': // d
          final var secondChar = string.charAt(1);
          switch (secondChar)
          {
            case 'x':
            case 'X':
              return string;
            default:
              return new BigInteger(string.substring(0, length - 1).replace("'", "")).multiply(DAY)
                      .toString() + "L";
          }
        case 'y': // y
          return new BigInteger(string.substring(0, length - 1).replace("'", "")).multiply(YEAR)
                  .toString() + "L";
        default:
          break;
      }
    }

    return string;
  }
}
