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
import org.eclipse.xsmp.model.xsmp.Class
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.VisibilityKind
import org.eclipse.xsmp.model.xsmp.Operation

class ClassGenerator extends AbstractTypeWithMembersGenerator<Class> {

    override protected generateHeaderBody(Class it) {
        '''
            «comment()»
            class «name» : public «nameGen» 
            {
                friend class «idGen»;
            public:
                «name»()=default;
                «name»(const «name»&)=default;
                «declareMembers(VisibilityKind.PUBLIC)»
            };
        '''
    }

    protected def CharSequence base(Class it) {
        '''«IF base!==null»: public «base.id»«ENDIF»'''
    }

    override protected generateHeaderGenBody(Class it, boolean useGenPattern) {

        val constructor = !noConstructor && !member.filter(Operation).exists[f|f.isConstructor && f.parameter.empty]
        val destructor = !noDestructor
        '''
            «IF useGenPattern»class «name»;«ENDIF»
            «comment»
            class «name(useGenPattern)»«base()»
            {
                «IF useGenPattern»friend class «id»;«ENDIF»
            public:
                static void _Register(::Smp::Publication::ITypeRegistry* registry);
                
                «IF constructor»«name(useGenPattern)»() = default;«ENDIF»
                «IF destructor»~«name(useGenPattern)»() noexcept = default;«ENDIF»
                «name(useGenPattern)»(const «name(useGenPattern)»&) = default;
                
                «declareMembersGen(useGenPattern, VisibilityKind.PUBLIC)»
            };
            
            «uuidDeclaration»
        '''
    }

    override protected generateSourceGenBody(Class it, boolean useGenPattern) {
        val fields = member.filter(Field).filter[f|!f.static]
        '''
            void «name(useGenPattern)»::_Register(::Smp::Publication::ITypeRegistry* registry) 
            {
                «IF !fields.empty»auto *type = «ENDIF»registry->AddClassType(
                    "«name»"  /// Name
                    ,«description()»   /// description
                    ,«uuid()» /// UUID
                    ,«IF base !== null»«base.uuid()»«ELSE»::Smp::Uuids::Uuid_Void«ENDIF» /// Base Class UUID
                    ); 
                    
                «FOR l : fields BEFORE "/// Register the Fields of the Class\n"»
                    type->AddField(
                        "«l.name»",
                        «l.description()»,
                        «l.type.uuid()», /// UUID of the Field Type
                        offsetof(«name», «l.name»), ///Compute the offset of the current item
                        «l.viewKind», /// viewkind
                        «!l.isTransient», /// state
                        «l.isInput», /// input
                        «l.isOutput»/// output
                        );  
                «ENDFOR»
            }
            «defineMembersGen(useGenPattern)»
            
            «uuidDefinition»
        '''
    }

    override protected CharSequence generateSourceBody(Class it, boolean useGenPattern) {
        '''
            «defineMembers(useGenPattern)»
        '''
    }

    override collectIncludes(Class type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        if (type.base !== null)
            acceptor.include(type.base)
    }

}
