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

public final class EnumerationLiteral extends AbstractPrimitiveType<EnumerationLiteral>
{
  private final org.eclipse.xsmp.model.xsmp.EnumerationLiteral value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.ENUM;
  }

  private EnumerationLiteral(org.eclipse.xsmp.model.xsmp.EnumerationLiteral value)
  {
    this.value = value;
  }

  public static EnumerationLiteral valueOf(org.eclipse.xsmp.model.xsmp.EnumerationLiteral value)
  {
    return new EnumerationLiteral(value);
  }

  public org.eclipse.xsmp.model.xsmp.EnumerationLiteral getValue()
  {
    return value;
  }

  @Override
  public EnumerationLiteral enumerationLiteralValue()
  {
    return this;
  }

  /**
   * Returns a hash code for this {@code EnumerationLiteral}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code int} value represented by this
   *         {@code EnumerationLiteral} object.
   */
  @Override
  public int hashCode()
  {
    return value.hashCode();
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code EnumerationLiteral} object that
   * contains the same {@code org.eclipse.xsmp.model.xsmp.EnumerationLiteral} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof EnumerationLiteral)
    {
      return value == ((EnumerationLiteral) obj).value;
    }
    return false;
  }

  @Override
  public String toString()
  {
    return value.getName();
  }
}