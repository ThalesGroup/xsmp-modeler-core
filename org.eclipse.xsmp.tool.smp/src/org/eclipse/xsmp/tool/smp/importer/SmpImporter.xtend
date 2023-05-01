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
package org.eclipse.xsmp.tool.smp.importer

import com.google.common.collect.ImmutableSet
import com.google.inject.Inject
import java.io.ByteArrayInputStream
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xsmp.tool.smp.core.elements.Comment
import org.eclipse.xsmp.tool.smp.core.elements.Documentation
import org.eclipse.xsmp.tool.smp.core.elements.ElementsPackage
import org.eclipse.xsmp.tool.smp.core.elements.NamedElement
import org.eclipse.xsmp.tool.smp.core.types.Array
import org.eclipse.xsmp.tool.smp.core.types.ArrayValue
import org.eclipse.xsmp.tool.smp.core.types.Association
import org.eclipse.xsmp.tool.smp.core.types.Attribute
import org.eclipse.xsmp.tool.smp.core.types.AttributeType
import org.eclipse.xsmp.tool.smp.core.types.BoolValue
import org.eclipse.xsmp.tool.smp.core.types.Char8Value
import org.eclipse.xsmp.tool.smp.core.types.Class
import org.eclipse.xsmp.tool.smp.core.types.Constant
import org.eclipse.xsmp.tool.smp.core.types.DateTimeValue
import org.eclipse.xsmp.tool.smp.core.types.DurationValue
import org.eclipse.xsmp.tool.smp.core.types.ElementReference
import org.eclipse.xsmp.tool.smp.core.types.Enumeration
import org.eclipse.xsmp.tool.smp.core.types.EnumerationValue
import org.eclipse.xsmp.tool.smp.core.types.Field
import org.eclipse.xsmp.tool.smp.core.types.Float
import org.eclipse.xsmp.tool.smp.core.types.Float32Value
import org.eclipse.xsmp.tool.smp.core.types.Float64Value
import org.eclipse.xsmp.tool.smp.core.types.Int16Value
import org.eclipse.xsmp.tool.smp.core.types.Int32Value
import org.eclipse.xsmp.tool.smp.core.types.Int64Value
import org.eclipse.xsmp.tool.smp.core.types.Int8Value
import org.eclipse.xsmp.tool.smp.core.types.Integer
import org.eclipse.xsmp.tool.smp.core.types.NativeType
import org.eclipse.xsmp.tool.smp.core.types.Operation
import org.eclipse.xsmp.tool.smp.core.types.Parameter
import org.eclipse.xsmp.tool.smp.core.types.ParameterDirectionKind
import org.eclipse.xsmp.tool.smp.core.types.PlatformMapping
import org.eclipse.xsmp.tool.smp.core.types.PrimitiveType
import org.eclipse.xsmp.tool.smp.core.types.Property
import org.eclipse.xsmp.tool.smp.core.types.SimpleArrayValue
import org.eclipse.xsmp.tool.smp.core.types.String
import org.eclipse.xsmp.tool.smp.core.types.String8Value
import org.eclipse.xsmp.tool.smp.core.types.Structure
import org.eclipse.xsmp.tool.smp.core.types.StructureValue
import org.eclipse.xsmp.tool.smp.core.types.Type
import org.eclipse.xsmp.tool.smp.core.types.TypesPackage
import org.eclipse.xsmp.tool.smp.core.types.UInt16Value
import org.eclipse.xsmp.tool.smp.core.types.UInt32Value
import org.eclipse.xsmp.tool.smp.core.types.UInt64Value
import org.eclipse.xsmp.tool.smp.core.types.UInt8Value
import org.eclipse.xsmp.tool.smp.core.types.ValueReference
import org.eclipse.xsmp.tool.smp.core.types.VisibilityElement
import org.eclipse.xsmp.tool.smp.core.types.VisibilityKind
import org.eclipse.xsmp.tool.smp.smdl.catalogue.Catalogue
import org.eclipse.xsmp.tool.smp.smdl.catalogue.CataloguePackage
import org.eclipse.xsmp.tool.smp.smdl.catalogue.Component
import org.eclipse.xsmp.tool.smp.smdl.catalogue.Container
import org.eclipse.xsmp.tool.smp.smdl.catalogue.EntryPoint
import org.eclipse.xsmp.tool.smp.smdl.catalogue.EventSink
import org.eclipse.xsmp.tool.smp.smdl.catalogue.EventSource
import org.eclipse.xsmp.tool.smp.smdl.catalogue.EventType
import org.eclipse.xsmp.tool.smp.smdl.catalogue.Interface
import org.eclipse.xsmp.tool.smp.smdl.catalogue.Namespace
import org.eclipse.xsmp.tool.smp.smdl.catalogue.Reference
import org.eclipse.xsmp.tool.smp.util.SmpUtil
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.resource.SaveOptions
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xsmp.tool.smp.core.types.Value
import org.eclipse.xsmp.util.XsmpUtil

