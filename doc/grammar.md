
Files with the file extension `.xsmpcat` are Xsmpcat files and have to comply with the following grammar. An Xsmpcat file must be placed on your project's class path (usually ./smdl)

# The Grammar

This section describes all SMP elements and their grammar.
Each Xsmpcat file must start with a Catalogue declaration.

# Catalogue


A Catalogue is a document that defines types. It contains namespaces as a primary ordering mechanism. The names of these namespaces need to be unique within the catalogue.

```xsmpcat
/**
 * Catalogue description
 * 
 * @creator username
 * @date 2022-02-28T13:31:48Z
 * @title Catalogue Title
 * @version 1.0.0
 */
'catalogue' catalogue_name

 Namespace*
```


# Namespace

A Namespace is a primary ordering mechanism. A namespace may contain other namespaces (nested namespaces), and does typically contain types or constants. In SMDL, namespaces are contained within a Catalogue (either directly, or within another namespace in a catalogue).
All sub-elements of a namespace (namespaces, types and constants) must have unique names.
```xsmpcat
/**
 * Namespace description
 */
 'namespace' namespace_name
 '{'
    (Namespace|Type|Constant)*
 '}'
```


# Type
A Type is the abstract base metaclass for all type definition constructs specified by SMDL. A type must have a Uuid attribute representing a Universally Unique Identifier (UUID) as defined above. 


## LanguageType 
A LanguageType is the abstract base metaclass for value types (where instances are defined by their value), and references to value types. Also the Smdl Catalogue schema defines reference types (where instances are defined by their reference, i.e. their location in memory) which are derived from LanguageType as well.

### ValueType
An instance of a ValueType is uniquely determined by its value. Two instances of a value type are said to be equal if they have equal values. Value types include simple types like enumerations and integers, and composite types like structures and arrays.

#### SimpleType
A simple type is a type that can be represented by a simple value. Simple types include primitive types as well as user-defined Enumeration, Integer, Float and String types.

##### Enumeration
An Enumeration type represents one of a number of pre-defined enumeration literals. The Enumeration language element can be used to create user-defined enumeration types. An enumeration must always contain at least one EnumerationLiteral, each having a name and an integer Value attached to it.
All enumeration literals of an enumeration type must have unique names and values, respectively.

```xsmpcat
/**
 * Enumeration description
 */
 'enum' enum_name
 '{'
     literal_name '=' Expression
     (',' literal_name '=' Expression)*
 '}'
```

##### Integer
An Integer type represents integer values with a given range of valid values (via the Minimum and Maximum attributes). The Unit element can hold a physical unit that can be used by applications to ensure physical unit integrity across models.
Optionally, the PrimitiveType used to encode the integer value may be specified (one of Int8, Int16, Int32, Int64, UIn8, UInt16, UInt32, UInt64, where the default is Int32).

```xsmpcat
/**
 * Integer description
 * 
 * @unit integer_unit
 */
 'integer' integer_name ('extends' [PrimitiveType])? ('in' (MinimumExpression|'*') '...' (MaximumExpression|'*'))?
```


##### Float
A Float type represents floating-point values with a given range of valid values (via the Minimum and Maximum expressions). The MinInclusive and MaxInclusive attributes determine whether the boundaries are included in the range or not. The Unit element can hold a physical unit that can be used by applications to ensure physical unit integrity across models.
Optionally, the PrimitiveType used to encode the floating-point value may be specified (one of Float32 or Float64, where the default is Float64).
```xsmpcat
/**
 * Float description
 * 
 * @unit float_unit
 */
 'float' float_name ('extends' [PrimitiveType])? ('in' (MinimumExpression|'*') ('...'|'..<'|'<..'|'<.<') (MaximumExpression|'*'))?
```

##### String
A String type represents fixed Length string values base on Char8. The String language element defines an Array of Char8 values, but allows a more natural handling of it, e.g. by storing a string value as one string, not as an array of individual characters.
As with arrays, SMDL does not allow defining variable-sized strings, as these have the same problems as dynamic arrays (e.g. their size is not know up-front, and their use requires memory allocation).

```xsmpcat
/**
 * String description
 */
 'string' string_name '['LengthExpression']'
```


#### Structure

A Structure type collects an arbitrary number of Fields representing the state of the structure. 
Within a structure, each field needs to be given a unique name. In order to arrive at semantically correct (data) type definitions, a structure type may not be recursive, i.e. a structure may not have a field that is typed by the structure itself.
A structure can also serve as a namespace to define an arbitrary number of Constants.


