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

class ClassGenerator extends MemberGenerator<Class> {


	override protected generateHeaderBody(Class t) {
		'''
			«t.comment()»
			class «t.name» : public «t.genName» 
			{
				«t.declareMembers(VisibilityKind.PRIVATE)»
			};
		'''
	}

	override protected generateSourceBody(Class type) {
	}

	override protected generateHeaderGenBody(Class t, boolean useGenPattern) {
		'''
			«t.comment»
			class «t.name(useGenPattern)»«IF t.base!==null» : public ::«t.base.fqn.toString("::")»«ENDIF» 
			{
			    «t.declareMembersGen(useGenPattern, VisibilityKind.PRIVATE)»
			};
			
			«t.uuidDeclaration»
		'''
	}

	override protected generateSourceGenBody(Class t, boolean useGenPattern) {
		'''
			void «t.name(useGenPattern)»::_Register(::Smp::Publication::ITypeRegistry* registry) 
			{
			     ::Smp::Publication::IClassType* pClass = registry->AddClassType(
			        "«t.name»"  /// Name
			        ,«t.description()»   /// description
			        ,«t.uuidQfn» /// UUID
			        ,«IF t.base !== null»«t.base.uuidQfn»«ELSE»::Smp::Uuids::Uuid_Void«ENDIF» /// Base Class UUID
			        ); 
			        
			    /// Register the Fields of the Class
			    «FOR l : t.member.filter(Field)»
			    	pClass->AddField(
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
		'''
	}

	override collectIncludes(Class type, IncludeAcceptor acceptor) {
		super.collectIncludes(type, acceptor)

		if (type.base !== null)
			acceptor.include(type.base)
	}

}
