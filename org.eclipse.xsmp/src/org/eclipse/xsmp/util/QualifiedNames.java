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

  public static final QualifiedName Attributes_OperatorKind_None = Attributes_OperatorKind
          .append("None");

  public static final QualifiedName Attributes_OperatorKind_Positive = Attributes_OperatorKind
          .append("Positive");

  public static final QualifiedName Attributes_OperatorKind_Negative = Attributes_OperatorKind
          .append("Negative");

  public static final QualifiedName Attributes_OperatorKind_Assign = Attributes_OperatorKind
          .append("Assign");

  public static final QualifiedName Attributes_OperatorKind_Add = Attributes_OperatorKind
          .append("Add");

  public static final QualifiedName Attributes_OperatorKind_Subtract = Attributes_OperatorKind
          .append("Subtract");

  public static final QualifiedName Attributes_OperatorKind_Multiply = Attributes_OperatorKind
          .append("Multiply");

  public static final QualifiedName Attributes_OperatorKind_Divide = Attributes_OperatorKind
          .append("Divide");

  public static final QualifiedName Attributes_OperatorKind_Remainder = Attributes_OperatorKind
          .append("Remainder");

  public static final QualifiedName Attributes_OperatorKind_Greater = Attributes_OperatorKind
          .append("Greater");

  public static final QualifiedName Attributes_OperatorKind_Less = Attributes_OperatorKind
          .append("Less");

  public static final QualifiedName Attributes_OperatorKind_Equal = Attributes_OperatorKind
          .append("Equal");

  public static final QualifiedName Attributes_OperatorKind_NotGreater = Attributes_OperatorKind
          .append("NotGreater");

  public static final QualifiedName Attributes_OperatorKind_NotLess = Attributes_OperatorKind
          .append("NotLess");

  public static final QualifiedName Attributes_OperatorKind_NotEqual = Attributes_OperatorKind
          .append("NotEqual");

  public static final QualifiedName Attributes_OperatorKind_Indexer = Attributes_OperatorKind
          .append("Indexer");

  public static final QualifiedName Attributes_OperatorKind_Sum = Attributes_OperatorKind
          .append("Sum");

  public static final QualifiedName Attributes_OperatorKind_Difference = Attributes_OperatorKind
          .append("Difference");

  public static final QualifiedName Attributes_OperatorKind_Product = Attributes_OperatorKind
          .append("Product");

  public static final QualifiedName Attributes_OperatorKind_Quotient = Attributes_OperatorKind
          .append("Quotient");

  public static final QualifiedName Attributes_OperatorKind_Module = Attributes_OperatorKind
          .append("Module");

  public static final QualifiedName Attributes_FieldUpdateKind = Attributes
          .append("FieldUpdateKind");

  public static final QualifiedName Attributes_Operator = Attributes.append("Operator");

  public static final QualifiedName Attributes_View = Attributes.append("View");
  
  public static final QualifiedName Attributes_ViewKind = Attributes_View.append("ViewKind");
  
  public static final QualifiedName Attributes_ViewKind_None = Attributes_View.append("None");
  
  public static final QualifiedName Attributes_ViewKind_Debug = Attributes_View.append("Debug");
  
  public static final QualifiedName Attributes_ViewKind_Expert = Attributes_View.append("Expert");
  
  public static final QualifiedName Attributes_ViewKind_All = Attributes_View.append("All");

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

  public static final QualifiedName Smp_ViewKind = Smp.append("ViewKind");

  public static final QualifiedName Smp_ViewKind_VK_None = Smp_ViewKind.append("VK_None");

  public static final QualifiedName Smp_ViewKind_VK_Debug = Smp_ViewKind.append("VK_Debug");

  public static final QualifiedName Smp_ViewKind_VK_Expert = Smp_ViewKind.append("VK_Expert");

  public static final QualifiedName Smp_ViewKind_VK_All = Smp_ViewKind.append("VK_All");

  public static final QualifiedName Smp_IComponent = Smp.append("IComponent");

  public static final QualifiedName Smp_IModel = Smp.append("IModel");

  public static final QualifiedName Smp_IService = Smp.append("IService");
}