class SmpImporter {

    static final Set<EStructuralFeature> transientFeatures = ImmutableSet.<EStructuralFeature>builder().add(
        ElementsPackage.Literals.DOCUMENT__CREATOR, ElementsPackage.Literals.DOCUMENT__DATE,
        ElementsPackage.Literals.DOCUMENT__TITLE, ElementsPackage.Literals.DOCUMENT__VERSION,
        TypesPackage.Literals.ATTRIBUTE_TYPE__ALLOW_MULTIPLE, TypesPackage.Literals.ATTRIBUTE_TYPE__USAGE,
        TypesPackage.Literals.PROPERTY__CATEGORY, TypesPackage.Literals.TYPE__UUID, TypesPackage.Literals.INTEGER__UNIT,
        TypesPackage.Literals.FLOAT__UNIT, CataloguePackage.Literals.EVENT_SOURCE__MULTICAST).build();

    def doGenerate(Resource resource, IFileSystemAccess2 fsa) {
        // create result resource
        val rs = new XtextResourceSet
        val filename = resource.URI.trimFileExtension.appendFileExtension("xsmpcat").lastSegment
        var r = rs.createResource(fsa.getURI(filename))

        // convert the smpcat model to xsmpcat
        var result = resource.contents.get(0).generate.toString
        val in = new ByteArrayInputStream(result.bytes);
        try {
            r.load(in, rs.getLoadOptions());

            // save the result with format option
            r.save(SaveOptions.newBuilder.format.options.toOptionsMap)
        } catch (Exception e) {
            fsa.generateFile(filename, result)
        }
    }

    @Inject
    extension SmpUtil
        @Inject
     XsmpUtil xsmpUtil

    def CharSequence qfn(NamedElement e) {

        return e.fqn.toString
    }

    def CharSequence qfn(ElementReference<?> e) {
        if (e === null)
            return "typename"
        var ref = e.ref
        if (ref === null || ref.eIsProxy)
            return e.title
        return e.ref.qfn
    }

    def CharSequence name(ElementReference<?> e) {
        if (e === null)
            return "__name__"
        var href = e.ref
        if (href === null || href.eIsProxy)
            return e.title

        return href.name
    }

    def dispatch CharSequence generate(EObject o) {
        ''' /* TODO implements import of «o.eClass.name»: «o.toString» */'''
    }

    def dispatch CharSequence generate(PlatformMapping o) {
    }

    def dispatch CharSequence generate(Catalogue o) {
        '''
            «o.header»
            catalogue «o.name»
            
            «FOR n : o.namespace SEPARATOR '\n'»«n.generate»«ENDFOR»
            
        '''
    }

    def static convertToString(EObject value) {
        return value.eClass().getEAllAttributes().filter[value.eIsSet(it)].map [
            it.getName() + "=\"" + value.eGet(it).toString() + "\""
        ].join(", ")
    }

