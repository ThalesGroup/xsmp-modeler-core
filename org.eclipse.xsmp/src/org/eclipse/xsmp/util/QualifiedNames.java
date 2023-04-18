package org.eclipse.xsmp.util;

import org.eclipse.xtext.naming.QualifiedName;

/**
 * Qualified names for all standard SMP types
 */
public final class QualifiedNames
{
  private QualifiedNames()
  {
  }

  public static final QualifiedName Attributes = QualifiedName.create("Attributes");

  public static final QualifiedName Attributes_Static = Attributes.append("Static");

  public static final QualifiedName Attributes_Const = Attributes.append("Const");

  public static final QualifiedName Attributes_Mutable = Attributes.append("Mutable");

  public static final QualifiedName Attributes_ByPointer = Attributes.append("ByPointer");

  public static final QualifiedName Attributes_ByReference = Attributes.append("ByReference");

  public static final QualifiedName Attributes_Abstract = Attributes.append("Abstract");

  public static final QualifiedName Attributes_Virtual = Attributes.append("Virtual");

  public static final QualifiedName Attributes_Constructor = Attributes.append("Constructor");

  public static final QualifiedName Attributes_Forcible = Attributes.append("Forcible");

  public static final QualifiedName Attributes_Failure = Attributes.append("Failure");

  public static final QualifiedName Attributes_ConstGetter = Attributes.append("ConstGetter");

  public static final QualifiedName Attributes_NoConstructor = Attributes.append("NoConstructor");

  public static final QualifiedName Attributes_NoDestructor = Attributes.append("NoDestructor");

  public static final QualifiedName Attributes_SimpleArray = Attributes.append("SimpleArray");

  public static final QualifiedName Attributes_OperatorKind = Attributes.append("OperatorKind");

  public static final QualifiedName Attributes_FieldUpdateKind = Attributes
          .append("FieldUpdateKind");

  public static final QualifiedName Attributes_Operator = Attributes.append("Operator");

  public static final QualifiedName Attributes_View = Attributes.append("View");

  public static final QualifiedName Smp = QualifiedName.create("Smp");

  public static final QualifiedName Smp_Char8 = Smp.append("Char8");

  public static final QualifiedName Smp_String8 = Smp.append("String8");

  public static final QualifiedName Smp_Float32 = Smp.append("Float32");

  public static final QualifiedName Smp_Float64 = Smp.append("Float64");

  public static final QualifiedName Smp_Int8 = Smp.append("Int8");

  public static final QualifiedName Smp_UInt8 = Smp.append("UInt8");

  public static final QualifiedName Smp_Int16 = Smp.append("Int16");

  public static final QualifiedName Smp_UInt16 = Smp.append("UInt16");

  public static final QualifiedName Smp_Int32 = Smp.append("Int32");

  public static final QualifiedName Smp_UInt32 = Smp.append("UInt32");

  public static final QualifiedName Smp_Int64 = Smp.append("Int64");

  public static final QualifiedName Smp_UInt64 = Smp.append("UInt64");

  public static final QualifiedName Smp_Bool = Smp.append("Bool");

  public static final QualifiedName Smp_DateTime = Smp.append("DateTime");

  public static final QualifiedName Smp_Duration = Smp.append("Duration");

  public static final QualifiedName Smp_PrimitiveTypeKind = Smp.append("PrimitiveTypeKind");

  public static final QualifiedName Smp_EventSourceCollection = Smp.append("EventSourceCollection");

  public static final QualifiedName Smp_EntryPointCollection = Smp.append("EntryPointCollection");

  public static final QualifiedName Smp_FactoryCollection = Smp.append("FactoryCollection");

  public static final QualifiedName Smp_FailureCollection = Smp.append("FailureCollection");

  public static final QualifiedName Smp_FieldCollection = Smp.append("FieldCollection");

  public static final QualifiedName Smp_ComponentCollection = Smp.append("ComponentCollection");

  public static final QualifiedName Smp_OperationCollection = Smp.append("OperationCollection");

  public static final QualifiedName Smp_ParameterCollection = Smp.append("ParameterCollection");

  public static final QualifiedName Smp_PropertyCollection = Smp.append("PropertyCollection");

  public static final QualifiedName Smp_AnySimpleArray = Smp.append("AnySimpleArray");

  public static final QualifiedName Smp_ModelCollection = Smp.append("ModelCollection");

  public static final QualifiedName Smp_ServiceCollection = Smp.append("ServiceCollection");

  public static final QualifiedName Smp_ReferenceCollection = Smp.append("ReferenceCollection");

  public static final QualifiedName Smp_ContainerCollection = Smp.append("ContainerCollection");

  public static final QualifiedName Smp_EventSinkCollection = Smp.append("EventSinkCollection");

}
