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
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

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
                 // the _raw_type
                 using _raw_type = «id(useGenPattern)»;
                
                    // constructor
                    template<typename ..._Args>
                    _Field (_Args ...args, const _raw_type &default_value = {}) : _BASE(args...)
                    «FOR f : member.filter(Field) BEFORE ', ' SEPARATOR ', '»
                        /// «f.name» initialization
                        «f.name» { this, «f.type.uuid()», "«f.name»", «f.description()», «f.viewKind»,  default_value.«f.name» }
                    «ENDFOR»
                    {
                    }
                    
                    // copy operator
                    _Field & operator=(const _Field  &other)
                    {
                        «FOR f : member.filter(Field)»
                            this->«f.name» = other.«f.name»;
                        «ENDFOR»
                        return *this;
                    }
                    
                    // copy operator from _raw_type
                    _Field & operator=(const _raw_type &other)
                    {
                       «FOR f : member.filter(Field)»
                           this->«f.name» = other.«f.name»;
                       «ENDFOR»
                       return *this;
                    }
                    
                    // convert to _raw_type
                    operator _raw_type() const noexcept
                    {
                        return {«FOR f : member.filter(Field) SEPARATOR ', '»«f.name»«ENDFOR»};
                    }
                     «FOR f : member.filter(Field)»
                         «f.comment»
                         «IF f.isMutable»mutable «ENDIF»typename _BASE::template Field<«f.type.id»>«IF f.transient»::transient«ENDIF»«IF f.input»::input«ENDIF»«IF f.output»::output«ENDIF»«IF f.failure»::failure«ENDIF»«IF f.forcible»::forcible«ENDIF» «f.name»;
                    «ENDFOR»
                   };
            };
            
            «uuidDeclaration»
        '''
    }

    override collectIncludes(Structure it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
    }
}
