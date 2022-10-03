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
package org.eclipse.xsmp.ui.hover

import org.eclipse.xsmp.services.XsmpcatGrammarAccess
import javax.inject.Inject
import org.eclipse.xtext.Keyword

class XsmpcatKeywordHovers {

	@Inject XsmpcatGrammarAccess ga;

def multiplicity()
{
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
				<p><code><strong><span style="color: #7f0055;">catalogue</span></strong> <em>name</em></code></p>
				<br/>
				A <b>Catalogue</b> is a document that defines types.
				<p>It contains namespaces as a primary ordering mechanism.
				<br />The names of these namespaces need to be unique within the catalogue.</p>
			'''
			case ga.namespaceDeclarationAccess.namespaceKeyword_0: '''
				<p><code><strong><span style="color: #7f0055;">namespace</span></strong> <em>name</em> { } </code></p>
				<br/>
				A <b>Namespace</b> is a primary ordering mechanism. <br />
				<p>A <b>namespace</b> may contain other namespaces (nested namespaces), and does typically contain types.
				<br />In SMDL, namespaces are contained within a <b>Catalogue</b> (either directly, or within another namespace in a catalogue).
				<br />All sub-elements of a namespace (namespaces and types) must have unique names.</p>
			'''
			case ga.structureDeclarationAccess.structKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">struct</span></strong> <em>name</em> { } </code></p>
				<br/>
				A <b>Structure</b> type collects an arbitrary number of <b>Fields</b> representing the state of the structure.
				<p>Within a structure, each field needs to be given a unique name.
				<br />In order to arrive at semantically correct (data) type definitions, a structure type may not be recursive, i.e. a structure may not have a field that is typed by the structure itself.
				<br />A structure can also serve as a namespace to define an arbitrary number of <b>Constants</b>.</p>
			'''
			case ga.classDeclarationAccess.classKeyword_1: '''
				<p><code>[<strong><span style="color: #7f0055;">abstract</span></strong>] <strong><span style="color: #7f0055;">class</span></strong> <em>name</em> [<strong><span style="color: #7f0055;">extends</span></strong> <em>base</em>] { } </code></p>
				<br/>
				<p>The <b>Class</b> metaclass is derived from <b>Structure</b>. 
				<br />A <b>class</b> may be abstract (attribute <b>Abstract</b>), and it may <b>extend</b> from a single base class (implementation inheritance), which is represented by the Base link.
				<br />As the Class metaclass is derived from Structure it can contain constants and fields. 
				<br />Further, it can have arbitrary numbers of properties (Property elements), operations (<b>Operation</b> elements), and associations (<b>Association</b> elements).</p>
			'''
			case ga.exceptionDeclarationAccess.exceptionKeyword_1: '''
				<p><code>[<strong><span style="color: #7f0055;">abstract</span></strong>] <strong><span style="color: #7f0055;">exception</span></strong> <em>name</em> [<strong><span style="color: #7f0055;">extends</span></strong> <em>base</em>] { } </code></p>
				<br/>
				<p>An <b>Exception</b> represents a non-recoverable error that can occur when calling into an <b>Operation</b> or <b>Property</b> getter/setter (within an <b>Operation</b> this is represented by the <b>RaisedException</b> links and within a <b>Property</b> this is represented by the <b>GetRaises</b> and <b>SetRaises</b> links, respectively).
				<br />An Exception can contain constants and fields (from <b>Structure</b>) as well as operations, properties and associations (from <b>Class</b>). The fields represent the state variables of the exception which carry additional information when the exception is raised.
				<br />Furthermore, an <b>Exception</b> may be <b>Abstract</b> (from <b>Class</b>), and it may inherit from a single base exception (implementation inheritance), which is represented by the Base link (from <b>Class</b>).</p>
			'''
			case ga.interfaceDeclarationAccess.interfaceKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">interface</span></strong> <em>name</em> [<strong><span style="color: #7f0055;">extends</span></strong> <em>interface1, ..., interfaceN</em>] { } </code></p>
				<br/>
				<p>An <b>Interface</b> is a reference type that serves as a contract in a loosely coupled architecture. It has the ability to contain constants, properties and operations (from ReferenceType). 
				<br />An <b>Interface</b> may inherit from other interfaces (interface inheritance), which is represented via the Base links. 
				  <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
				    <xhtml:i>
				      <xhtml:b> Remark </xhtml:b>
				    </xhtml:i>
				     : It is strongly recommended to only use value types, references and other interfaces in the properties and operations of an interface (i.e. not to use models). Otherwise, a dependency between a model implementing the interface, and other models referenced by this interface is introduced, which is against the idea of interface-based or component-based design. 
				  </xhtml:p></p>
			'''
			case ga.modelDeclarationAccess.modelKeyword_1: '''
				<p><code>[<strong><span style="color: #7f0055;">abstract</span></strong>] <strong><span style="color: #7f0055;">model</span></strong> <em>name</em> [<strong><span style="color: #7f0055;">extends</span></strong> <em>base</em>] [<strong><span style="color: #7f0055;">implements</span></strong> <em>interface1, ..., interfaceN</em>] { } </code></p>
				<br/>
				<p>The <b>Model</b> metaclass is a component and hence inherits all component mechanisms.
				<br />These mechanisms allow using various different modelling approaches.
				<br />For a class-based design, a <b>Model</b> may provide a collection of <b>Field</b> elements to define its internal state. 
				<br />For scheduling and global events, a <b>Model</b> may provide a collection of <b>EntryPoint</b> elements that can be registered with the <b>Scheduler</b> or <b>EventManager</b> services of a Simulation Environment.
				<br />For an interface-based design, a <b>Model</b> may provide (i.e. implement) an arbitrary number of interfaces, which is represented via the <b>Interface</b> links.
				<br />For a component-based design, a <b>Model</b> may provide <b>Container</b> elements to contain other models (composition), and <b>Reference</b> elements to reference other components (aggregation).
				<br />These components can either be models or services.
				<br />For an event-based design, a <b>Model</b> may support inter-model events via the <b>EventSink</b> and <b>EventSource</b> elements.
				<br />For a dataflow-based design, the fields of a <b>Model</b> can be tagged as Input or Output fields.
				<br />In addition, a <b>Model</b> may have <b>Association</b> elements to express associations to other models or fields of other models.</p>
			'''
			case ga.serviceDeclarationAccess.serviceKeyword_1: '''
				<p><code>[<strong><span style="color: #7f0055;">abstract</span></strong>] <strong><span style="color: #7f0055;">service</span></strong> <em>name</em> [<strong><span style="color: #7f0055;">extends</span></strong> <em>base</em>] [<strong><span style="color: #7f0055;">implements</span></strong> <em>interface1, ..., interfaceN</em>] { } </code></p>
				<br/>
				<p>The Service metaclass is a component and hence inherits all component mechanisms. 
				<br />A Service can reference one or more interfaces via the <b>Interface</b> links (inherited from Component), where at least one of them must be derived from Smp::IService, which qualifies it as a service interface.</p>
			'''
			// Array
			case ga.arrayDeclarationAccess.arrayKeyword_1_0_0_0,
			case ga.arrayDeclarationAccess.usingKeyword_1_0_0_1: '''
				<p><code><strong><span style="color: #7f0055;">array</span></strong> <em>name</em> = <em>type</em> <strong>[</strong><em>integerExpression</em><strong>]</strong></code></p>
				<br/>
				<p>An <b>Array</b> type defines a fixed-size array of identically typed elements, where ItemType defines the type of the array items, and Size defines the number of array items.
				<br />Multi-dimensional arrays are defined when ItemType is an <b>Array</b> type as well.
				<br />Dynamic arrays are not supported by SMDL, as they are not supported by some potential target platforms, and introduce various difficulties in memory management. 
				  <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
				    <xhtml:b>
				      <xhtml:i> Remarks </xhtml:i>
				    </xhtml:b>
				     : Nevertheless, specific mechanisms are available to allow dynamic collections of components, either for containment (composition) or references (aggregation). 
				  </xhtml:p></p>
			'''
			// ValueReference
			case ga.valueReferenceDeclarationAccess.usingKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">using</span></strong> <em>name</em> = <em>type</em> *</code></p>
				<br/>
				<p>A <b>ValueReference</b> is a type that references a specific value type. It is the "missing link" between value types and reference types.</p>
			'''
			case ga.integerDeclarationAccess.integerKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">integer</span></strong> <em>name</em> [<strong><span style="color: #7f0055;">extends</span></strong> <em>(Int8|Int16|Int32|Int64|UInt8|UInt16|UInt32|UInt64)</em>] [<strong><span style="color: #7f0055;">in</span></strong> <em>(*|integralExpression) ... (*|integralExpression)</em>]</code></p>
				<br/>
				<p>An <b>Integer</b> type represents integer values with a given range of valid values (via the Minimum and Maximum attributes). 
				<br />The Unit element can hold a physical unit that can be used by applications to ensure physical unit integrity across models.
				<br />Optionally, the <b>PrimitiveType</b> used to encode the integer value may be specified (one of <b>Int8</b>, <b>Int16</b>, <b>Int32</b>, <b>Int64</b>, <b>UIn8</b>, <b>UInt16</b>, <b>UInt32</b>, <b>UInt64</b>, where the default is <b>Int32</b>).</p>
			'''
			case ga.floatDeclarationAccess.floatKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">float</span></strong> <em>name</em> [<strong><span style="color: #7f0055;">extends</span></strong> <em>(Float32|Float64)</em>] [<strong><span style="color: #7f0055;">in</span></strong> <em>(*|decimalExpression) ... (*|decimalExpression)</em>]</code></p>
				<br/>
				<p>A <b>Float</b> type represents floating-point values with a given range of valid values (via the Minimum and Maximum attributes). 
				<br />The MinInclusive and MaxInclusive attributes determine whether the boundaries are included in the range or not. 
				<br />The Unit element can hold a physical unit that can be used by applications to ensure physical unit integrity across models.
				<br />Optionally, the <b>PrimitiveType</b> used to encode the floating-point value may be specified (one of <b>Float32</b> or <b>Float64</b>, where the default is <b>Float64</b>).</p>
			'''
			case ga.eventDeclarationAccess.eventKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">event</span></strong> <em>name</em> [<strong><span style="color: #7f0055;">extends</span></strong> <em>simpleType</em>]</code></p>
				<br/>
				<p>An <b>Event</b> Type is used to specify the type of an event. 
				<br />This can be used not only to give a meaningful name to an event type, but also to link it to an existing simple type (via the EventArgs attribute) that is passed as an argument with every invocation of the event.</p>
			'''
			case ga.stringDeclarationAccess.stringKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">string</span></strong> <em>name</em> <strong>[</strong><em>integerExpression</em><strong>]</strong></code></p>
				<br/>
				<p>A <b>String</b> type represents fixed Length string values base on <b>Char8</b>.
				<br />The <b>String</b> language element defines an <b>Array</b> of <b>Char8</b> values, but allows a more natural handling of it, e.g. by storing a string value as one string, not as an array of individual characters.
				<br />As with arrays, SMDL does not allow defining variable-sized strings, as these have the same problems as dynamic arrays (e.g. their size is not know up-front, and their use requires memory allocation).</p>
			'''
			case ga.primitiveDeclarationAccess.primitiveKeyword_1: '''
				A number of pre-defined types are needed in order to bootstrap the type system. 
				<br/>
				<p>These pre-defined value types are represented by instances of the metaclass <b>PrimitiveType</b>.
				<br />This mechanism is only used in order to bootstrap the type system and may not be used to define new types for modelling. 
				<br />This is an important restriction, as all values of primitive types may be held in a <b>SimpleValue</b>. 
				<br />The metaclasses derived from <b>SimpleValue</b>, however, are pre-defined and cannot be extended.</p>
			'''
			case ga.nativeDeclarationAccess.nativeKeyword_1: '''
				A <b>Native</b> Type specifies a type with any number of platform mappings. It is used to anchor existing or user-defined types into different target platforms. 
				<p>This mechanism is used within the specification to define the SMDL primitive types with respect to the Metamodel, but it can also be used to define native types within an arbitrary SMDL catalogue for use by models.
				<br />In the latter case, native types are typically used to bind a model to some external library or existing Application Programming <b>Interface</b> (API).</p>
			'''
			case ga.attributeTypeDeclarationAccess.attributeKeyword_1: '''
				An <b>Attribute</b> Type defines a new type available for adding attributes to elements. 
				<p><br />The AllowMultiple attribute specifies if a corresponding Attribute may be attached more than once to a language element, while the Usage element defines to which language elements attributes of this type can be attached. 
				<br />An attribute type always references a value type, and specifies a Default value.</p>
			'''
			case ga.enumerationDeclarationAccess.enumKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">enum</span></strong> <em>name</em> { }</code></p>
				<br/>
				An <b>Enumeration</b> type represents one of a number of pre-defined enumeration literals. 
				<p>The Enumeration language element can be used to create user-defined enumeration types. 
				<br />An enumeration must always contain at least one EnumerationLiteral, each having a name and an integer Value attached to it.
				<br />All enumeration literals of an enumeration type must have unique names and values, respectively.</p>
			'''
			case ga.fieldDeclarationAccess.fieldKeyword_1: '''
				<p><code>[<strong><span style="color: #7f0055;">input</span></strong>] [<strong><span style="color: #7f0055;">output</span></strong>] [<strong><span style="color: #7f0055;">transient</span></strong>] <strong><span style="color: #7f0055;">field</span></strong> <em>type name</em> [ = defaultValueExpression]</code></p>
				<br/>
				A <b>Field</b> is a feature that is typed by any value type but String8, and that may have a Default value.
				<p>The transient attribute defines how the field is published to the simulation environment. 
				<br />Only non transient fields are stored using external persistence. 
				<br />The visibility to the user within the simulation environment can be controlled via the standard SMP attribute "View". 
				<br />By default, a field is not transient and the View attribute defaults to "None" when not applied.
				<br />The Input and Output attributes define whether the field value is an input for internal calculations (i.e. needed in order to perform these calculations), or an output of internal calculations (i.e. modified when performing these calculations). 
				<br />These flags default to false, but can be changed from their default value to support dataflow-based design.</p>
			'''
			
			case ga.constantDeclarationAccess.constantKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">constant</span></strong> <em>type name</em>  = valueExpression</code></p>
				<br/>
				A <b>Constant</b> is a feature that is typed by a simple type and that must have a Value.
			'''
			case ga.associationDeclarationAccess.associationKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">association</span></strong> <em>type name</em></code></p>
				<br/>
				An <b>Association</b> is a feature that is typed by a language type (Type link). An association always expresses a reference to an instance of the referenced language type. 
				<p><br />This reference is either another model (if the Type link refers to a <b>Model</b> or <b>Interface</b>), or it is a field contained in another model (if the Type link refers to a ValueType).</p>
			'''
			case ga.propertyDeclarationAccess.propertyKeyword_1: '''
				<p><code>[(<strong><span style="color: #7f0055;">readOnly</span></strong>|<strong><span style="color: #7f0055;">writeOnly</span></strong>|<strong><span style="color: #7f0055;">readWrite</span></strong>)]<strong><span style="color: #7f0055;">property</span></strong> <em>type name</em> [<strong><span style="color: #7f0055;">get throws</span></strong> exception1, ..., exceptionN] [<strong><span style="color: #7f0055;">set throws</span></strong> exception1, ..., exceptionN] [ -> <em>attachedField</em>]</code></p>
				<br/>
				A <b>Property</b> has a similar syntax as a Field: It is a feature that references a language type. 
				<p>However, the semantics is different in that a property does not represent a state and that it can be assigned an Access attribute to specify how the property can be accessed (either readWrite, readOnly, or writeOnly, see <b>AccessKind</b>).
				<br />Furthermore, a property can be assigned a <b>Category</b> attribute to help grouping the properties within its owning type, and a property may specify an arbitrary number of exceptions that it can raise in its getter (<b>GetRaises</b>) and/or setter (<b>SetRaises</b>).
				  <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
				    <xhtml:b>
				      <xhtml:i> Remark </xhtml:i>
				    </xhtml:b>
				     : The category can be used in applications as ordering or filtering criterion, for example in a property grid. The term "property" used here closely corresponds in its semantics to the same term in the Java Beans specification and in the Microsoft .NET framework. 
				     That is, a property formally represents a "getter" or a "setter" operation or both which allow accessing state or configuration information (or derived information thereof) in a controlled way and which can also be exposed via interfaces (in contrast to fields). 
				  </xhtml:p></p>
			'''
			case ga.containerDeclarationAccess.containerKeyword_0: '''
				<p><code><strong><span style="color: #7f0055;">container</span></strong> <em>type multiplicity? name</em> [ = defaultComponent]</code></p>
				«multiplicity()»
				<br/>
				A <b>Container</b> defines the rules of composition (containment of children) for a Component.
				<p>The type of components that can be contained is specified via the Type link.
				<br />The Lower and Upper attributes specify the multiplicity, i.e. the number of possibly stored components. 
				<br />Therein the upper bound may be unlimited, which is represented by Upper=-1. 
				<br />Furthermore, a container may specify a default implementation of the container type via the DefaultComponentl link.
				  <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
				    <xhtml:b>
				      <xhtml:i> Remark </xhtml:i>
				    </xhtml:b>
				     : SMDL support tools may use this during instantiation (i.e. during model integration) to select an initial implementation for newly created contained components. 
				  </xhtml:p></p>
			'''
			case ga.referenceDeclarationAccess.referenceKeyword_0: '''
				<p><code><strong><span style="color: #7f0055;">reference</span></strong> <em>interface multiplicity? name</em></code></p>
				«multiplicity()»
				<br/>
				A <b>Reference</b> defines the rules of aggregation (links to components) for a <b>Component</b>.
				<p>The type of components (models or services) that can be referenced is specified by the <b>Interface</b> link. 
				<br />Thereby, a service reference is characterized by an interface that is derived from <code>Smp::IService</code>.
				<br />The Lower and Upper attributes specify the multiplicity, i.e. the number of possibly held references to components implementing this interface. 
				<br />Therein the upper bound may be unlimited, which is represented by Upper=-1.</p>
			'''
			case ga.entryPointDeclarationAccess.entrypointKeyword_0: '''
				<p><code><strong><span style="color: #7f0055;">entrypoint</span></strong> <em>name</em></code></p>
				<br/>
				An <b>EntryPoint</b> is a named element of a <b>Component</b> (<b>Model</b> or <b>Service</b>).
				<p>It corresponds to a void operation taking no parameters that can be called from an external client (e.g. the Scheduler or Event Manager services).
				<br />An Entry Point can reference both Input fields (which must have their Input attribute set to true) and Output fields (which must have their Output attribute set to true). 
				<br />These links can be used to ensure that all component output fields are updated before the entry point is called, or that all input fields can be used after the entry point has been called.</p>
			'''
			case ga.eventSinkDeclarationAccess.eventsinkKeyword_0: '''
				<p><code><strong><span style="color: #7f0055;">eventsink</span></strong> <em>type name</em></code></p>
				<br/>
				An <b>EventSink</b> is used to specify that a <b>Component</b> can receive a specific event using a given name. An <b>EventSink</b> can be connected to any number of <b>EventSource</b> instances.
			'''
			case ga.eventSourceDeclarationAccess.eventsourceKeyword_0: '''
				<p><code><strong><span style="color: #7f0055;">eventsource</span></strong> <em>type name</em></code></p>
				<br/>
				An <b>EventSource</b> is used to specify that a <b>Component</b> publishes a specific event under a given name.
				<p>The Multicast attribute can be used to specify whether any number of sinks can connect to the source (the default), or only a single sink can connect (<b>Multicast=false</b>).
				  <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
				    <xhtml:b>
				      <xhtml:i> Remark </xhtml:i>
				    </xhtml:b>
				     : A tool for model integration can use the <b>Multicast</b> attribute to configure the user interface accordingly to ease the specification of event links. 
				  </xhtml:p></p>
			'''
			case ga.operationDeclarationAccess.defKeyword_1: '''
				<p><code><strong><span style="color: #7f0055;">def</span></strong> (<em>returnType</em>|<strong><span style="color: #7f0055;">void</span></strong>) <em>name</em> <strong>(</strong><em>p1, ..., pN</em><strong>)</strong> [<strong><span style="color: #7f0055;">throws</span></strong> exception1, ..., exceptionN]</code></p>
				<p><code>p*: [(<strong><span style="color: #7f0055;">in</span></strong>|<strong><span style="color: #7f0055;">out</span></strong>|<strong><span style="color: #7f0055;">inout</span></strong>)] <em>type name</em> [ = defaultValueExpression]</code></p>
				<br/>
				An <b>Operation</b> may have an arbitrary number of parameters, where at most one of the parameters may be of <b>Direction = ParameterDirectionKind.return</b>. 
				<p>If such a parameter is absent, the operation is a void function (procedure) without return value.
				<br />An <b>RaisedException</b> may specify an arbitrary number of exceptions that it can raise (<b>RaisedException</b>).</p>
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
