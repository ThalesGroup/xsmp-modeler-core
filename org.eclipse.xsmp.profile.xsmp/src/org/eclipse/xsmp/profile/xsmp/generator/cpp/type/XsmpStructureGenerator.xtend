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
package org.eclipse.xsmp.profile.xsmp.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.VisibilityKind
import org.eclipse.xsmp.generator.cpp.type.StructureGenerator

class XsmpStructureGenerator extends StructureGenerator {

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
			    	«t.name(useGenPattern)»(«FOR f : t.member.filter(Field) SEPARATOR ", "»::«f.type.fqn.toString("::")» _«f.name»«ENDFOR»):
			    	«FOR f : t.member.filter(Field) SEPARATOR ", "»«f.name»(_«f.name»)«ENDFOR» {}
			    	«t.name(useGenPattern)»& operator=(const «t.name(useGenPattern)» &) = default;
			    	
			    «ENDIF»
			    static void _Register(::Smp::Publication::ITypeRegistry* registry);
			    
			    template<typename _BASE>
			    struct _Field : public _BASE
			    {
			     // the _raw_type
			     using _raw_type = ::«t.fqn(useGenPattern).toString("::")»;
				
				    // constructor
				    template<typename ..._Args>
				    _Field (_Args ...args, const _raw_type &default_value = {}) : _BASE(args...)
				    «FOR f : t.member.filter(Field) BEFORE ', ' SEPARATOR ', '»
				    	/// «f.name» initialization
				    	«f.name» { this, «f.type.uuidQfn», "«f.name»", «f.description()», «f.viewKind»,  default_value.«f.name» }
				    «ENDFOR»
				    {
				    }
				    
				    // copy operator
				    _Field & operator=(const _Field  &other)
				    {
				        «FOR f : t.member.filter(Field)»
				        	this->«f.name» = other.«f.name»;
				        «ENDFOR»
				        return *this;
				    }
				    
				    // copy operator from _raw_type
				    _Field & operator=(const _raw_type &other)
				    {
				       «FOR f : t.member.filter(Field)»
				       	this->«f.name» = other.«f.name»;
				       «ENDFOR»
				       return *this;
				    }
				    
				    // convert to _raw_type
				    operator _raw_type() const noexcept
				    {
				        return {«FOR f : t.member.filter(Field) SEPARATOR ', '»«f.name»«ENDFOR»};
				    }
				     «FOR f : t.member.filter(Field)»
				     	«f.comment»
				     	«IF f.isMutable»mutable «ENDIF»typename _BASE::template Field<::«f.type.fqn.toString("::")»>«IF f.transient»::transient«ENDIF»«IF f.input»::input«ENDIF»«IF f.output»::output«ENDIF»«IF f.failure»::failure«ENDIF»«IF f.forcible»::forcible«ENDIF» «f.name»;
				    «ENDFOR»
				   };
			};
			
			«t.uuidDeclaration»
		'''
	}

	override collectIncludes(Structure type, IncludeAcceptor acceptor) {
		super.collectIncludes(type, acceptor)
	}
}
