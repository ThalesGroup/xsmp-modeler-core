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
package org.eclipse.xsmp.ide.hover

import org.eclipse.xsmp.services.XsmpcatGrammarAccess
import javax.inject.Inject
import org.eclipse.xtext.Keyword

class XsmpcatKeywordHovers {

	@Inject XsmpcatGrammarAccess ga;

	def multiplicity() {
		'''
			multiplicity:
			 - empty : 1 element
			 - ? : 0 or 1 element
			 - [] | [*] : 0 to infinitite
			 - [ expr ] : expr elements
			 - [ expr ... * ] : expr to infinitite
			 - [ expr1 ... expr2 ] : expr1 to expr2
		'''
	}

	def hoverText(Keyword k) {
		val result = switch (k) {
			case ga.catalogueAccess.catalogueKeyword_1: '''
				`catalogue <name>`
				
				A **Catalogue** is a document that defines types.
				
				It contains namespaces as a primary ordering mechanism.
				The names of these namespaces need to be unique within the catalogue.
			'''
			case ga.namespaceMemberAccess.namespaceKeyword_3_0_1,
			case ga.namespaceAccess.namespaceKeyword_4: '''
				`namespace <name> { }`
				
				A **Namespace** is a primary ordering mechanism.
				
				A **namespace** may contain other namespaces (nested namespaces), and does typically contain types.
				In SMDL, namespaces are contained within a **Catalogue** (either directly, or within another namespace in a catalogue).
				All sub-elements of a namespace (namespaces and types) must have unique names.
			'''
			case ga.namespaceMemberAccess.structKeyword_3_1_2: '''
				`struct <name> { }`
				
				A **Structure** type collects an arbitrary number of **Fields** representing the state of the structure.
				
				Within a structure, each field needs to be given a unique name.
				In order to arrive at semantically correct (data) type definitions, a structure type may not be recursive, i.e. a structure may not have a field that is typed by the structure itself.
				A structure can also serve as a namespace to define an arbitrary number of **Constants**.
			'''
			case ga.namespaceMemberAccess.classKeyword_3_2_2: '''
				`[abstract] class <name> [extends <base>] { }`
				
				The **Class** metaclass is derived from **Structure**.
				
				A **class** may be abstract (attribute **Abstract**), and it may **extend** from a single base class (implementation inheritance), which is represented by the Base link.
				As the Class metaclass is derived from Structure it can contain constants and fields.
				Further, it can have arbitrary numbers of properties (Property elements), operations (**Operation** elements), and associations (**Association** elements).
			'''
			case ga.namespaceMemberAccess.exceptionKeyword_3_3_2: '''
				`[abstract] exception <name> [extends <base>] { }`
				
				An **Exception** represents a non-recoverable error that can occur when calling into an **Operation** or **Property** getter/setter (within an **Operation** this is represented by the **RaisedException** links and within a **Property** this is represented by the **GetRaises** and **SetRaises** links, respectively).
				
				An Exception can contain constants and fields (from **Structure**) as well as operations, properties and associations (from **Class**). The fields represent the state variables of the exception which carry additional information when the exception is raised.
				Furthermore, an **Exception** may be **Abstract** (from **Class**), and it may inherit from a single base exception (implementation inheritance), which is represented by the Base link (from **Class**).
			'''
			case ga.namespaceMemberAccess.interfaceKeyword_3_4_2: '''
				`interface <name> [extends <interface1>, ..., <interfaceN>] { }`
				
				An **Interface** is a reference type that serves as a contract in a loosely coupled architecture. It has the ability to contain constants, properties and operations (from ReferenceType).
				
				An **Interface** may inherit from other interfaces (interface inheritance), which is represented via the Base links.
				
				**Remark**: It is strongly recommended to only use value types, references and other interfaces in the properties and operations of an interface (i.e. not to use models). Otherwise, a dependency between a model implementing the interface, and other models referenced by this interface is introduced, which is against the idea of interface-based or component-based design.
			'''
			case ga.namespaceMemberAccess.modelKeyword_3_5_2: '''
				`[abstract] model <name> [extends <base>] [implements <interface1>, ..., <interfaceN>] { }`
				
				The **Model** metaclass is a component and hence inherits all component mechanisms.
				
				These mechanisms allow using various different modelling approaches.
				
				For a class-based design, a **Model** may provide a collection of **Field** elements to define its internal state.
				For scheduling and global events, a **Model** may provide a collection of **EntryPoint** elements that can be registered with the **Scheduler** or **EventManager** services of a Simulation Environment.
				For an interface-based design, a **Model** may provide (i.e. implement) an arbitrary number of interfaces, which is represented via the **Interface** links.
				For a component-based design, a **Model** may provide **Container** elements to contain other models (composition), and **Reference** elements to reference other components (aggregation).
				These components can either be models or services.
				For an event-based design, a **Model** may support inter-model events via the **EventSink** and **EventSource** elements.
				For a dataflow-based design, the fields of a **Model** can be tagged as Input or Output fields.
				In addition, a **Model** may have **Association** elements to express associations to other models or fields of other models.
			'''
			case ga.namespaceMemberAccess.serviceKeyword_3_6_2: '''
				`[abstract] service <name> [extends <base>] [implements <interface1>, ..., <interfaceN>] { }`
				
				The Service metaclass is a component and hence inherits all component mechanisms.
				A Service can reference one or more interfaces via the **Interface** links (inherited from Component), where at least one of them must be derived from Smp::IService, which qualifies it as a service interface.
			'''
			// Array
			case ga.namespaceMemberAccess.arrayKeyword_3_7_2_0_0,
			case ga.namespaceMemberAccess.usingKeyword_3_7_2_0_1: '''
				`array <name> = <type> [<integerExpression>]`
				
				An **Array** type defines a fixed-size array of identically typed elements, where ItemType defines the type of the array items, and Size defines the number of array items.
				
				Multi-dimensional arrays are defined when ItemType is an **Array** type as well.
				Dynamic arrays are not supported by SMDL, as they are not supported by some potential target platforms, and introduce various difficulties in memory management.
				
				**Remarks**: Nevertheless, specific mechanisms are available to allow dynamic collections of components, either for containment (composition) or references (aggregation).
			'''
			// ValueReference
			case ga.namespaceMemberAccess.usingKeyword_3_8_2: '''
				`using <name> = <type>`
				
				A **ValueReference** is a type that references a specific value type. It is the "missing link" between value types and reference types.
			'''
			case ga.namespaceMemberAccess.integerKeyword_3_9_2: '''
				`integer <name> [extends <(Int8|Int16|Int32|Int64|UInt8|UInt16|UInt32|UInt64)>] [in <(*|integralExpression) ... (*|integralExpression)>]`
				
				An **Integer** type represents integer values with a given range of valid values (via the Minimum and Maximum attributes).
				
				The Unit element can hold a physical unit that can be used by applications to ensure physical unit integrity across models.
				Optionally, the **PrimitiveType** used to encode the integer value may be specified (one of **Int8**, **Int16**, **Int32**, **Int64**, **UIn8**, **UInt16**, **UInt32**, **UInt64**, where the default is **Int32**).
			'''
			case ga.namespaceMemberAccess.floatKeyword_3_10_2: '''
				`float <name> [extends <(Float32|Float64)>] [in <(*|decimalExpression) ... (*|decimalExpression)>]`
				
				A **Float** type represents floating-point values with a given range of valid values (via the Minimum and Maximum attributes).
				
				The MinInclusive and MaxInclusive attributes determine whether the boundaries are included in the range or not.
				The Unit element can hold a physical unit that can be used by applications to ensure physical unit integrity across models.
				
				Optionally, the **PrimitiveType** used to encode the floating-point value may be specified (one of **Float32** or **Float64**, where the default is **Float64**).
			'''
			case ga.namespaceMemberAccess.eventKeyword_3_11_2: '''
				`event <name> [extends <simpleType>]`
				
				An **Event** Type is used to specify the type of an event.
				This can be used not only to give a meaningful name to an event type, but also to link it to an existing simple type (via the EventArgs attribute) that is passed as an argument with every invocation of the event.
			'''
			case ga.namespaceMemberAccess.stringKeyword_3_12_2: '''
				`string <name> [<integerExpression>]`
				
				A **String** type represents fixed Length string values base on **Char8**.
				
				The **String** language element defines an **Array** of **Char8** values, but allows a more natural handling of it, e.g. by storing a string value as one string, not as an array of individual characters.
				As with arrays, SMDL does not allow defining variable-sized strings, as these have the same problems as dynamic arrays (e.g. their size is not know up-front, and their use requires memory allocation).
			'''
			case ga.namespaceMemberAccess.primitiveKeyword_3_13_2: '''
				A number of pre-defined types are needed in order to bootstrap the type system.
				
				These pre-defined value types are represented by instances of the metaclass **PrimitiveType**.
				This mechanism is only used in order to bootstrap the type system and may not be used to define new types for modelling.
				This is an important restriction, as all values of primitive types may be held in a **SimpleValue**.
				The metaclasses derived from **SimpleValue**, however, are pre-defined and cannot be extended.
			'''
			case ga.namespaceMemberAccess.nativeKeyword_3_14_2: '''
				A **Native** Type specifies a type with any number of platform mappings. It is used to anchor existing or user-defined types into different target platforms.
				
				This mechanism is used within the specification to define the SMDL primitive types with respect to the Metamodel, but it can also be used to define native types within an arbitrary SMDL catalogue for use by models.
				In the latter case, native types are typically used to bind a model to some external library or existing Application Programming **Interface** (API).
			'''
			case ga.namespaceMemberAccess.attributeKeyword_3_15_2,
			case ga.namespaceMemberAccess.attributeKeyword_3_17_2: '''
				An **Attribute** Type defines a new type available for adding attributes to elements.
				
				The AllowMultiple attribute specifies if a corresponding Attribute may be attached more than once to a language element, while the Usage element defines to which language elements attributes of this type can be attached.
				An attribute type always references a value type, and specifies a Default value.
			'''
			case ga.namespaceMemberAccess.enumKeyword_3_16_2: '''
				`enum <name> { }`
				
				An **Enumeration** type represents one of a number of pre-defined enumeration literals.
				
				The Enumeration language element can be used to create user-defined enumeration types.
				An enumeration must always contain at least one EnumerationLiteral, each having a name and an integer Value attached to it.
				All enumeration literals of an enumeration type must have unique names and values, respectively.
			'''
			case ga.fieldDeclarationAccess.fieldKeyword_1: '''
				`[input] [output] [transient] field <type> <name> [ = defaultValueExpression]`
				
				A **Field** is a feature that is typed by any value type but String8, and that may have a Default value.
				
				The transient attribute defines how the field is published to the simulation environment.
				Only non transient fields are stored using external persistence.
				The visibility to the user within the simulation environment can be controlled via the standard SMP attribute "View".
				By default, a field is not transient and the View attribute defaults to "None" when not applied.
				The Input and Output attributes define whether the field value is an input for internal calculations (i.e. needed in order to perform these calculations), or an output of internal calculations (i.e. modified when performing these calculations).
				These flags default to false, but can be changed from their default value to support dataflow-based design.
			'''
			case ga.constantDeclarationAccess.constantKeyword_1: '''
				`constant <type> <name> = valueExpression`
				
				A **Constant** is a feature that is typed by a simple type and that must have a Value.
			'''
			case ga.associationDeclarationAccess.associationKeyword_1: '''
				`association <type> <name>`
				
				An **Association** is a feature that is typed by a language type (Type link). An association always expresses a reference to an instance of the referenced language type.
				
				This reference is either another model (if the Type link refers to a **Model** or **Interface**), or it is a field contained in another model (if the Type link refers to a ValueType).
			'''
			case ga.propertyDeclarationAccess.propertyKeyword_1: '''
				`[(readOnly|writeOnly|readWrite)] property <type> <name> [get throws <exception1, ..., exceptionN>] [set throws <exception1, ..., exceptionN>] [ -> <attachedField>]`
				
				A **Property** has a similar syntax as a Field: It is a feature that references a language type.
				
				However, the semantics is different in that a property does not represent a state and that it can be assigned an Access attribute to specify how the property can be accessed (either readWrite, readOnly, or writeOnly, see **AccessKind**).
				Furthermore, a property can be assigned a **Category** attribute to help grouping the properties within its owning type, and a property may specify an arbitrary number of exceptions that it can raise in its getter (**GetRaises**) and/or setter (**SetRaises**).
				
				**Remark**: The category can be used in applications as ordering or filtering criterion, for example in a property grid. The term "property" used here closely corresponds in its semantics to the same term in the Java Beans specification and in the Microsoft .NET framework. That is, a property formally represents a "getter" or a "setter" operation or both which allow accessing state or configuration information (or derived information thereof) in a controlled way and which can also be exposed via interfaces (in contrast to fields).
			'''
			case ga.containerDeclarationAccess.containerKeyword_0: '''
				`container <type> <multiplicity?> <name> [ = defaultComponent]`
				«multiplicity()»
				
				A **Container** defines the rules of composition (containment of children) for a Component.
				
				The type of components that can be contained is specified via the Type link.
				The Lower and Upper attributes specify the multiplicity, i.e. the number of possibly stored components.
				Therein the upper bound may be unlimited, which is represented by Upper=-1.
				Furthermore, a container may specify a default implementation of the container type via the DefaultComponentl link. Remark : SMDL support tools may use this during instantiation (i.e. during model integration) to select an initial implementation for newly created contained components.
			'''
			case ga.referenceDeclarationAccess.referenceKeyword_0: '''
				`reference <interface> <multiplicity?> <name>`
				«multiplicity()»
				
				A **Reference** defines the rules of aggregation (links to components) for a **Component**.
				
				The type of components (models or services) that can be referenced is specified by the **Interface** link.
				Thereby, a service reference is characterized by an interface that is derived from `Smp::IService`.
				The Lower and Upper attributes specify the multiplicity, i.e. the number of possibly held references to components implementing this interface.
				Therein the upper bound may be unlimited, which is represented by Upper=-1.
			'''
			case ga.entryPointDeclarationAccess.entrypointKeyword_0: '''
				`entrypoint <name>`
				
				An **EntryPoint** is a named element of a **Component** (**Model** or **Service**).
				
				It corresponds to a void operation taking no parameters that can be called from an external client (e.g. the Scheduler or Event Manager services).
				An Entry Point can reference both Input fields (which must have their Input attribute set to true) and Output fields (which must have their Output attribute set to true).
				These links can be used to ensure that all component output fields are updated before the entry point is called, or that all input fields can be used after the entry point has been called.
			'''
			case ga.eventSinkDeclarationAccess.eventsinkKeyword_0: '''
				`eventsink <type> <name>`
				
				An **EventSink** is used to specify that a **Component** can receive a specific event using a given name. An **EventSink** can be connected to any number of **EventSource** instances.
			'''
			case ga.eventSourceDeclarationAccess.eventsourceKeyword_0: '''
				`eventsource <type> <name>`
				
				An **EventSource** is used to specify that a **Component** publishes a specific event under a given name.
				
				The Multicast attribute can be used to specify whether any number of sinks can connect to the source (the default), or only a single sink can connect (**Multicast=false**).
				
				**Remark**: A tool for model integration can use the **Multicast** attribute to configure the user interface accordingly to ease the specification of event links.
			'''
			case ga.operationDeclarationAccess.defKeyword_1: '''
				`def (<returnType>|void) <name> (<p1>, ..., <pN>) [throws <exception1>, ..., <exceptionN>]`
				
				`p*: [(in|out|inout)] <type> <name> [ = defaultValueExpression]`
				
				An **Operation** may have an arbitrary number of parameters, where at most one of the parameters may be of **Direction = ParameterDirectionKind.return**.
				
				If such a parameter is absent, the operation is a void function (procedure) without return value.
				An **RaisedException** may specify an arbitrary number of exceptions that it can raise (**RaisedException**).
			'''
			case ga.visibilityModifiersAccess.privateKeyword_0: '''
				The element is visible only within its containing classifier.
			'''
			case ga.visibilityModifiersAccess.protectedKeyword_1: '''
				The element is visible within its containing classifier and derived classifiers thereof.
			'''
			case ga.visibilityModifiersAccess.publicKeyword_2: '''
				The element is globally visible.
			'''
			case ga.parameterDirectionKindAccess.inInKeyword_0_0: '''
				The parameter is read-only to the operation, i.e. its value must be specified on call, and cannot be changed inside the operation.
			'''
			case ga.parameterDirectionKindAccess.outOutKeyword_1_0: '''
				The parameter is write-only to the operation, i.e. its value is unspecified on call, and must be set by the operation.
			'''
			case ga.parameterDirectionKindAccess.inoutInoutKeyword_2_0: '''
				The parameter must be specified on call, and may be changed by the operation.
			'''
			default:
				return null
		}
		result.toString;
	}
}
