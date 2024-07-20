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
package org.eclipse.xsmp.ide.tests;

import org.eclipse.xtext.testing.TestCompletionConfiguration;
import org.junit.jupiter.api.Test;

class CompletionTest extends AbstractXsmpcatLanguageServerTest
{
  @Test
  void testCompletion_01()
  {
    testCompletion((TestCompletionConfiguration it) -> {
      it.setModel("""
              catalogue TestCatalogue


              """);
      it.setLine(2);

      final var expected = """
              namespace -> namespace ${1:name}
              {
                  $0
              } // namespace ${1:name}
               [[2, 0] .. [2, 0]]
              namespace -> namespace [[2, 0] .. [2, 0]]
              @ -> @ [[2, 0] .. [2, 0]]
                                            """;

      it.setExpectedCompletionItems(expected);
    });
  }

  @Test
  void testCompletion_02()
  {
    testCompletion((TestCompletionConfiguration it) -> {
      it.setModel("""
              catalogue TestCatalogue

              namespace TestNamespace
              {
                /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                model TestModel
                {

                }
              }
              """);
      it.setLine(7);

      final var expected = """
              association -> association ${1|TestModel,Publication.DuplicateLiteral,Publication.InvalidPrimitiveType,Publication.TypeAlreadyRegistered,Publication.TypeNotRegistered,Publication.ParameterDirectionKind,Publication.ITypeRegistry,Publication.IType,Publication.IEnumerationType,Publication.IArrayType,Publication.IStructureType,Publication.IClassType,Publication.IPublishOperation,Services.ILinkRegistry,Services.EntryPointAlreadySubscribed,Services.EntryPointNotSubscribed,Services.InvalidCycleTime,Services.InvalidEventName,Services.InvalidEventTime,Services.InvalidSimulationTime,Services.ILogger,Services.LogMessageKind,Services.ITimeKeeper,Services.TimeKind,Services.IScheduler,Services.EventId,Services.InvalidEventId,Services.IEventManager,Services.IResolver,Char8,String8,Float32,Float64,Int8,UInt8,Int16,UInt16,Int32,UInt32,Int64,UInt64,Bool,DateTime,Duration,ICollection,IDataflowField,IFailure,IFallibleModel,ILinkingComponent,IOperation,IParameter,IProperty,ISimpleArrayField,IStructureField,CannotDelete,CannotRemove,CannotRestore,CannotStore,DuplicateUuid,EventSinkAlreadySubscribed,EventSinkNotSubscribed,FieldAlreadyConnected,InvalidArrayIndex,InvalidArraySize,InvalidArrayValue,InvalidComponentState,InvalidEventSink,InvalidFieldName,InvalidFieldType,InvalidLibrary,InvalidOperationName,InvalidParameterCount,InvalidParameterIndex,InvalidParameterType,InvalidParameterValue,InvalidReturnValue,InvalidSimulatorState,InvalidTarget,LibraryNotFound,NotContained,NotReferenced,ReferenceFull,Uuid,UuidBytes,VoidOperation,AccessKind,ViewKind,AnySimple,IObject,Exception,IEventSource,IField,EventSourceCollection,IArrayField,IDynamicInvocation,IRequest,ISimpleField,IForcibleField,IPersist,IStorageReader,IStorageWriter,IEntryPoint,SimulatorStateKind,EntryPointCollection,ISimulator,IFactory,FactoryCollection,IPublication,IComponent,InvalidObjectName,PrimitiveTypeKind,ComponentCollection,FailureCollection,FieldCollection,OperationCollection,ParameterCollection,PropertyCollection,ComponentStateKind,IModel,DuplicateName,AnySimpleArray,ModelCollection,IService,InvalidAnyType,ServiceCollection,IAggregate,InvalidObjectType,IEventConsumer,IReference,ContainerFull,ReferenceCollection,IComposite,IEventProvider,InvalidFieldValue,IContainer,ContainerCollection,IEntryPointPublisher,IEventSink,EventSinkCollection,TestNamespace.TestModel,Smp.Publication.DuplicateLiteral,Smp.Publication.InvalidPrimitiveType,Smp.Publication.TypeAlreadyRegistered,Smp.Publication.TypeNotRegistered,Smp.Publication.ParameterDirectionKind,Smp.Publication.ITypeRegistry,Smp.Publication.IType,Smp.Publication.IEnumerationType,Smp.Publication.IArrayType,Smp.Publication.IStructureType,Smp.Publication.IClassType,Smp.Publication.IPublishOperation,Smp.Services.ILinkRegistry,Smp.Services.EntryPointAlreadySubscribed,Smp.Services.EntryPointNotSubscribed,Smp.Services.InvalidCycleTime,Smp.Services.InvalidEventName,Smp.Services.InvalidEventTime,Smp.Services.InvalidSimulationTime,Smp.Services.ILogger,Smp.Services.LogMessageKind,Smp.Services.ITimeKeeper,Smp.Services.TimeKind,Smp.Services.IScheduler,Smp.Services.EventId,Smp.Services.InvalidEventId,Smp.Services.IEventManager,Smp.Services.IResolver,Smp.Char8,Smp.String8,Smp.Float32,Smp.Float64,Smp.Int8,Smp.UInt8,Smp.Int16,Smp.UInt16,Smp.Int32,Smp.UInt32,Smp.Int64,Smp.UInt64,Smp.Bool,Smp.DateTime,Smp.Duration,Smp.ICollection,Smp.IDataflowField,Smp.IFailure,Smp.IFallibleModel,Smp.ILinkingComponent,Smp.IOperation,Smp.IParameter,Smp.IProperty,Smp.ISimpleArrayField,Smp.IStructureField,Smp.CannotDelete,Smp.CannotRemove,Smp.CannotRestore,Smp.CannotStore,Smp.DuplicateUuid,Smp.EventSinkAlreadySubscribed,Smp.EventSinkNotSubscribed,Smp.FieldAlreadyConnected,Smp.InvalidArrayIndex,Smp.InvalidArraySize,Smp.InvalidArrayValue,Smp.InvalidComponentState,Smp.InvalidEventSink,Smp.InvalidFieldName,Smp.InvalidFieldType,Smp.InvalidLibrary,Smp.InvalidOperationName,Smp.InvalidParameterCount,Smp.InvalidParameterIndex,Smp.InvalidParameterType,Smp.InvalidParameterValue,Smp.InvalidReturnValue,Smp.InvalidSimulatorState,Smp.InvalidTarget,Smp.LibraryNotFound,Smp.NotContained,Smp.NotReferenced,Smp.ReferenceFull,Smp.Uuid,Smp.UuidBytes,Smp.VoidOperation,Smp.AccessKind,Smp.ViewKind,Smp.AnySimple,Smp.IObject,Smp.Exception,Smp.IEventSource,Smp.IField,Smp.EventSourceCollection,Smp.IArrayField,Smp.IDynamicInvocation,Smp.IRequest,Smp.ISimpleField,Smp.IForcibleField,Smp.IPersist,Smp.IStorageReader,Smp.IStorageWriter,Smp.IEntryPoint,Smp.SimulatorStateKind,Smp.EntryPointCollection,Smp.ISimulator,Smp.IFactory,Smp.FactoryCollection,Smp.IPublication,Smp.IComponent,Smp.InvalidObjectName,Smp.PrimitiveTypeKind,Smp.ComponentCollection,Smp.FailureCollection,Smp.FieldCollection,Smp.OperationCollection,Smp.ParameterCollection,Smp.PropertyCollection,Smp.ComponentStateKind,Smp.IModel,Smp.DuplicateName,Smp.AnySimpleArray,Smp.ModelCollection,Smp.IService,Smp.InvalidAnyType,Smp.ServiceCollection,Smp.IAggregate,Smp.InvalidObjectType,Smp.IEventConsumer,Smp.IReference,Smp.ContainerFull,Smp.ReferenceCollection,Smp.IComposite,Smp.IEventProvider,Smp.InvalidFieldValue,Smp.IContainer,Smp.ContainerCollection,Smp.IEntryPointPublisher,Smp.IEventSink,Smp.EventSinkCollection|} ${2:name}
               [[7, 0] .. [7, 0]]
              constant -> constant ${1|Publication.ParameterDirectionKind,Services.LogMessageKind,Services.TimeKind,Services.EventId,Char8,String8,Float32,Float64,Int8,UInt8,Int16,UInt16,Int32,UInt32,Int64,UInt64,Bool,DateTime,Duration,AccessKind,ViewKind,SimulatorStateKind,PrimitiveTypeKind,ComponentStateKind,Smp.Publication.ParameterDirectionKind,Smp.Services.LogMessageKind,Smp.Services.TimeKind,Smp.Services.EventId,Smp.Char8,Smp.String8,Smp.Float32,Smp.Float64,Smp.Int8,Smp.UInt8,Smp.Int16,Smp.UInt16,Smp.Int32,Smp.UInt32,Smp.Int64,Smp.UInt64,Smp.Bool,Smp.DateTime,Smp.Duration,Smp.AccessKind,Smp.ViewKind,Smp.SimulatorStateKind,Smp.PrimitiveTypeKind,Smp.ComponentStateKind|} ${2:name} = $0
               [[7, 0] .. [7, 0]]
              container -> container ${1|TestModel,Publication.ITypeRegistry,Publication.IType,Publication.IEnumerationType,Publication.IArrayType,Publication.IStructureType,Publication.IClassType,Publication.IPublishOperation,Services.ILinkRegistry,Services.ILogger,Services.ITimeKeeper,Services.IScheduler,Services.IEventManager,Services.IResolver,ICollection,IDataflowField,IFailure,IFallibleModel,ILinkingComponent,IOperation,IParameter,IProperty,ISimpleArrayField,IStructureField,IObject,IEventSource,IField,IArrayField,IDynamicInvocation,IRequest,ISimpleField,IForcibleField,IPersist,IStorageReader,IStorageWriter,IEntryPoint,ISimulator,IFactory,IPublication,IComponent,IModel,IService,IAggregate,IEventConsumer,IReference,IComposite,IEventProvider,IContainer,IEntryPointPublisher,IEventSink,TestNamespace.TestModel,Smp.Publication.ITypeRegistry,Smp.Publication.IType,Smp.Publication.IEnumerationType,Smp.Publication.IArrayType,Smp.Publication.IStructureType,Smp.Publication.IClassType,Smp.Publication.IPublishOperation,Smp.Services.ILinkRegistry,Smp.Services.ILogger,Smp.Services.ITimeKeeper,Smp.Services.IScheduler,Smp.Services.IEventManager,Smp.Services.IResolver,Smp.ICollection,Smp.IDataflowField,Smp.IFailure,Smp.IFallibleModel,Smp.ILinkingComponent,Smp.IOperation,Smp.IParameter,Smp.IProperty,Smp.ISimpleArrayField,Smp.IStructureField,Smp.IObject,Smp.IEventSource,Smp.IField,Smp.IArrayField,Smp.IDynamicInvocation,Smp.IRequest,Smp.ISimpleField,Smp.IForcibleField,Smp.IPersist,Smp.IStorageReader,Smp.IStorageWriter,Smp.IEntryPoint,Smp.ISimulator,Smp.IFactory,Smp.IPublication,Smp.IComponent,Smp.IModel,Smp.IService,Smp.IAggregate,Smp.IEventConsumer,Smp.IReference,Smp.IComposite,Smp.IEventProvider,Smp.IContainer,Smp.IEntryPointPublisher,Smp.IEventSink|}[*] ${2:name}
               [[7, 0] .. [7, 0]]
              def -> def void ${1:name} ($0)
               [[7, 0] .. [7, 0]]
              entrypoint -> entrypoint ${1:name}
               [[7, 0] .. [7, 0]]
              eventsink -> eventsink ${1|None|} ${2:name}
               [[7, 0] .. [7, 0]]
              eventsource -> eventsource ${1|None|} ${2:name}
               [[7, 0] .. [7, 0]]
              field -> field ${1|Publication.DuplicateLiteral,Publication.InvalidPrimitiveType,Publication.TypeAlreadyRegistered,Publication.TypeNotRegistered,Publication.ParameterDirectionKind,Services.EntryPointAlreadySubscribed,Services.EntryPointNotSubscribed,Services.InvalidCycleTime,Services.InvalidEventName,Services.InvalidEventTime,Services.InvalidSimulationTime,Services.LogMessageKind,Services.TimeKind,Services.EventId,Services.InvalidEventId,Char8,Float32,Float64,Int8,UInt8,Int16,UInt16,Int32,UInt32,Int64,UInt64,Bool,DateTime,Duration,CannotDelete,CannotRemove,CannotRestore,CannotStore,DuplicateUuid,EventSinkAlreadySubscribed,EventSinkNotSubscribed,FieldAlreadyConnected,InvalidArrayIndex,InvalidArraySize,InvalidArrayValue,InvalidComponentState,InvalidEventSink,InvalidFieldName,InvalidFieldType,InvalidLibrary,InvalidOperationName,InvalidParameterCount,InvalidParameterIndex,InvalidParameterType,InvalidParameterValue,InvalidReturnValue,InvalidSimulatorState,InvalidTarget,LibraryNotFound,NotContained,NotReferenced,ReferenceFull,Uuid,UuidBytes,VoidOperation,AccessKind,ViewKind,Exception,SimulatorStateKind,InvalidObjectName,PrimitiveTypeKind,ComponentStateKind,DuplicateName,InvalidAnyType,InvalidObjectType,ContainerFull,InvalidFieldValue,Smp.Publication.DuplicateLiteral,Smp.Publication.InvalidPrimitiveType,Smp.Publication.TypeAlreadyRegistered,Smp.Publication.TypeNotRegistered,Smp.Publication.ParameterDirectionKind,Smp.Services.EntryPointAlreadySubscribed,Smp.Services.EntryPointNotSubscribed,Smp.Services.InvalidCycleTime,Smp.Services.InvalidEventName,Smp.Services.InvalidEventTime,Smp.Services.InvalidSimulationTime,Smp.Services.LogMessageKind,Smp.Services.TimeKind,Smp.Services.EventId,Smp.Services.InvalidEventId,Smp.Char8,Smp.Float32,Smp.Float64,Smp.Int8,Smp.UInt8,Smp.Int16,Smp.UInt16,Smp.Int32,Smp.UInt32,Smp.Int64,Smp.UInt64,Smp.Bool,Smp.DateTime,Smp.Duration,Smp.CannotDelete,Smp.CannotRemove,Smp.CannotRestore,Smp.CannotStore,Smp.DuplicateUuid,Smp.EventSinkAlreadySubscribed,Smp.EventSinkNotSubscribed,Smp.FieldAlreadyConnected,Smp.InvalidArrayIndex,Smp.InvalidArraySize,Smp.InvalidArrayValue,Smp.InvalidComponentState,Smp.InvalidEventSink,Smp.InvalidFieldName,Smp.InvalidFieldType,Smp.InvalidLibrary,Smp.InvalidOperationName,Smp.InvalidParameterCount,Smp.InvalidParameterIndex,Smp.InvalidParameterType,Smp.InvalidParameterValue,Smp.InvalidReturnValue,Smp.InvalidSimulatorState,Smp.InvalidTarget,Smp.LibraryNotFound,Smp.NotContained,Smp.NotReferenced,Smp.ReferenceFull,Smp.Uuid,Smp.UuidBytes,Smp.VoidOperation,Smp.AccessKind,Smp.ViewKind,Smp.Exception,Smp.SimulatorStateKind,Smp.InvalidObjectName,Smp.PrimitiveTypeKind,Smp.ComponentStateKind,Smp.DuplicateName,Smp.InvalidAnyType,Smp.InvalidObjectType,Smp.ContainerFull,Smp.InvalidFieldValue|} ${2:name}
               [[7, 0] .. [7, 0]]
              property -> property ${1|Publication.DuplicateLiteral,Publication.InvalidPrimitiveType,Publication.TypeAlreadyRegistered,Publication.TypeNotRegistered,Publication.ParameterDirectionKind,Services.EntryPointAlreadySubscribed,Services.EntryPointNotSubscribed,Services.InvalidCycleTime,Services.InvalidEventName,Services.InvalidEventTime,Services.InvalidSimulationTime,Services.LogMessageKind,Services.TimeKind,Services.EventId,Services.InvalidEventId,Char8,String8,Float32,Float64,Int8,UInt8,Int16,UInt16,Int32,UInt32,Int64,UInt64,Bool,DateTime,Duration,CannotDelete,CannotRemove,CannotRestore,CannotStore,DuplicateUuid,EventSinkAlreadySubscribed,EventSinkNotSubscribed,FieldAlreadyConnected,InvalidArrayIndex,InvalidArraySize,InvalidArrayValue,InvalidComponentState,InvalidEventSink,InvalidFieldName,InvalidFieldType,InvalidLibrary,InvalidOperationName,InvalidParameterCount,InvalidParameterIndex,InvalidParameterType,InvalidParameterValue,InvalidReturnValue,InvalidSimulatorState,InvalidTarget,LibraryNotFound,NotContained,NotReferenced,ReferenceFull,Uuid,UuidBytes,VoidOperation,AccessKind,ViewKind,Exception,SimulatorStateKind,InvalidObjectName,PrimitiveTypeKind,ComponentStateKind,DuplicateName,InvalidAnyType,InvalidObjectType,ContainerFull,InvalidFieldValue,Smp.Publication.DuplicateLiteral,Smp.Publication.InvalidPrimitiveType,Smp.Publication.TypeAlreadyRegistered,Smp.Publication.TypeNotRegistered,Smp.Publication.ParameterDirectionKind,Smp.Services.EntryPointAlreadySubscribed,Smp.Services.EntryPointNotSubscribed,Smp.Services.InvalidCycleTime,Smp.Services.InvalidEventName,Smp.Services.InvalidEventTime,Smp.Services.InvalidSimulationTime,Smp.Services.LogMessageKind,Smp.Services.TimeKind,Smp.Services.EventId,Smp.Services.InvalidEventId,Smp.Char8,Smp.String8,Smp.Float32,Smp.Float64,Smp.Int8,Smp.UInt8,Smp.Int16,Smp.UInt16,Smp.Int32,Smp.UInt32,Smp.Int64,Smp.UInt64,Smp.Bool,Smp.DateTime,Smp.Duration,Smp.CannotDelete,Smp.CannotRemove,Smp.CannotRestore,Smp.CannotStore,Smp.DuplicateUuid,Smp.EventSinkAlreadySubscribed,Smp.EventSinkNotSubscribed,Smp.FieldAlreadyConnected,Smp.InvalidArrayIndex,Smp.InvalidArraySize,Smp.InvalidArrayValue,Smp.InvalidComponentState,Smp.InvalidEventSink,Smp.InvalidFieldName,Smp.InvalidFieldType,Smp.InvalidLibrary,Smp.InvalidOperationName,Smp.InvalidParameterCount,Smp.InvalidParameterIndex,Smp.InvalidParameterType,Smp.InvalidParameterValue,Smp.InvalidReturnValue,Smp.InvalidSimulatorState,Smp.InvalidTarget,Smp.LibraryNotFound,Smp.NotContained,Smp.NotReferenced,Smp.ReferenceFull,Smp.Uuid,Smp.UuidBytes,Smp.VoidOperation,Smp.AccessKind,Smp.ViewKind,Smp.Exception,Smp.SimulatorStateKind,Smp.InvalidObjectName,Smp.PrimitiveTypeKind,Smp.ComponentStateKind,Smp.DuplicateName,Smp.InvalidAnyType,Smp.InvalidObjectType,Smp.ContainerFull,Smp.InvalidFieldValue|} ${2:name}
               [[7, 0] .. [7, 0]]
              reference -> reference ${1|Publication.ITypeRegistry,Publication.IType,Publication.IEnumerationType,Publication.IArrayType,Publication.IStructureType,Publication.IClassType,Publication.IPublishOperation,Services.ILinkRegistry,Services.ILogger,Services.ITimeKeeper,Services.IScheduler,Services.IEventManager,Services.IResolver,ICollection,IDataflowField,IFailure,IFallibleModel,ILinkingComponent,IOperation,IParameter,IProperty,ISimpleArrayField,IStructureField,IObject,IEventSource,IField,IArrayField,IDynamicInvocation,IRequest,ISimpleField,IForcibleField,IPersist,IStorageReader,IStorageWriter,IEntryPoint,ISimulator,IFactory,IPublication,IComponent,IModel,IService,IAggregate,IEventConsumer,IReference,IComposite,IEventProvider,IContainer,IEntryPointPublisher,IEventSink,Smp.Publication.ITypeRegistry,Smp.Publication.IType,Smp.Publication.IEnumerationType,Smp.Publication.IArrayType,Smp.Publication.IStructureType,Smp.Publication.IClassType,Smp.Publication.IPublishOperation,Smp.Services.ILinkRegistry,Smp.Services.ILogger,Smp.Services.ITimeKeeper,Smp.Services.IScheduler,Smp.Services.IEventManager,Smp.Services.IResolver,Smp.ICollection,Smp.IDataflowField,Smp.IFailure,Smp.IFallibleModel,Smp.ILinkingComponent,Smp.IOperation,Smp.IParameter,Smp.IProperty,Smp.ISimpleArrayField,Smp.IStructureField,Smp.IObject,Smp.IEventSource,Smp.IField,Smp.IArrayField,Smp.IDynamicInvocation,Smp.IRequest,Smp.ISimpleField,Smp.IForcibleField,Smp.IPersist,Smp.IStorageReader,Smp.IStorageWriter,Smp.IEntryPoint,Smp.ISimulator,Smp.IFactory,Smp.IPublication,Smp.IComponent,Smp.IModel,Smp.IService,Smp.IAggregate,Smp.IEventConsumer,Smp.IReference,Smp.IComposite,Smp.IEventProvider,Smp.IContainer,Smp.IEntryPointPublisher,Smp.IEventSink|}[*] ${2:name}
               [[7, 0] .. [7, 0]]
              association -> association [[7, 0] .. [7, 0]]
              constant -> constant [[7, 0] .. [7, 0]]
              container -> container [[7, 0] .. [7, 0]]
              def -> def [[7, 0] .. [7, 0]]
              entrypoint -> entrypoint [[7, 0] .. [7, 0]]
              eventsink -> eventsink [[7, 0] .. [7, 0]]
              eventsource -> eventsource [[7, 0] .. [7, 0]]
              field -> field [[7, 0] .. [7, 0]]
              property -> property [[7, 0] .. [7, 0]]
              reference -> reference [[7, 0] .. [7, 0]]
              @ -> @ [[7, 0] .. [7, 0]]
              } -> } [[7, 0] .. [7, 0]]
                                            """;

      it.setExpectedCompletionItems(expected);
    });
  }

