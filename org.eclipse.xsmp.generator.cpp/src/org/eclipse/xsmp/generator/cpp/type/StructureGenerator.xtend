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

class StructureGenerator extends MemberGenerator<Structure> {

    override protected generateHeaderBody(Structure t) {
        '''
            «t.comment()»
            struct «t.name» : public «t.genName» 
            {
                «t.declareMembers(VisibilityKind.PUBLIC)»
            };
        '''
    }

    override protected generateHeaderGenBody(Structure t, boolean useGenPattern) {
        val fields = t.member.filter(Field).filter[!it.static]
        val hasConstructor = fields.exists[it.^default !== null]
        '''
            «t.comment»
            struct «t.name(useGenPattern)» 
            {
                «t.declareMembersGen(useGenPattern,  VisibilityKind.PUBLIC)»
                
                «IF hasConstructor»
                «t.name(useGenPattern)»(/*«FOR f : fields SEPARATOR ", "»::«f.type.fqn.toString("::")» «f.name» = «IF f.^default !==null» «f.^default.generateExpression(f.type, t)»«ELSE»{}«ENDIF»«ENDFOR»*/);
                ~«t.name(useGenPattern)»() noexcept;
                «t.name(useGenPattern)»(const «t.name(useGenPattern)» &);
                «t.name(useGenPattern)»(«t.name(useGenPattern)» &&);
                «t.name(useGenPattern)»& operator=(const «t.name(useGenPattern)» &);
                
                «ENDIF»
                static void _Register(::Smp::Publication::ITypeRegistry* registry);
            };
            
            «t.uuidDeclaration»
        '''
    }

    protected def Iterable<CharSequence> initializerList(Structure t, List<Field> fields, boolean useGenPattern) {
        var List<CharSequence> list = newArrayList
        for (c : fields)
            list += t.initialize(c, useGenPattern)
        return list.filterNull
    }

    override protected generateSourceGenBody(Structure t, boolean useGenPattern) {
        val fields = t.assignableFields
        val hasConstructor = fields.exists[it.^default !== null]
        '''
            void «t.name(useGenPattern)»::_Register(::Smp::Publication::ITypeRegistry* registry) 
            {
                 «IF !fields.empty»auto* pStructure = «ENDIF»registry->AddStructureType(
                    "«t.name»"  /// Name
                    ,«t.description()»   /// description
                    ,«t.uuidQfn» /// UUID
                    ); 
                    
                
                «FOR l : fields BEFORE "/// Register the Fields of the Structure\n"»
                    pStructure->AddField(
                        "«l.name»"
                        ,«l.description()»
                        ,«l.type.uuidQfn»  ///UUID of the Field Type
                        ,offsetof(«t.name», «l.name»)  ///Compute the offset of the current item
                        ,«l.viewKind»  ///viewkind
                        ,«!l.isTransient»  ///state
                        ,«l.isInput»  ///is an input field
                        ,«l.isOutput»  ///is an output field
                        );  
                «ENDFOR»
            }
            «IF hasConstructor»
                «t.name(useGenPattern)»::«t.name(useGenPattern)»(/*«FOR f : fields SEPARATOR ", "»::«f.type.fqn.toString("::")» «f.name»«ENDFOR»*/)
                                        «FOR f : t.initializerList(fields, useGenPattern) BEFORE ':' SEPARATOR ", "»«f»«ENDFOR» 
                {
                 «FOR f : t.member»
                     «t.construct(f, useGenPattern)»
                 «ENDFOR»
                }
                «t.name(useGenPattern)»::~«t.name(useGenPattern)»() noexcept = default;
                «t.name(useGenPattern)»::«t.name(useGenPattern)»(const «t.name(useGenPattern)» &) = default;
                «t.name(useGenPattern)»::«t.name(useGenPattern)»(«t.name(useGenPattern)» &&) = default;
                «t.name(useGenPattern)»& «t.name(useGenPattern)»::operator=(const «t.name(useGenPattern)» &) = default;
            «ENDIF»
            «t.defineMembersGen(useGenPattern)»
            
            «t.uuidDefinition»
        '''
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.systemSource("cstddef")
        acceptor.mdkSource("Smp/Publication/IStructureType.h")
    }
}
