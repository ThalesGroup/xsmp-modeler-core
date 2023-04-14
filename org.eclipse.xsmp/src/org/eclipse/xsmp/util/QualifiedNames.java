package org.eclipse.xsmp.util;

import org.eclipse.xtext.naming.QualifiedName;

public interface QualifiedNames
{

  QualifiedName _Attributes = QualifiedName.create("Attributes");

  public interface Attributes
  {
    QualifiedName Static = _Attributes.append("Static");

    QualifiedName Const = _Attributes.append("Const");

    QualifiedName Mutable = _Attributes.append("Mutable");

    QualifiedName ByPointer = _Attributes.append("ByPointer");

    QualifiedName ByReference = _Attributes.append("ByReference");

    QualifiedName Abstract = _Attributes.append("Abstract");

    QualifiedName Virtual = _Attributes.append("Virtual");

    QualifiedName Constructor = _Attributes.append("Constructor");

    QualifiedName Forcible = _Attributes.append("Forcible");

    QualifiedName Failure = _Attributes.append("Failure");

    QualifiedName ConstGetter = _Attributes.append("ConstGetter");

    QualifiedName NoConstructor = _Attributes.append("NoConstructor");

    QualifiedName NoDestructor = _Attributes.append("NoDestructor");

    QualifiedName SimpleArray = _Attributes.append("SimpleArray");

    QualifiedName OperatorKind = _Attributes.append("OperatorKind");

    QualifiedName FieldUpdateKind = _Attributes.append("FieldUpdateKind");

    QualifiedName Operator = _Attributes.append("Operator");

    QualifiedName View = _Attributes.append("View");
  }

  QualifiedName _Smp = QualifiedName.create("Smp");

  public interface Smp
  {
    QualifiedName Char8 = _Smp.append("Char8");

    QualifiedName String8 = _Smp.append("String8");

    QualifiedName Float32 = _Smp.append("Float32");

    QualifiedName Float64 = _Smp.append("Float64");

    QualifiedName Int8 = _Smp.append("Int8");

    QualifiedName UInt8 = _Smp.append("UInt8");

    QualifiedName Int16 = _Smp.append("Int16");

    QualifiedName UInt16 = _Smp.append("UInt16");

    QualifiedName Int32 = _Smp.append("Int32");

    QualifiedName UInt32 = _Smp.append("UInt32");

    QualifiedName Int64 = _Smp.append("Int64");

    QualifiedName UInt64 = _Smp.append("UInt64");

    QualifiedName Bool = _Smp.append("Bool");

    QualifiedName DateTime = _Smp.append("DateTime");

    QualifiedName Duration = _Smp.append("Duration");

    QualifiedName PrimitiveTypeKind = _Smp.append("PrimitiveTypeKind");

    QualifiedName EventSourceCollection = _Smp.append("EventSourceCollection");

    QualifiedName EntryPointCollection = _Smp.append("EntryPointCollection");

    QualifiedName FactoryCollection = _Smp.append("FactoryCollection");

    QualifiedName FailureCollection = _Smp.append("FailureCollection");

    QualifiedName FieldCollection = _Smp.append("FieldCollection");

    QualifiedName ComponentCollection = _Smp.append("ComponentCollection");

    QualifiedName OperationCollection = _Smp.append("OperationCollection");

    QualifiedName ParameterCollection = _Smp.append("ParameterCollection");

    QualifiedName PropertyCollection = _Smp.append("PropertyCollection");

    QualifiedName AnySimpleArray = _Smp.append("AnySimpleArray");

    QualifiedName ModelCollection = _Smp.append("ModelCollection");

    QualifiedName ServiceCollection = _Smp.append("ServiceCollection");

    QualifiedName ReferenceCollection = _Smp.append("ReferenceCollection");

    QualifiedName ContainerCollection = _Smp.append("ContainerCollection");

    QualifiedName EventSinkCollection = _Smp.append("EventSinkCollection");

  }
}