  @Test
  void testCompletion_03()
  {
    testCompletion((TestCompletionConfiguration it) -> {

      it.setModel("""
              catalogue TestCatalogue

              namespace TestNamespace
              {
                /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                model TestModel
                {
                  def B
                }
              }
              """);
      it.setLine(7);
      it.setColumn(9);

      final var expected = """
              Bool (PrimitiveType) -> Bool [[7, 8] .. [7, 9]]
              Smp.Bool (PrimitiveType) -> Smp.Bool [[7, 8] .. [7, 9]]
              """;

      it.setExpectedCompletionItems(expected);
    });
  }

  @Test
  void testCompletion_04()
  {
    testCompletion((TestCompletionConfiguration it) -> {

      it.setModel("""
              catalogue TestCatalogue

              namespace TestNamespace
              {
                /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                model TestModel
                {
                  field B
                }
              }
              """);
      it.setLine(7);
      it.setColumn(11);

      final var expected = """
              Bool (PrimitiveType) -> Bool [[7, 10] .. [7, 11]]
              Smp.Bool (PrimitiveType) -> Smp.Bool [[7, 10] .. [7, 11]]
                            """;

      it.setExpectedCompletionItems(expected);
    });
  }

  @Test
  void testCompletion_05()
  {
    testCompletion((TestCompletionConfiguration it) -> {

      it.setModel("""
              catalogue TestCatalogue

              namespace TestNamespace
              {
                /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                struct TestModel
                {
                  field B
                }
              }
              """);
      it.setLine(7);
      it.setColumn(11);

      final var expected = """
              Bool (PrimitiveType) -> Bool [[7, 10] .. [7, 11]]
              Smp.Bool (PrimitiveType) -> Smp.Bool [[7, 10] .. [7, 11]]
                            """;

      it.setExpectedCompletionItems(expected);
    });
  }
}
