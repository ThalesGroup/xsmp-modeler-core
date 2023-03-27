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

import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.VisibilityKind
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

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

	override protected generateSourceBody(Structure type) {
	}

	override protected generateHeaderGenBody(Structure t, boolean useGenPattern) {
		val hasConstructor = t.member.filter(Field).exists[it.^default !== null]
		'''
			«t.comment»
			struct «t.name(useGenPattern)» 
			{
			    «t.declareMembersGen(useGenPattern,  VisibilityKind.PUBLIC)»
			    
			    «IF hasConstructor»
			    	«t.name(useGenPattern)»() = default;
			    	~«t.name(useGenPattern)»() = default;
			    	«t.name(useGenPattern)»(const «t.name(useGenPattern)» &) = default;
			    	«t.name(useGenPattern)»(«t.name(useGenPattern)» &&) = default;
			    	«t.name(useGenPattern)»(«FOR f : t.member.filter(Field) SEPARATOR ", "»::«f.type.fqn.toString("::")» «f.name»«ENDFOR»):
			    	«FOR f : t.member.filter(Field) SEPARATOR ", "»«f.name»(«f.name»)«ENDFOR» {}
			    	«t.name(useGenPattern)»& operator=(const «t.name(useGenPattern)» &) = default;
			    	
			    «ENDIF»
			    static void _Register(::Smp::Publication::ITypeRegistry* registry);
			};
			
			«t.uuidDeclaration»
		'''
	}

	override protected generateSourceGenBody(Structure t, boolean useGenPattern) {
		'''
			void «t.name(useGenPattern)»::_Register(::Smp::Publication::ITypeRegistry* registry) 
			{
			     «IF !t.member.filter(Field).empty»::Smp::Publication::IStructureType* pStructure = «ENDIF»registry->AddStructureType(
			        "«t.name»"  /// Name
			        ,«t.description()»   /// description
			        ,«t.uuidQfn» /// UUID
			        ); 
			        
			    
			    «FOR l : t.member.filter(Field) BEFORE "/// Register the Fields of the Structure\n"»
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
		'''
	}

	override protected collectIncludes(IncludeAcceptor acceptor) {
		super.collectIncludes(acceptor)
		acceptor.systemSource("cstddef")
		acceptor.mdkSource("Smp/Publication/IStructureType.h")
	}
}
