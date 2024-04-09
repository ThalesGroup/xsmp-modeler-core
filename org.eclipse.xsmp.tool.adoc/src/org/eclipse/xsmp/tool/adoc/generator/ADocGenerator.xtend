/*******************************************************************************
 * Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.tool.adoc.generator

import com.google.inject.Inject
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.model.xsmp.Association
import org.eclipse.xsmp.model.xsmp.Catalogue
import org.eclipse.xsmp.model.xsmp.Class
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.Constant
import org.eclipse.xsmp.model.xsmp.Container
import org.eclipse.xsmp.model.xsmp.EntryPoint
import org.eclipse.xsmp.model.xsmp.Enumeration
import org.eclipse.xsmp.model.xsmp.EventSink
import org.eclipse.xsmp.model.xsmp.EventSource
import org.eclipse.xsmp.model.xsmp.EventType
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.Float
import org.eclipse.xsmp.model.xsmp.Integer
import org.eclipse.xsmp.model.xsmp.Interface
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.Namespace
import org.eclipse.xsmp.model.xsmp.NativeType
import org.eclipse.xsmp.model.xsmp.Operation
import org.eclipse.xsmp.model.xsmp.Reference
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.model.xsmp.Type
import org.eclipse.xsmp.model.xsmp.VisibilityKind
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import org.eclipse.xsmp.util.ViewKind
import org.eclipse.xsmp.model.xsmp.Property
import org.eclipse.xsmp.model.xsmp.AccessKind

class ADocGenerator extends AbstractGenerator {

    @Inject extension ADocUtil

    override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
        generateCatalogue(input.getContents().get(0) as Catalogue, fsa);
    }

    def void generateCatalogue(Catalogue catalogue, IFileSystemAccess2 fsa) {
        // Catalogue file
        val fileName = catalogue.name + "-gen.adoc"
        val catTitle = catalogue.title !== null ? catalogue.title : "Catalogue " + catalogue.name
        // val externalReferences = EcoreUtil.ExternalCrossReferencer.find(catalogue).keySet.filter(Type)
        fsa.generateFile(fileName, ADocOutputConfigurationProvider.DOC, '''
            = «catTitle»
            «catalogue.description»
            
            «FOR namespace : catalogue.eAllContents.filter(Namespace).filter[hasTypeMembers].toIterable»
                «namespace.generateNamespace»
            «ENDFOR»
        ''')
    }

    def CharSequence generateNamespace(Namespace namespace) {
        '''
            == Namespace «namespace.fqn.toString("::")»
            ++++
            «namespace.description»
            ++++
            «namespace.generateMermaidNamespace»
            
            «FOR type : namespace.member.filter(Type)»«type.generateType»«ENDFOR»
        '''
    }

    def CharSequence generateType(Type type) {
        '''
            [#«type.fqn.toString("-")»]
            === «type.eClass.name» «type.name»
            ++++
            «type.description»
            ++++
            
            .«type.name»'s informations
            [%autowidth.stretch]
            |===
            «type.generateTypeInfoDetails»
            |===
            
            «type.generateMermaid»
            
            «type.generateContent»
        '''
    }

    def dispatch CharSequence generateMermaid(EObject obj) {
    }

    def dispatch CharSequence generateMermaid(Component component) {
        val interfaces = component.interface
        val base = component.base
        val references = component.member.filter(Reference)
        val containers = component.member.filter(Container)
        '''
            ==== Diagram
            
            [.center]
            [mermaid]
            ....
            classDiagram
                «IF base !== null»
                    class «base.name» {
                        <<«base.eClass.name»>>
                    }
                «ENDIF»
                
                «FOR interf : interfaces»
                    class «interf.name» {
                        <<«interf.eClass.name»>>
                    }
                «ENDFOR»
                
                «FOR ref : references»
                    class «ref.interface.name» {
                        <<«ref.eClass.name»>>
                    }
                «ENDFOR»
                
                «FOR container : containers»
                    class «container.type.name» {
                        <<«container.eClass.name»>>
                    }
                «ENDFOR»
                
                class «component.name» {
                    «component.mermaidClassAttributes»
                }
                
                «IF base !== null»«base.name» <|-- «component.name» : extends«ENDIF»
                «FOR interf : interfaces»
                    «interf.name» <|.. «component.name» : implements
                «ENDFOR»
                «FOR ref : references»
                    «component.name» "«ref.lower»..«ref.upper»" o-- «ref.interface.name» : «ref.name»
                «ENDFOR»
                «FOR container : containers»
                    «component.name» "«container.lower»..«container.upper»" *-- «container.type.name» : «container.name»
                «ENDFOR»
            ....
        '''
    }

    def dispatch CharSequence generateMermaid(Interface interf) {
        val base = interf.base
        '''
            ==== Diagram
                        
            [.center]
            [mermaid]
            ....
            classDiagram
                «FOR b : base»
                    class «b.name» {
                        <<Interface>>
                    }
                «ENDFOR»
            
                class «interf.name» {
                    «interf.mermaidClassAttributes»
                }
                
                «FOR b : base»
                    «b.name» <|-- «interf.name» : extends
                «ENDFOR»
            ....
        '''
    }

    def dispatch CharSequence generateMermaid(Structure structure) {
        '''
            ==== Diagram
                        
            [.center]
            [mermaid]
            ....
            classDiagram
                «IF structure instanceof Class»
                    «IF (structure as Class).base !== null»
                        class «(structure as Class).base.name» {
                            <<«(structure as Class).base.eClass.name»>>
                        }
                    «ENDIF»
                «ENDIF»
                
                class «structure.name» {
                    «structure.mermaidClassAttributes»
                }
                
                «IF structure instanceof Class»
                    «IF (structure as Class).base !== null»
                        «(structure as Class).base.name» <|-- «structure.name»
                    «ENDIF»
                «ENDIF»
            ....
        '''
    }

    def dispatch CharSequence generateMermaidMembers(EObject obj) {
    }

    def dispatch CharSequence generateMermaidMembers(Field field) {
        '''
            «field.type.name» «field.name»
        '''
    }

    def dispatch CharSequence generateMermaidMembers(EventSource eventSource) {
        '''
            EventSource<«eventSource.type.name»> «eventSource.name»
        '''
    }

    def dispatch CharSequence generateMermaidMembers(EventSink eventSink) {
        '''
            EventSink<«eventSink.type.name»> «eventSink.name»
        '''
    }

    def dispatch CharSequence generateMermaidMembers(EntryPoint entryPoint) {
        '''
            EntryPoint «entryPoint.name»
        '''
    }

    def dispatch CharSequence generateMermaidMembers(Constant constant) {
        '''
            constexpr «constant.type.name» «constant.name» = «constant.value.shortValue»
        '''
    }

    def dispatch CharSequence generateMermaidMembers(Association association) {
        '''
            «association.type.name»* «association.name»
        '''
    }

    def dispatch CharSequence generateMermaidMembers(Operation operation) {
        '''
            «operation.name»(«FOR param : operation.parameter SEPARATOR ','»«param.direction» «param.type.name»«ENDFOR») «IF operation.returnParameter === null »void«ELSE»«operation.returnParameter.type.name»«ENDIF»
        '''
    }

    def dispatch CharSequence generateContent(EObject obj) {
    }

    def dispatch CharSequence generateContent(NamedElementWithMembers element) {
        '''
            «element.generateField»
            
            «element.generateConstant»
            
            «element.generateEventSource»
            
            «element.generateEventSink»
            
            «element.generateEntryPoint»
            
            «element.generateReference»
            
            «element.generateContainer»
            
            «element.generateAssociation»
            
            «element.generateOperation»
        '''
    }

    def dispatch CharSequence generateContent(Enumeration enumeration) {
        '''
            .«enumeration.name»'s literals
            [%autowidth.stretch]
            |===
            |Name |Value
            
            «FOR literal : enumeration.literal»
                |«literal.name»
                |«literal.value.shortValue»
            «ENDFOR»
            |===
        '''
    }
    
    def CharSequence generateField(NamedElementWithMembers element) {
        val fields = element.member.filter(Field)
        '''
            «IF !fields.empty»
                ==== Fields
                The «element.name» «element.eClass.name.toLowerCase» provides the following fields:
                
                «FOR v : VisibilityKind.values»
                    «element.generateField(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateField(NamedElementWithMembers element, VisibilityKind v) {
        val fields = element.member.filter(Field).filter[visibility == v]
        val hasUnit = fields.exists[unit !== null]
        val hasInitialValue = fields.exists[^default !== null]
        val hasDescription = fields.exists[description !== null && !description.isEmpty]
        val hasViewKind = fields.exists[viewKind !== ViewKind.NONE]
        '''
            «IF !fields.empty»
                .«element.name»'s «v.getName()» fields
                [%autowidth.stretch]
                |===
                |Kind |Name |Type «IF hasUnit»|Unit «ENDIF»«IF hasViewKind»|View Kind «ENDIF»«IF hasInitialValue»|Initial Value «ENDIF»«IF hasDescription»|Description«ENDIF»
                
                «FOR field : fields»
                    |«FOR kind : field.fieldKinds SEPARATOR ', '»«kind»«ENDFOR»
                    |«field.name»
                    |«field.type.crossReference(field)»
                    «IF hasUnit»|«field.unit»«ENDIF»
                    «IF hasViewKind»|«field.viewKind.label»«ENDIF»
                    «IF hasInitialValue»|«field.^default.shortValue»«ENDIF»
                    «IF hasDescription»|«field.description?.espaceDescription»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateConstant(NamedElementWithMembers element) {
        val constants = element.member.filter(Constant)
        '''
            «IF !constants.empty»
                ==== Constants
                The «element.name» «element.eClass.name.toLowerCase» provides the following constants:
                
                «FOR v : VisibilityKind.values»
                    «element.generateConstant(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateConstant(NamedElementWithMembers element, VisibilityKind v) {
        val constants = element.member.filter(Constant).filter[visibility == v]
        val hasDescription = constants.exists[description !== null && !description.isEmpty]
        '''
            «IF !constants.empty»
                .«element.name»'s «v.getName()» constants
                [%autowidth.stretch]
                |===
                |Name |Type |Value «IF hasDescription»|Description«ENDIF»
                
                «FOR constant : constants»
                    |«constant.name»
                    |«constant.type.crossReference(constant)»
                    |«constant.value.shortValue»
                    «IF hasDescription»|«constant.description?.espaceDescription»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateEventSource(NamedElementWithMembers element) {
        val eventSources = element.member.filter(EventSource)
        '''
            «IF !eventSources.empty»
                ==== Event Sources
                The «element.name» «element.eClass.name.toLowerCase» provides the following event sources:
                
                «FOR v : VisibilityKind.values»
                    «element.generateEventSource(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateEventSource(NamedElementWithMembers element, VisibilityKind v) {
        val eventSources = element.member.filter(EventSource).filter[visibility == v]
        val hasDescription = eventSources.exists[description !== null && !description.isEmpty]
        '''
            «IF !eventSources.empty»
                .«element.name»'s «v.getName()» Event Sources
                [%autowidth.stretch]
                |===
                |Name |Type «IF hasDescription»|Description«ENDIF»
                
                «FOR eventSource : eventSources»
                    |«eventSource.name»
                    |«eventSource.type.crossReference(eventSource)»
                    «IF hasDescription»|«eventSource.description?.espaceDescription»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateEventSink(NamedElementWithMembers element) {
        val eventSinks = element.member.filter(EventSink)
        '''
            «IF !eventSinks.empty»
                ==== Event Sinks
                The «element.name» «element.eClass.name.toLowerCase» provides the following event sinks:
                
                «FOR v : VisibilityKind.values»
                    «element.generateEventSink(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateEventSink(NamedElementWithMembers element, VisibilityKind v) {
        val eventSinks = element.member.filter(EventSink).filter[visibility == v]
        val hasDescription = eventSinks.exists[description !== null && !description.isEmpty]
        '''
            «IF !eventSinks.empty»
                .«element.name»'s «v.getName()» Event Sink
                [%autowidth.stretch]
                |===
                |Name |Type «IF hasDescription»|Description«ENDIF»
                
                «FOR eventSink : eventSinks»
                    |«eventSink.name»
                    |«eventSink.type.crossReference(eventSink)»
                    «IF hasDescription»|«eventSink.description?.espaceDescription»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateEntryPoint(NamedElementWithMembers element) {
        val entryPoints = element.member.filter(EntryPoint)
        '''
            «IF !entryPoints.empty»
                ==== Entry Points
                The «element.name» «element.eClass.name.toLowerCase» provides the following entry points:
                
                «FOR v : VisibilityKind.values»
                    «element.generateEntryPoint(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateEntryPoint(NamedElementWithMembers element, VisibilityKind v) {
        val entryPoints = element.member.filter(EntryPoint).filter[visibility == v]
        val hasDescription = entryPoints.exists[description !== null && !description.isEmpty]
        val hasViewKind = entryPoints.exists[viewKind !== ViewKind.NONE]
        '''
            «IF !entryPoints.empty»
                .«element.name»'s «v.getName()» Entry Points
                [%autowidth.stretch]
                |===
                |Name «IF hasViewKind»|View Kind «ENDIF»«IF hasDescription»|Description«ENDIF»
                
                «FOR entryPoint : entryPoints»
                    |«entryPoint.name»
                    «IF hasViewKind»|«entryPoint.viewKind.label»«ENDIF»
                    «IF hasDescription»|«entryPoint.description?.espaceDescription»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateReference(NamedElementWithMembers element) {
        val references = element.member.filter(Reference)
        '''
            «IF !references.empty»
                ==== References
                The «element.name» «element.eClass.name.toLowerCase» provides the following references:
                
                «FOR v : VisibilityKind.values»
                    «element.generateReference(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateReference(NamedElementWithMembers element, VisibilityKind v) {
        val references = element.member.filter(Reference).filter[visibility == v]
        val hasDescription = references.exists[description !== null && !description.isEmpty]
        '''
            «IF !references.empty»
                .«element.name»'s «v.getName()» references
                [%autowidth.stretch]
                |===
                |Name |Type |Minimum |Maximum «IF hasDescription»|Description«ENDIF»
                
                «FOR reference : references»
                    |«reference.name»
                    |«reference.interface.crossReference(reference)»
                    |«reference.lower»
                    |«reference.upper == -1 ? "unlimited" : reference.upper»
                    «IF hasDescription»|«reference.description?.espaceDescription»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateContainer(NamedElementWithMembers element) {
        val containers = element.member.filter(Container)
        '''
            «IF !containers.empty»
                ==== Containers
                The «element.name» «element.eClass.name.toLowerCase» provides the following containers:
                
                «FOR v : VisibilityKind.values»
                    «element.generateContainer(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateContainer(NamedElementWithMembers element, VisibilityKind v) {
        val containers = element.member.filter(Container).filter[visibility == v]
        val hasDescription = containers.exists[description !== null && !description.isEmpty]
        '''
            «IF !containers.empty»
                .«element.name»'s «v.getName()» containers
                [%autowidth.stretch]
                |===
                |Name |Type |Minimum |Maximum «IF hasDescription»|Description«ENDIF»
                
                «FOR container : containers»
                    |«container.name»
                    |«container.type.crossReference(container)»
                    |«container.lower»
                    |«container.upper == -1 ? "unlimited" : container.upper»
                    «IF hasDescription»|«container.description?.espaceDescription»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateOperation(NamedElementWithMembers element) {
        val operations = element.member.filter(Operation)
        '''
            «IF !operations.empty»
                ==== Operations
                The «element.name» «element.eClass.name.toLowerCase» provides the following operations:
                
                «FOR operation : operations»
                    «val hasDefaultValue = operation.parameter.exists[^default !== null]»
                    «val hasDescription = operation.parameter.exists[description !== null && !description.isEmpty]»
                    «val hasViewKind = operation.viewKind !== ViewKind.NONE»
                    ===== Operation «operation.name» «IF hasViewKind»(«operation.viewKind.name»)«ENDIF»
                    «operation.description»
                    
                    «IF !operation.parameter.empty || operation.returnParameter !== null»
                        .«operation.name»'s parameters
                        [%autowidth.stretch]
                        |===
                        |Direction |Name |Type «IF hasDefaultValue»|Default Value «ENDIF»«IF hasDescription»|Description«ENDIF»
                        
                        «FOR param : operation.parameter»
                            |«param.direction» |«param.name» |«param.type.crossReference(param)» «IF hasDefaultValue»|«param.^default.shortValue» «ENDIF»«IF hasDescription»|«param.description?.espaceDescription»«ENDIF»
                        «ENDFOR»
                        «IF operation.returnParameter !== null»
                            |return |«operation.returnParameter.name» |«operation.returnParameter.type.crossReference(operation.returnParameter)» «IF hasDefaultValue»|  «ENDIF»|«operation.returnParameter.description?.espaceDescription»
                        «ENDIF»
                        |===
                    «ENDIF»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateAssociation(NamedElementWithMembers element) {
        val associations = element.member.filter(Association)
        '''
            «IF !associations.empty»
                ==== Associations
                The «element.name» «element.eClass.name.toLowerCase» provides the following associations:
                
                «FOR v : VisibilityKind.values»
                    «element.generateAssociation(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateAssociation(NamedElementWithMembers element, VisibilityKind v) {
        val associations = element.member.filter(Association).filter[visibility == v]
        val hasDescription = associations.exists[description !== null && !description.isEmpty]
        '''
            «IF !associations.empty»
                .«element.name»'s «v.getName()» containers
                [%autowidth.stretch]
                |===
                |Name |Type «IF hasDescription»|Description«ENDIF»
                
                «FOR association : associations»
                    |«association.name»
                    |«association.type.crossReference(association)»
                    «IF hasDescription»|«association.description»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateProperty(NamedElementWithMembers element) {
        val properties = element.member.filter(Property)
        '''
            «IF !properties.empty»
                ==== Properties
                The «element.name» «element.eClass.name.toLowerCase» provides the following properties:
                
                «FOR v : VisibilityKind.values»
                    «element.generateProperty(v)»
                «ENDFOR»
            «ENDIF»
        '''
    }

    def CharSequence generateProperty(NamedElementWithMembers element, VisibilityKind v) {
        val properties = element.member.filter(Property).filter[visibility == v]
        val hasDescription = properties.exists[description !== null && !description.isEmpty]
        val hasViewKind = properties.exists[viewKind !== ViewKind.NONE]
        val hasAccess = properties.exists[access !== AccessKind.READ_WRITE]
        '''
            «IF !properties.empty»
                .«element.name»'s «v.getName()» properties
                [%autowidth.stretch]
                |===
                |Name |Type «IF hasViewKind»|View Kind «ENDIF»«IF hasAccess»|Access «ENDIF»«IF hasDescription»|Description«ENDIF»
                
                «FOR property : properties»
                    |«property.name»
                    |«property.type.crossReference(property)»
                    «IF hasViewKind»|«property.viewKind.label»«ENDIF»
                    «IF hasAccess»|«property.access.getName»«ENDIF»
                    «IF hasDescription»|«property.description»«ENDIF»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generateMermaidNamespace(Namespace namespace) {
        val types = namespace.member.filter(Type)
        '''
            [.center]
            [mermaid]
            ....
            classDiagram
            
            direction LR
            
            namespace «namespace.name» {
                «IF types.size > 8»
                    «FOR type : types.take(7)»
                        class «type.name» {
                            <<«type.eClass.name»>>
                        }                        
                    «ENDFOR»
                    class - {
                        <<...>>
                    }
                «ELSE»
                    «FOR type : types»
                        class «type.name» {
                            <<«type.eClass.name»>>
                        }
                    «ENDFOR»
                «ENDIF»
            }
            ....
        '''
    }
    
    def dispatch CharSequence generateTypeInfoDetails(Type type) {
        '''
            .^h|Visibility |«type.visibility»
            .^h|Qualified Name |«type.fqn.toString("::")»
            .^h|UUID |«type.uuid»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(Integer type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            .^h|Primitive Type |«IF type.primitiveType !== null»«type.primitiveType.crossReference(type)»«ELSE»Smp::Int32«ENDIF»
            .^h|Minimum |«type.minimum.shortValue»
            .^h|Maximum |«type.maximum.shortValue»
            «IF type.unit !== null».^h|Unit |«type.unit»«ENDIF»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(Float type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            .^h|Primitive Type |«IF type.primitiveType !== null»«type.primitiveType.crossReference(type)»«ELSE»Smp::Float64 TODO VERIFIER«ENDIF»
            .^h|Minimum |«type.minimum.shortValue» «IF !type.minInclusive»(exclusive)«ENDIF»
            .^h|Maximum |«type.maximum.shortValue» «IF !type.maxInclusive»(exclusive)«ENDIF»
            «IF type.unit !== null».^h|Unit |«type.unit»«ENDIF»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(Array type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            .^h|Item Type |«type.itemType.crossReference(type)»
            .^h|Size |«type.size.shortValue»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(EventType type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            .^h|Event Type |«type.eventArgs.crossReference(type)»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(org.eclipse.xsmp.model.xsmp.String type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            .^h|Length |«type.length.shortValue»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(NativeType type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            .^h|Type |«type.type»
            «IF type.location !== null».^h|Location |«type.location»«ENDIF»
            «IF type.namespace !== null».^h|Namespace |«type.namespace»«ENDIF»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(Class type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            «IF type.base !== null».^h|Base Class |«type.base.crossReference(type)»«ENDIF»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(Interface type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            «IF !type.base.isEmpty».«type.base.size»+.^h|Extends «FOR i : type.base»|«i.crossReference(type)» «ENDFOR»«ENDIF»
        '''
    }

    def dispatch CharSequence generateTypeInfoDetails(Component type) {
        '''
            «(type as Type)._generateTypeInfoDetails»
            «IF type.base !== null».^h|Extends |«type.base.crossReference(type)»«ENDIF»
            «IF !type.interface.isEmpty».«type.interface.size»+.^h|Implements «FOR i : type.interface»|«i.crossReference(type)» «ENDFOR»«ENDIF»
        '''
    }

    def CharSequence crossReference(Type type, EObject context) {
        return context.eResource.equals(type.eResource)
            ? '''
            <<«type.fqn.toString("-")»,«type.name»>>
        '''
            : '''
            <<«type.fqn.toString("-")»,«type.fqn.toString("::")»>>
        '''
    }
    
    private def CharSequence mermaidClassAttributes(NamedElementWithMembers element) {
        val members = element.member.reject(Operation).reject(Container).reject(Reference)
        val operations = element.member.filter(Operation)
        val maxMembers = Math.max(5, 10 - operations.size)
        val maxOperations = Math.max(5, 10 - members.size)
        '''
            «IF members.size > maxMembers»
                «FOR m : members.take(maxMembers - 1)»
                    «m.generateMermaidMembers»
                «ENDFOR»
                ...
            «ELSE»
                «FOR m : members.take(maxMembers)»
                    «m.generateMermaidMembers»
                «ENDFOR»
            «ENDIF»
            
            «IF operations.size > maxOperations»
                «FOR m : operations.take(maxOperations - 1)»
                    «m.generateMermaidMembers»
                «ENDFOR»
                ...()
            «ELSE»
                «FOR m : operations.take(maxOperations)»
                    «m.generateMermaidMembers»
                «ENDFOR»
            «ENDIF»
        '''
    }

    private def hasTypeMembers(NamedElementWithMembers elem) {
        return !elem.member.filter[it|!(it instanceof Namespace)].empty
    }

    private def List<String> getFieldKinds(Field field) {
        val kinds = new ArrayList<String>()
        if(field.isOutput) kinds.add("output")
        if(field.isInput) kinds.add("input")
        if(field.isTransient) kinds.add("transient")
        if(field.isForcible) kinds.add("forcible")
        if(field.isFailure) kinds.add("failure")
        return kinds
    }

    private def String getUnit(Field field) {
        switch field.type {
            case Float:
                return (field as Float).unit
            case Integer:
                return (field as Integer).unit
            default:
                return null
        }
    }

    private def String espaceDescription(String s) {
        return s.replace('|', '\\|')
    }
}