```xsmpcat
/**
 * Structure description
 */
 'struct' struct_name
 '{'
      (Constant|Field)*
 '}'
```

#### Class
The Class metaclass is derived from Structure. A class may be abstract (attribute Abstract), and it may inherit from a single base class (implementation inheritance), which is represented by the Base link.
As the Class metaclass is derived from Structure it can contain constants and fields. Further, it can have arbitrary numbers of properties (Property elements), operations (Operation elements), and associations (Association elements).

```xsmpcat
/**
 * Class description
 */
 'abstract'? 'class' class_name ('extends' [Class])?
 '{'
      (Constant|Field|Property|Operation|Association)*
 '}'
```


#### Exception
An Exception represents a non-recoverable error that can occur when calling into an Operation or Property getter/setter (within an Operation this is represented by the RaisedException links and within a Property this is represented by the GetRaises and SetRaises links, respectively).
An Exception can contain constants and fields (from Structure) as well as operations, properties and associations (from Class). The fields represent the state variables of the exception which carry additional information when the exception is raised.
Furthermore, an Exception may be Abstract (from Class), and it may inherit from a single base exception (implementation inheritance), which is represented by the Base link (from Class).
```xsmpcat
/**
 * Exception description
 */
 'abstract'? 'exception' exception_name ('extends' [Exception])?
 '{'
      (Constant|Field|Property|Operation|Association)*
 '}'
```
#### Array
An Array type defines a fixed-size array of identically typed elements, where ItemType defines the type of the array items, and Size defines the number of array items.
Multi-dimensional arrays are defined when ItemType is an Array type as well.
Dynamic arrays are not supported by SMDL, as they are not supported by some potential target platforms, and introduce various difficulties in memory management. 

 Remarks 
  : Nevertheless, specific mechanisms are available to allow dynamic collections of components, either for containment (composition) or references (aggregation). 

```xsmpcat
/**
 * Array description
 */
 'using' array_name '=' [ValueType] '[' SizeExpression ']'
```
### ReferenceType

ReferenceType serves as an abstract base metaclass for Interface and Component. An instance of a ReferenceType is identified by a reference to it - as opposed to an instance of a ValueType which is identified by its value. A reference type may have various optional elements: 
 - Constant elements specify constants defined in the reference type's name scope;
 - Property elements specify the reference type's properties; 
 -  and Operation elements specify the reference type's operations. 


#### Interface
An Interface is a reference type that serves as a contract in a loosely coupled architecture. It has the ability to contain constants, properties and operations (from ReferenceType). An Interface may inherit from other interfaces (interface inheritance), which is represented via the Base links.

 Remarks 
  :  It is strongly recommended to only use value types, references and other interfaces in the properties and operations of an interface (i.e. not to use models). Otherwise, a dependency between a model implementing the interface, and other models referenced by this interface is introduced, which is against the idea of interface-based or component-based design. 
           
```xsmpcat
/**
 * Interface description
 */
 'interface' interface_name ('extends' [Interface] (',' [Interface])*)?
 '{'
      (Constant|Property|Operation)*
 '}'
```


#### Component

