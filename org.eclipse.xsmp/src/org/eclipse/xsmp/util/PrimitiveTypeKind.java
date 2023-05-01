package org.eclipse.xsmp.util;

/**
 * Enumeration with SMP primitive type kinds
 */
public enum PrimitiveTypeKind
{
  BOOL("Bool"), CHAR8("Char8"), DATE_TIME("DateTime"), DURATION("Duration"), FLOAT32(
          "Float32"), FLOAT64("Float64"), INT8("Int8"), INT16("Int16"), INT32("Int32"), INT64(
                  "Int64"), STRING8("String8"), UINT8("UInt8"), UINT16("UInt16"), UINT32(
                          "UInt32"), UINT64("UInt64"), ENUM("Int32"), NONE("None");

  public final String label;

  PrimitiveTypeKind(String label)
  {
    this.label = label;
  }
}