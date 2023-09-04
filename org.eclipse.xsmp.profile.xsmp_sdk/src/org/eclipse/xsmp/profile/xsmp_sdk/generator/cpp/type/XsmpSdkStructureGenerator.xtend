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
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.type.StructureGenerator
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.VisibilityKind

class XsmpSdkStructureGenerator extends StructureGenerator {

    override protected generateHeaderGenBody(Structure it, boolean useGenPattern) {
        val hasConstructor = member.filter(Field).exists[^default !== null]
        '''
            «comment»
            struct «name(useGenPattern)» 
            {
                «declareMembersGen(useGenPattern,  VisibilityKind.PUBLIC)»
                
                «IF hasConstructor»
                    «name(useGenPattern)»(«FOR f : member.filter(Field) SEPARATOR ", "»«f.type.id» «f.name» = «IF f.^default !== null»«f.^default.generateExpression()»«ELSE»{}«ENDIF»«ENDFOR»):
                            «FOR f : member.filter(Field) SEPARATOR ", "»«f.name»(«f.name»)«ENDFOR» {}
                    ~«name(useGenPattern)»() = default;
                    «name(useGenPattern)»(const «name(useGenPattern)» &) = default;
                    «name(useGenPattern)»(«name(useGenPattern)» &&) = default;
                    «name(useGenPattern)»& operator=(const «name(useGenPattern)» &) = default;
                    
                «ENDIF»
                static void _Register(::Smp::Publication::ITypeRegistry* registry);
                
                template<typename _BASE>
                struct _Field : public _BASE
                {
                    // constructor
                    //template<typename ..._Args>
                    _Field ( ::Smp::Publication::ITypeRegistry *typeRegistry, ::Smp::Uuid typeUuid,
                             ::Smp::String8 name, ::Smp::String8 description = "", ::Smp::IObject *parent = nullptr,
                             ::Smp::ViewKind view = ::Smp::ViewKind::VK_None, const «id(useGenPattern)» &value = {}) :
                             _BASE(typeRegistry, typeUuid, name ,description, parent, view)
                    «FOR f : member.filter(Field) BEFORE ',\n' SEPARATOR ', '»
                        /// «f.name» initialization
                        «f.name» {  typeRegistry, «f.type.uuid()», "«f.name»", «f.description()», this, «f.viewKind("view")», value.«f.name» }
                    «ENDFOR»
                    {
                    }
                    _Field(const _Field&) = delete;
                    _Field& operator = (const _Field&) = delete;

                    
                    // copy operator from «id(useGenPattern)»
                    _Field & operator=(const «id(useGenPattern)» &other)
                    {
                       «FOR f : member.filter(Field)»
                           this->«f.name» = other.«f.name»;
                       «ENDFOR»
                       return *this;
                    }
                    
                    // convert to «id(useGenPattern)»
                    operator «id(useGenPattern)»() const noexcept
                    {
                        return {«FOR f : member.filter(Field) SEPARATOR ', '»«f.name»«ENDFOR»};
                    }
                     «FOR f : member.filter(Field) BEFORE "\n// Fields declaration\n"»
                         «f.comment»
                         «IF f.isMutable»mutable «ENDIF»typename _BASE::template Field<«f.type.id»>«IF f.transient»::transient«ENDIF»«IF f.input»::input«ENDIF»«IF f.output»::output«ENDIF»«IF f.forcible»::forcible«ENDIF»«IF f.failure»::failure«ENDIF» «f.name»;
                    «ENDFOR»
                   };
            };
            
            «uuidDeclaration»
        '''
    }

}