    def CharSequence header(NamedElement o) {

        var m = o.eClass.EAllStructuralFeatures.filter[transientFeatures.contains(it) && o.eIsSet(it)].map [
            if (it instanceof EAttribute) {
                if (it === CataloguePackage.Literals.EVENT_SOURCE__MULTICAST) {
                    if (!(o as EventSource).multicast)
                        return '''@singlecast'''
                } else {
                    if (it.isMany)
                        return '''
                            «FOR elem : o.eGet(it) as List<?>»
                                @«it.name» «EcoreUtil.convertToString(it.getEAttributeType(), elem)»
                            «ENDFOR»
                        '''
                    else {
                        val value = o.eGet(it)
                        if (value === Boolean.TRUE)
                            return '''
                                @«it.name»
                            '''
                        else if (value === Boolean.FALSE)
                            return null
                        else
                            return '''
                                @«it.name» «EcoreUtil.convertToString(it.getEAttributeType(), value)»
                            '''

                    }

                }
            } else if (it instanceof EReference) {
                if (it.isMany)
                    return '''
                        «FOR elem : o.eGet(it) as List<EObject>»
                            @«it.name» «convertToString(elem)»
                        «ENDFOR»
                    '''
                else {
                    return '''
                        @«it.name» «convertToString(o.eGet(it) as EObject)»
                    '''
                }
            }
        ].toList
        if (o instanceof Operation) {
            for (param : o.parameter.filter[it.direction != ParameterDirectionKind.RETURN])
                if (param.description !== null)
                    m += '''
                        @param «param.name» «param.description»
                    '''.toString
            for (param : o.parameter.filter[it.direction == ParameterDirectionKind.RETURN])
                if (param.description !== null)
                    m += '''
                        @return «param.description»
                    '''.toString
        }
        if (o instanceof NativeType) {
            var mapping = o.platform.findFirst[name == "cpp"]
            if (mapping !== null) {
                if (mapping.location !== null)
                    m += '''
                        @location «mapping.location»
                    '''.toString
                if (mapping.type !== null)
                    m += '''
                        @type «mapping.type»
                    '''.toString
                if (mapping.namespace !== null)
                    m += '''
                        @namespace «mapping.namespace»
                    '''.toString
            }
        }
        '''
            «IF m.empty && !o.description.nullOrEmpty»/** «o.description.replaceAll("\n", "\n * ")» */ «ENDIF»
            «FOR i : m.filterNull BEFORE '/** \n' + (o.description!==null&& !o.description.isEmpty? ' * ' + o.description.replaceAll("\n", "\n * ")+'\n * \n':'\n') AFTER ' */'» * «i»«ENDFOR»
            «FOR n : o.metadata»
                «n.generate»
            «ENDFOR»
        '''
    }

    /** print the visibility of an element if the visibility is different from the default visibility of the container */
    def CharSequence generateVisibility(VisibilityElement o) {

        val parent = o.eContainer

        if (o.isSetVisibility && !(parent instanceof Interface) &&
            !(parent instanceof Structure && !(parent instanceof Class)))
            '''«o.visibility» '''
    }

    def dispatch CharSequence generate(Comment o) {
        '''/* 
            * «o.name»  «o.description» 
            */'''
    }

    def dispatch CharSequence generate(Documentation o) {
        '''/* 
            * «o.name»  «o.description» 
            * resources: «FOR r : o.resource SEPARATOR ', '»«r.href»«ENDFOR»
            */'''
    }

    def dispatch CharSequence generate(Attribute o) {

        if (o.type.reference.eIsProxy)
            '''@«o.type.qfn»(«o.value?.generate(null)»)'''
        else
            '''@«o.type.qfn»«IF !EcoreUtil.equals(o.value,o.type.ref.^default)»(«o.value?.generate(o.type.ref.type.ref)»)«ENDIF»'''
    }

    def dispatch CharSequence generate(Namespace o) {
        '''
            «o.header»
            namespace «o.name»
            {
                «FOR n : o.namespace SEPARATOR '\n'»«n.generate»«ENDFOR»
                «FOR n : o.type SEPARATOR '\n'»«n.generate»«ENDFOR»
            } // namespace «o.name»
        '''
    }

    def dispatch CharSequence generate(PrimitiveType o) {
        '''
            «o.header»
            «o.generateVisibility()»primitive «o.name»
        '''
    }

    def dispatch CharSequence generate(AttributeType o) {
        '''
            «o.header»
            «o.generateVisibility()»attribute «o.type.qfn» «o.name»«IF o.^default !== null» = «o.^default.generate(o.type.ref)»«ENDIF»
        '''
    }

    def dispatch CharSequence generate(NativeType o) {

        '''
            «o.header»
            «o.generateVisibility()»native «o.name»
            
        '''
    }

    def dispatch CharSequence generate(EventType o) {
        '''
            «o.header»
            «o.generateVisibility()»event «o.name»«IF o.eventArgs !== null» extends «o.eventArgs.qfn»«ENDIF»
        '''
    }

    def dispatch CharSequence generate(Array o) {
        '''
            «o.header»
            «o.generateVisibility()»array «o.name» = «o.itemType.qfn»[«o.size»]
        '''
    }

