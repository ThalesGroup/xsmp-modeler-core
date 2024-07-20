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

import java.util.List
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.model.xsmp.VisibilityKind

class StructureGenerator extends AbstractTypeWithMembersGenerator<Structure> {

    override protected generateHeaderBody(Structure it) {
        '''
            «comment()»
            struct «name» : public «nameGen» 
            {
                «declareMembers(VisibilityKind.PUBLIC)»
            };
        '''
    }

    override protected generateHeaderGenBody(Structure it, boolean useGenPattern) {
        val fields = member.filter(Field).filter[!isStatic]
        val hasConstructor = fields.exists[^default !== null]
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
            };
            
            «uuidDeclaration»
        '''
    }

    protected def Iterable<CharSequence> initializerList(Structure it, List<Field> fields, boolean useGenPattern) {
        var List<CharSequence> list = newArrayList
        for (c : fields)
            list += initialize(c, useGenPattern)
        return list.filterNull
    }

    override protected generateSourceGenBody(Structure it, boolean useGenPattern) {
        val fields = assignableFields
        '''
            void «name(useGenPattern)»::_Register(::Smp::Publication::ITypeRegistry* registry) 
            {
                 «IF !fields.empty»auto *type = «ENDIF»registry->AddStructureType(
                    "«name»",  /// Name
                    «description()», /// description
                    «uuid()» /// UUID
                    ); 
                    
                
                «FOR l : fields BEFORE "/// Register the Fields of the Structure\n"»
                    type->AddField(
                        "«l.name»",
                        «l.description()»,
                        «l.type.uuid()», ///UUID of the Field Type
                        offsetof(«name», «l.name»), ///Compute the offset of the current item
                        «l.viewKindCpp», ///viewkind
                        «!l.isTransient», ///state
                        «l.isInput», ///is an input field
                        «l.isOutput» ///is an output field
                        );  
                «ENDFOR»
            }
            «defineMembersGen(useGenPattern)»
            
            «uuidDefinition»
        '''
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.systemSource("cstddef")
        acceptor.userSource("Smp/Publication/IStructureType.h")
    }
}