A Component is a reference type and hence inherits the ability to hold constants, properties and operations. As a Component semantically forms a deployable unit, it may use the available object and component mechanisms of the standard. Apart from the ability to specify a Base component (single implementation inheritance), a component may have various optional elements: 
 - Interface links specify interfaces that the component provides (in SMP this implies that the component implements these interfaces); 
 -  EntryPoint elements allow the component to be scheduled (via the Scheduler service) and to listen to global events (via the EventManager service); 
 -  EventSink elements specify which events the component can receive (these may be registered with other components' event sources);  
 - EventSource elements specify events that the component can emit (other components may register their associated event sink(s) with these);  
 - Field elements define a component's internal state; 
 - and  Association elements express associations to other components or fields of other components. 
 -  Container elements define containment of other components (composition).  
 - Reference elements define reference to other components (aggregation).
           
##### Model
The Model metaclass is a component and hence inherits all component mechanisms.
These mechanisms allow using various different modelling approaches.
For a class-based design, a Model may provide a collection of Field elements to define its internal state. For scheduling and global events, a Model may provide a collection of EntryPoint elements that can be registered with the Scheduler or EventManager services of a Simulation Environment.
For an interface-based design, a Model may provide (i.e. implement) an arbitrary number of interfaces, which is represented via the Interface links.
For a component-based design, a Model may provide Container elements to contain other models (composition), and Reference elements to reference other components (aggregation). These components can either be models or services.
For an event-based design, a Model may support inter-model events via the EventSink and EventSource elements.
For a dataflow-based design, the fields of a Model can be tagged as Input or Output fields.
In addition, a Model may have Association elements to express associations to other models or fields of other models.
```xsmpcat
/**
 * Model description
 */
 'model' model_name ('extends' [Model])? ('implements' [Interface] (',' [Interface])*)?
 '{'
      (Constant|Property|Operation|Field|Association|Container|Reference|EntryPoint|EventSink|EventSource)*
 '}'
```
     
##### Service
The Service metaclass is a component and hence inherits all component mechanisms. A Service can reference one or more interfaces via the Interface links (inherited from Component), where at least one of them must be derived from Smp::IService, which qualifies it as a service interface.

```xsmpcat
/**
 * Service description
 */
 'service' service_name ('extends' [Service])? ('implements' [Interface] (',' [Interface])*)?
 '{'
      (Constant|Property|Operation|Field|Association|Container|Reference|EntryPoint|EventSink|EventSource)*
 '}'
```

## EventType
An EventType is used to specify the type of an event. This can be used not only to give a meaningful name to an event type, but also to link it to an existing simple type (via the EventArgs attribute) that is passed as an argument with every invocation of the event.
```xsmpcat
/**
 * EventType description
 */
 'event' event_name ('extends' [SimpleType])?
```


# Constant
A Constant is a feature that is typed by a simple type and that must have a Value.
```xsmpcat
/**
 * Constant description
 */
 'constant' [SimpleType] constant_name '=' Expression
```

# Field
A Field is a feature that is typed by any value type but String8, and that may have a Default value.
The Transient attribute defines how the field is published to the simulation environment. Only non-transient fields are stored using external persistence. The visibility to the user within the simulation environment can be controlled via the standard SMP attribute "View". By default, the State flag is set to true and the View attribute defaults to "None" when not applied.
The Input and Output attributes define whether the field value is an input for internal calculations (i.e. needed in order to perform these calculations), or an output of internal calculations (i.e. modified when performing these calculations). These flags default to false, but can be changed from their default value to support dataflow-based design.
```xsmpcat
/**
 * Field description
 */
 ('input'? & 'output'? & 'transient'?) 'field' [ValueType] field_name ('=' Expression)?
```


# Operation
An Operation may have an arbitrary number of parameters.
An Operation may specify an arbitrary number of exceptions that it can raise (RaisedException).
```xsmpcat
/**
 * Operation description
 */
 'def' ('void' | [LanguageType] return_parameter_name?) operation_name '(' (Parameter (',' Parameter)*)? ')' ('throws' [Exception] (','[Exception])*)?
```
## Parameter
A Parameter has a Type and a Direction, where the direction may have the values in, out or inout.
When referencing a value type, a parameter may have an additional Default value, which can be used by languages that support default values for parameters.
```xsmpcat
 ('in'|'out'|'inout')? [LanguageType] parameter_name ('=' Expression)?
```

# Property
A Property has a similar syntax as a Field: It is a feature that references a language type. However, the semantics is different in that a property does not represent a state and that it can be assigned an Access attribute to specify how the property can be accessed (either readWrite, readOnly, or writeOnly, see AccessKind).
Furthermore, a property can be assigned a Category attribute to help grouping the properties within its owning type, and a property may specify an arbitrary number of exceptions that it can raise in its getter (GetRaises) and/or setter (SetRaises).

Remark
    : The category can be used in applications as ordering or filtering criterion, for example in a property grid. The term "property" used here closely corresponds in its semantics to the same term in the Java Beans specification and in the Microsoft .NET framework. That is, a property formally represents a "getter" or a "setter" operation or both which allow accessing state or configuration information (or derived information thereof) in a controlled way and which can also be exposed via interfaces (in contrast to fields). 
           
```xsmpcat
/**
 * Property description
 */
 ('readWrite'|'readOnly'|'writeOnly')? 'property' [LanguageType] property_name ('get throws' [Exception](','[Exception])*)? ('set throws' [Exception](','[Exception])*)? ('->' [Field])?
```

# Association

An Association is a feature that is typed by a language type (Type link). An association always expresses a reference to an instance of the referenced language type. This reference is either another model (if the Type link refers to a Model or Interface), or it is a field contained in another model (if the Type link refers to a ValueType).
```xsmpcat
/**
 * Association description
 */
 'association' [LanguageType] association_name
```
# Container
A Container defines the rules of composition (containment of children) for a Component.
The type of components that can be contained is specified via the Type link.
The Lower and Upper attributes specify the multiplicity, i.e. the number of possibly stored components. Therein the upper bound may be unlimited, which is represented by Upper=-1. 
Furthermore, a container may specify a default implementation of the container type via the DefaultComponentl link.

 Remark 
  :  SMDL support tools may use this during instantiation (i.e. during model integration) to select an initial implementation for newly created contained components. 

```xsmpcat
/**
 * Container description
 */
 'container' [LanguageType] Multiplicity container_name ('=' [Component])?
```
**with Mutiplicity :**
   
| Multiplicity | lower bound| upper bound|
|--|--|--|
|  | 1 | 1 |
| ? | 0 | 1 |
| [*] | 0 | -1|
| [+] | 1 | -1|
| [ Expr] | Expr | Expr  |
| [ Expr ... *] | Expr | -1|
| [ Expr1 ... Expr2] | Expr1 | Expr2  |


# Reference

A Reference defines the rules of aggregation (links to components) for a Component.
The type of components (models or services) that can be referenced is specified by the Interface link. Thereby, a service reference is characterized by an interface that is derived from Smp::IService.
The Lower and Upper attributes specify the multiplicity, i.e. the number of possibly held references to components implementing this interface. Therein the upper bound may be unlimited, which is represented by Upper=-1.

```xsmpcat
/**
 * Reference description
 */
 'reference' [Interface] Multiplicity reference_name
```
**with Mutiplicity :**
   
| Multiplicity | lower bound| upper bound|
|--|--|--|
|  | 1 | 1 |
| ? | 0 | 1 |
| [*] | 0 | -1|
| [+] | 1 | -1|
| [ Expr] | Expr | Expr  |
| [ Expr ... *] | Expr | -1|
| [ Expr1 ... Expr2] | Expr1 | Expr2  |


# EntryPoint
An EntryPoint is a named element of a Component (Model or Service). It corresponds to a void operation taking no parameters that can be called from an external client (e.g. the Scheduler or Event Manager services). An Entry Point can reference both Input fields (which must have their Input attribute set to true) and Output fields (which must have their Output attribute set to true). These links can be used to ensure that all component output fields are updated before the entry point is called, or that all input fields can be used after the entry point has been called.
```xsmpcat
/**
 * EntryPoint description
 */
 'entrypoint' entrypoint_name
```


# EventSink
An EventSink is used to specify that a Component can receive a specific event using a given name. An EventSink can be connected to any number of EventSource instances.
```xsmpcat
/**
 * EnventSink description
 */
 'enventsink' [EventType] eventsink_name
```


# EventSource
An EventSource is used to specify that a Component publishes a specific event under a given name. The @singlecast attribute can be used to specify whether any number of sinks can connect to the source (the default), or only a single sink can connect.

   Remark 
     : A tool for model integration can use the Multicast attribute to configure the user interface accordingly to ease the specification of event links. 
        

```xsmpcat
/**
 * EnventSource description
 * @singlecast
 */
 'eventsource' [EventType] eventsink_name
```

# Expressions
Xsmpcat language support the folowing C++ operators in expressions
```
+, -, *, /, %,==, !=, >, <, >=, <=,!, &&, ||, &, |, ^, ~, <<, >>
```

The folowing built-in are available : $PI, $E, $sin(Expr), $cos(Expr), $tan(Expr), $acos(Expr), $asin(Expr), $atan(Expr), $cosh(Expr), $sinh(Expr), $tanh(Expr), $exp(Expr), $log(Expr), $log10(Expr), $expm1(Expr), $log1p(Expr), $sqrt(Expr), $ceil(Expr), $floor(Expr), $abs(Expr), $cosf(Expr), $sinf(Expr), $tanf(Expr), $acosf(Expr), $asinf(Expr), $atanf(Expr), $coshf(Expr), $sinhf(Expr), $tanhf(Expr), $expf(Expr), $logf(Expr), $log10f(Expr), $expm1f(Expr), $log1pf(Expr), $sqrtf(Expr), $ceilf(Expr), $floorf(Expr) and $absf(Expr)

Custom literals suffixs to write duration : y,d,h,mn,s,ms,us,ns wich convert the integral value to the number of nano seconds for respectively a year, a day, an hour, a minute, a second, a milisecond, a microsecond and a nanosecond.
```
10y + 5d + 3h + 15mn + 10s + 120ms + 40us - 1ns
```



