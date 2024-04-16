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
package org.eclipse.xsmp.profile.tas_mdk.generator.cpp.type

import com.google.inject.Inject
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.type.StructureGenerator
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.FieldHelper
import org.eclipse.xsmp.model.xsmp.VisibilityKind

class TasMdkStructureGenerator extends StructureGenerator {

    @Inject extension FieldHelper

    override protected generateHeaderGenBody(Structure t, boolean useGenPattern) {
        val fields = t.member.filter(Field)
        val hasConstructor = fields.exists[^default !== null]
        '''
            «t.comment()»
            struct «t.name(useGenPattern)» 
            {
                «t.declareMembersGen(useGenPattern,  VisibilityKind.PUBLIC)»
                
                «IF hasConstructor»
                    «t.name(useGenPattern)»() = default;
                    ~«t.name(useGenPattern)»() = default;
                    «t.name(useGenPattern)»(const «t.name(useGenPattern)» &) = default;
                    «t.name(useGenPattern)»(«t.name(useGenPattern)» &&) = default;
                    «t.name(useGenPattern)»(«FOR f : t.member.filter(Field) SEPARATOR ", "»«f.type.id» «f.name»«ENDFOR»):
                    «FOR f : t.member.filter(Field) SEPARATOR ", "»«f.name»(«f.name»)«ENDFOR» {}
                    «t.name(useGenPattern)»& operator=(const «t.name(useGenPattern)» &) = default;
                «ENDIF»
                static void _Register(::Smp::Publication::ITypeRegistry* registry);
            };
            
            «t.uuidDeclaration»
            
            «IF !useGenPattern»
                template<bool ...Opts>
                struct _«t.name(useGenPattern)» : public ::TasMdk::Types::StructureType<Opts...>
                {
                    // the equivalent raw_type
                    typedef «t.name(useGenPattern)» raw_type;
                    
                    // constructor
                    _«t.name(useGenPattern)» (const std::string& name, const std::string& description,
                            Smp::IObject *parent, Smp::ViewKind view,
                            const Smp::Publication::ITypeRegistry *type_registry, Smp::Uuid typeUuid,
                            const raw_type &default_value = raw_type{}) :
                            ::TasMdk::Types::StructureType<Opts...>(name, description, parent, view, type_registry->GetType(typeUuid))«FOR f : t.member.filter(Field) BEFORE ', \n' SEPARATOR ', '»
                                            /// «f.name» initialization
                                            «f.name»{"«f.name»", «f.description()», this->GetFields(), «f.viewKindCpp()», type_registry, «f.type.uuid()», default_value.«f.name»}
                            «ENDFOR»
                    {
                    }
                    
                    // copy operator
                    _«t.name(useGenPattern)» & operator=(const _«t.name(useGenPattern)»  &other)
                    {
                        «FOR f : t.member.filter(Field)»
                            «f.name» = other.«f.name»;
                        «ENDFOR»
                        return *this;
                    }
                    
                    // copy operator from raw_type
                    _«t.name(useGenPattern)» & operator=(const raw_type &other)
                    {
                       «FOR f : t.member.filter(Field)»
                           «f.name» = other.«f.name»;
                       «ENDFOR»
                       return *this;
                    }
                    
                    // convert to raw_type
                    operator raw_type() const noexcept
                    {
                        return {«FOR f : t.member.filter(Field) SEPARATOR ', '»«f.name»«ENDFOR»};
                    }
                     «FOR f : t.member.filter(Field)»
                         «f.generateField»
                     «ENDFOR»
                };
            «ENDIF»
        '''
    }

    def CharSequence generateField(Field f) {
        var CharSequence fieldType

        if (f.type instanceof Structure)
            fieldType = '''StructureField<«f.type.field_fqn»>'''
        else if (f.type instanceof Array)
            fieldType = '''ArrayField<«f.type.field_fqn»>'''
        else
            fieldType = '''SimpleField<«f.type.id»>'''

        '''
            «f.comment»
            «IF f.isMutable»mutable «ENDIF»typename ::TasMdk::«fieldType»«IF f.eContainer instanceof Structure»::in<Opts...>«ENDIF»«IF f.transient»::transient«ENDIF»«IF f.input»::input«ENDIF»«IF f.output»::output«ENDIF»«IF f.failure»::failure«ENDIF»«IF f.forcible»::forcible«ENDIF»«IF f.ofInput»::of_input«ENDIF»«IF f.ofOutput»::of_output«ENDIF»«IF f.ofFailure»::of_failure«ENDIF»::type «f.name»;
        '''
    }

    override collectIncludes(Structure type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        acceptor.userHeader("TasMdk/Types/Structure.h")
    }
    
    override protected declareGen(Structure type, Field c, boolean useGenPattern) {
        '''
            «c.comment»
            «IF c.isStatic»static «ENDIF»«IF c.isMutable»mutable «ENDIF»«c.type.id» «c.name»«IF c.^default !==null» «c.^default.generateExpression()»«ELSE»«ENDIF»;
        '''
    }
    
}