    def dispatch CharSequence generate(ValueReference o) {
        '''
            «o.header»
            «o.generateVisibility()»using «o.name» = «o.type.qfn»*
        '''
    }

    def dispatch CharSequence generate(String o) {
        '''
            «o.header»
            «o.generateVisibility()»string «o.name»[«o.length»]
        '''
    }

    def dispatch CharSequence generate(Integer o) {
        '''
            «o.header»
            «o.generateVisibility()»integer «o.name»«IF o.primitiveType !==null» extends «o.primitiveType.qfn»«ENDIF»«IF o.setMaximum || o.setMinimum » in «IF o.setMinimum»«o.minimum»«ELSE»*«ENDIF» ... «IF o.setMaximum»«o.maximum»«ELSE»*«ENDIF»«ENDIF»
        '''
    }

    def dispatch CharSequence generate(Float o) {
        val range = o.setMaximum || o.setMinimum || o.minInclusive == false || o.maxInclusive == false
        val rangeOp = (o.minInclusive ? "." : "<") + "." + (o.maxInclusive ? "." : "<")

        '''
            «o.header»
            «o.generateVisibility()»float «o.name»«IF o.primitiveType !==null» extends «o.primitiveType.qfn»«ENDIF»«IF range» in «IF o.setMinimum»«o.minimum»«ELSE»*«ENDIF» «rangeOp» «IF o.setMaximum»«o.maximum»«ELSE»*«ENDIF»«ENDIF»
        '''
    }

    def dispatch CharSequence generate(Enumeration o) {
        '''
            «o.header»
            «o.generateVisibility()»enum «o.name»
            {
                «FOR l : o.literal SEPARATOR ', \n'»«l.header»  «l.name» = «l.value»«ENDFOR»
            }
        '''
    }

    def dispatch CharSequence generate(Structure o) {
        '''
            «o.header»
            «o.generateVisibility()»struct «o.name»
            {
                «FOR m : o.eContents SEPARATOR '\n'»«m.generateMember»«ENDFOR»
            }
        '''
    }

    def dispatch CharSequence generate(Class o) {
        '''
            «o.header»
            «o.generateVisibility()»«IF o.abstract»abstract «ENDIF»«o.eClass.name.toLowerCase» «o.name»«IF o.base!==null» extends «o.base.qfn»«ENDIF»
            {
                «FOR m : o.eContents SEPARATOR '\n'»«m.generateMember»«ENDFOR»
            }
        '''
    }

    def dispatch CharSequence generate(Interface o) {
        '''
            «o.header»
            «o.generateVisibility()»interface «o.name»«FOR b : o.base BEFORE ' extends ' SEPARATOR ', '»«b.qfn»«ENDFOR»
            {
                «FOR m : o.eContents SEPARATOR '\n'»«m.generateMember»«ENDFOR»
            }
        '''
    }

    def dispatch CharSequence generate(Component o) {
        '''
            «o.header»
            «o.generateVisibility()»«IF o.name.startsWith("Abstract")»abstract «ENDIF»«o.eClass.name.toLowerCase» «o.name»«IF o.base!==null» extends «o.base.qfn»«ENDIF»«FOR b : o.interface BEFORE ' implements ' SEPARATOR ', '»«b.qfn»«ENDFOR»
            {
                «FOR m : o.eContents SEPARATOR '\n'»«m.generateMember»«ENDFOR»
            }
        '''
    }

    /**
     * skip content which is not a member of struct/class/exception/interface/model/service
     */
    def dispatch CharSequence generateMember(EObject o) {
    }

    def dispatch CharSequence generateMember(Field o) {
        '''
            «o.header»
            «o.generateVisibility()»«IF o.input»input «ENDIF»«IF o.output»output «ENDIF»«IF !o.state»transient «ENDIF»field «o.type.qfn» «o.name»«IF o.^default !==null» = «o.^default.generate(o.type.ref)»«ENDIF»
        '''
    }

    def dispatch CharSequence generateMember(Constant o) {
        '''
            «o.header»
            «o.generateVisibility()»constant «o.type.qfn» «o.name»«IF o.value !==null» = «o.value.generate(o.type.ref)»«ENDIF»
        '''
    }

    def CharSequence generateParameter(Parameter o) {
        '''«FOR n : o.metadata»«n.generate» «ENDFOR» «o.direction()»«o.type.qfn» «o.name» «IF o.^default !==null» = «o.^default.generate(o.type.ref)»«ENDIF»'''
    }

