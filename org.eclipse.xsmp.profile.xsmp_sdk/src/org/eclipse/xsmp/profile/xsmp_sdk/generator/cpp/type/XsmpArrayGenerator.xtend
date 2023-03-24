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

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.generator.cpp.type.ArrayGenerator

class XsmpArrayGenerator extends ArrayGenerator {

	override protected generateHeaderGenBody(Array t, boolean useGenPattern) {
		/*
		 * 	struct «t.name(useGenPattern)»: ::Xsmp::Types::Array<::«t.itemType.fqn.toString("::")», «t.size.doGenerateExpression(t,t)»«IF t.isSimpleArray», true«ENDIF»>
		 * 	{
		 * 		using Field =ArrayField<«t.name(useGenPattern)»>;
		 * 	};
		 * 	
		 */
		'''
			«t.comment»
			using «t.name(useGenPattern)»= ::Xsmp::Types::Array<::«t.itemType.fqn.toString("::")», «t.size.doGenerateExpression(t,t)»«IF t.isSimpleArray», true«ENDIF»>;
			
			«t.uuidDeclaration»
			
			void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
		'''
	}

	override collectIncludes(Array type, IncludeAcceptor acceptor) {
		super.collectIncludes(type, acceptor)

		acceptor.mdkHeader("Xsmp/Types/Array.h")
	}
}
