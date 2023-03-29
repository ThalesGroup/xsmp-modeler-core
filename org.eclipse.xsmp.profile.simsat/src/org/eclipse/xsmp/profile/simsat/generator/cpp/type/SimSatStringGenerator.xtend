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
package org.eclipse.xsmp.profile.simsat.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.String
import org.eclipse.xsmp.generator.cpp.type.StringGenerator

class SimSatStringGenerator extends StringGenerator {


	override protected generateHeaderGenBody(String t, boolean useGenPattern) {
		'''
			«t.comment»
			using «t.name(useGenPattern)» = ::esa::ecss::smp::cdk::String<«t.length.doGenerateExpression(t,t)»>;
			
			«t.uuidDeclaration»
			
			void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
		'''
	}

	override collectIncludes(String type, IncludeAcceptor acceptor) {
		super.collectIncludes(type, acceptor)

		acceptor.mdkHeader("esa/ecss/smp/cdk/String.h")
	}
}
