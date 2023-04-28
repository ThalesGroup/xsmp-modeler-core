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
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.VisibilityKind

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
                «name(useGenPattern)»();
                ~«name(useGenPattern)»() noexcept;
                «name(useGenPattern)»(const «name(useGenPattern)» &);
                «name(useGenPattern)»(«name(useGenPattern)» &&);
                «name(useGenPattern)»& operator=(const «name(useGenPattern)» &);
                
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
        val hasConstructor = fields.exists[^default !== null]
        '''
            void «name(useGenPattern)»::_Register(::Smp::Publication::ITypeRegistry* registry) 
            {
                 «IF !fields.empty»auto* pStructure = «ENDIF»registry->AddStructureType(
                    "«name»"  /// Name
                    ,«description()»   /// description
                    ,«uuid()» /// UUID
                    ); 
                    
                
                «FOR l : fields BEFORE "/// Register the Fields of the Structure\n"»
                    pStructure->AddField(
                        "«l.name»"
                        ,«l.description()»
                        ,«l.type.uuid()»  ///UUID of the Field Type
                        ,offsetof(«name», «l.name»)  ///Compute the offset of the current item
                        ,«l.viewKind»  ///viewkind
                        ,«!l.isTransient»  ///state
                        ,«l.isInput»  ///is an input field
                        ,«l.isOutput»  ///is an output field
                        );  
                «ENDFOR»
            }
            «IF hasConstructor»
                «name(useGenPattern)»::«name(useGenPattern)»()«FOR f : initializerList(fields, useGenPattern) BEFORE ':' SEPARATOR ", "»«f»«ENDFOR» 
                {
                 «FOR f : member»
                     «construct(f, useGenPattern)»
                 «ENDFOR»
                }
                «name(useGenPattern)»::~«name(useGenPattern)»() noexcept = default;
                «name(useGenPattern)»::«name(useGenPattern)»(const «name(useGenPattern)» &) = default;
                «name(useGenPattern)»::«name(useGenPattern)»(«name(useGenPattern)» &&) = default;
                «name(useGenPattern)»& «name(useGenPattern)»::operator=(const «name(useGenPattern)» &) = default;
            «ENDIF»
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