    def CharSequence direction(Parameter o) {
        if (o.isSetDirection)
            '''«o.direction.getName» '''
    }

    def Boolean is(NamedElement f, String name) {

        var a = f.getMetadata().findFirst[(it instanceof Attribute) && name.equals(( it as Attribute).type.qfn)]

        if (a === null)
            return false
        else
            return ((a as Attribute).value as BoolValue).value
    }

    def CharSequence parameters(Operation o) {

        '''«FOR p : o.parameter.filter[it.direction != ParameterDirectionKind.RETURN] SEPARATOR ', '»«p.generateParameter»«ENDFOR»'''
    }

    def CharSequence raises(Operation o) {

        '''«FOR p : o.raisedException BEFORE ' throws ' SEPARATOR ', ' AFTER ' '»«p.qfn»«ENDFOR»'''
    }

    def CharSequence returnParameter(Operation o) {
        var r = o.parameter.findFirst[it.direction == ParameterDirectionKind.RETURN];
        '''
            «IF r ===null»
                void 
            «ELSE»
                «FOR n : r.metadata»
                    «n.generate»
                «ENDFOR»
                «r.type.qfn» «IF !"return".equals(r.name)»«r.name» «ENDIF»
            «ENDIF»
        '''
    }

    def dispatch CharSequence generateMember(Operation o) {
        '''
            «o.header»
            «o.generateVisibility()»def «o.returnParameter»«o.name»(«o.parameters»)«o.raises»
        '''
    }

    def dispatch CharSequence generateMember(Association o) {
        '''
            «o.header»
            «o.generateVisibility()»association «o.type.qfn» «o.name»
        '''
    }

    def multiplicity(long lower, long upper) {
        if (lower === 1 && upper === 1)
            ''''''
        else if (lower === 0 && upper === -1)
            '''[*]'''
        else if (lower === 0 && upper === 1)
            '''?'''
        else if (lower === 1 && upper === -1)
            '''[+]'''
        else if (lower === upper)
            '''[«lower»]'''
        else if (upper === -1)
            '''[«lower» ... *]'''
        else
            '''[«lower» ... «upper»]'''

    }

    def dispatch CharSequence generateMember(Container o) {
        '''
            «o.header»
            container «o.type.qfn»«multiplicity(o.lower,o.upper)» «o.name»«IF o.defaultComponent !== null» = «o.defaultComponent.qfn»«ENDIF»
        '''
    }

    def dispatch CharSequence generateMember(Reference o) {
        '''
            «o.header»
            reference «o.interface.qfn»«multiplicity(o.lower,o.upper)» «o.name»
        '''
    }

    def dispatch CharSequence generateMember(EntryPoint o) {
        '''
            «o.header»
            entrypoint «o.name»
            «IF !o.input.empty || !o.output.empty»
                {
                «FOR in : o.input»
                    in «in.name»
                «ENDFOR»
                «FOR out : o.output»
                    out «out.name»
                «ENDFOR»
                }
            «ENDIF»
        '''
    }

    def dispatch CharSequence generateMember(EventSink o) {
        '''
            «o.header»
            eventsink «o.type.qfn» «o.name»
        '''
    }

    /*
     * *
     **/
    def dispatch CharSequence generateMember(EventSource o) {
        '''
            «o.header»
            eventsource «o.type.qfn» «o.name»
        '''
    }

    def CharSequence genGetRaises(Property o) {

        '''«FOR p : o.getRaises BEFORE ' get throws ' SEPARATOR ', ' AFTER ' '»«p.qfn»«ENDFOR»'''
    }

    def CharSequence genSetRaises(Property o) {

        '''«FOR p : o.setRaises BEFORE ' get throws ' SEPARATOR ', ' AFTER ' '»«p.qfn»«ENDFOR»'''
    }

    def CharSequence genAttachedField(Property o) {

        '''«IF o.attachedField !== null» -> «o.attachedField.ref.name»«ENDIF»'''
    }

    def dispatch CharSequence generateMember(Property o) {
        '''
            «o.header»
            «o.generateVisibility()»«IF o.setAccess»«o.access.literal» «ENDIF»property «o.type.qfn» «o.name»«o.genGetRaises»«o.genSetRaises»«o.genAttachedField»
        '''
    }

