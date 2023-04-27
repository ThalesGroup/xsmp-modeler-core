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
package org.eclipse.xsmp.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.Class
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.VisibilityKind
import org.eclipse.xsmp.xcatalogue.Operation

class ClassGenerator extends AbstractTypeWithMembersGenerator<Class> {

    override protected generateHeaderBody(Class t) {
        '''
            «t.comment()»
            class «t.name» : public «t.genName» 
            {
                friend class ::«t.fqn(true).toString("::")»;
                «t.declareMembers(VisibilityKind.PRIVATE)»
            };
        '''
    }

    protected def CharSequence base(Class t) {
        '''«IF t.base!==null»: public ::«t.base.fqn.toString("::")»«ENDIF»'''
    }

    override protected generateHeaderGenBody(Class t, boolean useGenPattern) {

        val constructor = !t.noConstructor && !t.member.filter(Operation).exists[it.constructor && it.parameter.empty]
        val destructor = !t.noDestructor
        '''
            «IF useGenPattern»class «t.name»;«ENDIF»
            «t.comment»
            class «t.name(useGenPattern)»«t.base()»
            {
                «IF useGenPattern»friend class ::«t.fqn.toString("::")»;«ENDIF»
            public:
                static void _Register(::Smp::Publication::ITypeRegistry* registry);
                
                «IF constructor»«t.name(useGenPattern)»() = default;«ENDIF»
                «IF destructor»~«t.name(useGenPattern)»() noexcept = default;«ENDIF»
                «t.name(useGenPattern)»(const «t.name(useGenPattern)»&) = default;
                
                «t.declareMembersGen(useGenPattern, VisibilityKind.PUBLIC)»
            };
            
            «t.uuidDeclaration»
        '''
    }

    override protected generateSourceGenBody(Class t, boolean useGenPattern) {
        val fields = t.member.filter(Field).filter[!it.static]
        '''
            void «t.name(useGenPattern)»::_Register(::Smp::Publication::ITypeRegistry* registry) 
            {
                «IF useGenPattern»«t.generateStaticAsserts()»«ENDIF»
                «IF !fields.empty»auto* type = «ENDIF»registry->AddClassType(
                    "«t.name»"  /// Name
                    ,«t.description()»   /// description
                    ,«t.uuidQfn» /// UUID
                    ,«IF t.base !== null»«t.base.uuidQfn»«ELSE»::Smp::Uuids::Uuid_Void«ENDIF» /// Base Class UUID
                    ); 
                    
                «FOR l : fields BEFORE "/// Register the Fields of the Class\n"»
                    type->AddField(
                        "«l.name»"
                        ,«l.description()»
                        ,«l.type.uuidQfn»  ///UUID of the Field Type
                        ,offsetof(«t.name», «l.name»)  ///Compute the offset of the current item
                        ,«l.viewKind»  ///viewkind
                        ,«!l.isTransient»  ///state
                        ,«l.isInput»  ///is an input field
                        ,«l.isOutput»///is an output field
                        );  
                «ENDFOR»
            }
            «t.defineMembersGen(useGenPattern)»
            
            «t.uuidDefinition»
        '''
    }

    override protected CharSequence generateSourceBody(Class t, boolean useGenPattern) {
        '''
            «t.defineMembers(useGenPattern)»
        '''
    }

    override collectIncludes(Class type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        if (type.base !== null)
            acceptor.include(type.base)
    }

}
