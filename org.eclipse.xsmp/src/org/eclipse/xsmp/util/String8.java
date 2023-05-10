/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.util;

import java.math.BigDecimal;
import java.time.Instant;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;

import org.eclipse.xtext.util.Strings;

public final class String8 extends AbstractPrimitiveType<String8>
{
  private final String value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.STRING8;
  }

  private String8(String value)
  {
    this.value = value;
  }

  public static String8 valueOf(String value)
  {
    return new String8(value);
  }

  @Override
  public String getValue()
  {
    return value;
  }

  /**
   * Returns a hash code for this {@code String8}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code String} value represented by this
   *         {@code String8} object.
   */
  @Override
  public int hashCode()
  {
    return value.hashCode();
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code String8} object that
   * contains the same {@code String} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof String8)
    {
      return Strings.equal(value, ((String8) obj).value);
    }
    return false;
  }

  @Override
  public String toString()
  {
    return "\"" + value + "\"";
  }

  @Override
  public Duration durationValue()
  {
    try
    {
      final var d = DatatypeFactory.newInstance().newDuration(value);
      return Duration.valueOf(((BigDecimal) d.getField(DatatypeConstants.SECONDS))
              .multiply(BigDecimal.valueOf(1_000_000_000L)).longValue());
    }
    catch (final Exception e)
    {
      throw new UnsupportedOperationException("Could not convert \"" + value + "\" to a Duration.");
    }
  }

  @Override
  public DateTime dateTimeValue()
  {
    try
    {
      final var i = Instant.parse(value);
      return DateTime.valueOf(i.getEpochSecond() * 1_000_000_000L + i.getNano());
    }
    catch (final Exception e)
    {
      throw new UnsupportedOperationException("Could not convert \"" + value + "\" to a DateTime.");
    }
  }

  @Override
  public String8 string8Value()
  {
    return this;
  }
}