    def dispatch CharSequence generate(String8Value o, Type type) {
        
        '''«o.field()»"«xsmpUtil.escape(o.value)»"'''
    }

    def dispatch CharSequence generate(BoolValue o, Type type) {
        '''«o.field()»«o.value»'''
    }

    def dispatch CharSequence generate(Char8Value o, Type type) {
        
        '''«o.field()»«"'"»«xsmpUtil.escape(o.value)»«"'"»'''
    }

    def dispatch CharSequence generate(DateTimeValue o, Type type) {
        '''«o.field()»"«o.value»"'''
    }

    def dispatch CharSequence generate(DurationValue o, Type type) {
        '''«o.field()»"«o.value»"'''
    }

    def dispatch CharSequence generate(EnumerationValue o, Type type) {
        if (type instanceof Enumeration)
            '''«o.field()»«type.literal.findFirst[it.value == o.value].qfn»'''
        else
            '''«o.field()»«o.value»'''
    }

    def dispatch CharSequence generate(Float32Value o, Type type) {
        '''«o.field()»«o.value»F'''
    }

    def dispatch CharSequence generate(Float64Value o, Type type) {
        '''«o.field()»«o.value»'''
    }

    def dispatch CharSequence generate(Int8Value o, Type type) {
        '''«o.field()»«o.value»'''
    }

    def dispatch CharSequence generate(Int16Value o, Type type) {
        '''«o.field()»«o.value»'''
    }

    def dispatch CharSequence generate(Int32Value o, Type type) {
        if (type instanceof Enumeration)
            '''«o.field()»«type.literal.findFirst[it.value == o.value].qfn»'''
        else
            '''«o.field()»«o.value»'''
    }

    def dispatch CharSequence generate(Int64Value o, Type type) {
        '''«o.value»L'''
    }

    def dispatch CharSequence generate(UInt8Value o, Type type) {
        '''«o.field()»«o.value»U'''
    }

    def dispatch CharSequence generate(UInt16Value o, Type type) {
        '''«o.field()»«o.value»U'''
    }

    def dispatch CharSequence generate(UInt32Value o, Type type) {
        '''«o.field()»«o.value»U'''
    }

    def dispatch CharSequence generate(UInt64Value o, Type type) {
        '''«o.field()»«o.value»UL'''
    }

    def dispatch CharSequence generate(ArrayValue o, Type type) {

        if (type instanceof Array)
            '''«o.field()»{«FOR i : o.itemValue SEPARATOR ', '»«i.generate(type.itemType.ref)»«ENDFOR»}'''
        else
            '''«o.field()»{«FOR i : o.itemValue SEPARATOR ', '»«i.generate(null)»«ENDFOR»}'''
    }

    def dispatch CharSequence generate(SimpleArrayValue o, Type type) {
        if (type instanceof Array)
            '''«o.field()»{«FOR i : o.itemValue SEPARATOR ', '»«i.generate(type.itemType.ref)»«ENDFOR»}'''
        else
            '''«o.field()»{«FOR i : o.itemValue SEPARATOR ', '»«i.generate(null)»«ENDFOR»}'''
    }

    // get all public & non-static fields
    def List<Field> getAssignableFields(Structure structure) {

        var fields = structure.field.filter[!it.isStatic && it.realVisibility === VisibilityKind.PUBLIC].toList;
        if (structure instanceof Class) {
            if (structure.base !== null) {
                fields.addAll(0, getAssignableFields(structure.base.ref))

            }
        }
        return fields;

    }
    def field(Value value)
    {
        '''«IF value.field !== null && !value.field.empty && value.eContainer instanceof StructureValue».«value.field» = «ENDIF»'''
    }

    def dispatch CharSequence generate(StructureValue o, Type type) {
        if (type instanceof Structure) {
            var index = 0
            val fields = type.getAssignableFields
            '''«o.field()»{«FOR i : o.fieldValue SEPARATOR ', '»«i.generate(fields.size > index ? fields.get(index++).type.ref : null)»«ENDFOR»}'''
        } else if (type instanceof Array)
            '''«o.field()»{«FOR i : o.fieldValue SEPARATOR ', '»«i.generate(type.itemType.ref)»«ENDFOR»}'''
        else
            '''«o.field()»{«FOR i : o.fieldValue SEPARATOR ', '»«i.generate(null)»«ENDFOR»}'''
    }
}
