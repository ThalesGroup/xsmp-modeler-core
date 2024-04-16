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
package org.eclipse.xsmp.profile.tas_mdk.generator.cpp.type

import com.google.inject.Inject
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.type.ArrayGenerator
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.FieldHelper
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.model.xsmp.SimpleType

class TasMdkArrayGenerator extends ArrayGenerator {

	@Inject extension FieldHelper

	override protected generateHeaderBody(Array t) {
		'''
			«super.generateHeaderBody(t)»
			template<bool ...Opts>
			using _«t.name» = _«t.nameGen»<Opts...>;
		'''
	}

	override protected generateHeaderGenBody(Array t, boolean useGenPattern) {
		'''
			«t.comment»
			typedef ::TasMdk::Types::Array<«t.itemType.id», «t.size.doGenerateExpression()»> «t.name(useGenPattern)»;
			
			«t.uuidDeclaration»
			
			void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
			
			template<bool ...Opts>
			«IF useGenPattern»using _«t.name(useGenPattern)» = TasMdk::Types::«IF t.itemType instanceof SimpleType»Simple«ENDIF»ArrayType<«t.itemType.id», «t.size.doGenerateExpression()», Opts...>;
			«ELSE»using _«t.name(useGenPattern)» = TasMdk::Types::«IF t.itemType instanceof SimpleType»SimpleArrayType<«t.itemType.id»«ELSE»ArrayType<«t.itemType.field_fqn»«ENDIF», «t.size.doGenerateExpression()», Opts...>;«ENDIF»
		'''
	}

	override collectIncludes(Array type, IncludeAcceptor acceptor) {
		super.collectIncludes(type, acceptor)

		acceptor.userHeader("TasMdk/Types/Array.h")
	}
}
