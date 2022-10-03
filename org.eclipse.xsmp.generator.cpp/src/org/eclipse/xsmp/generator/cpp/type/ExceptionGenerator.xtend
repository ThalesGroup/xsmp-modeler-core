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

import org.eclipse.xsmp.xcatalogue.Class
import org.eclipse.xsmp.xcatalogue.VisibilityKind
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class ExceptionGenerator extends ClassGenerator {

	override protected generateHeaderGenBody(Class t, boolean useGenPattern) {
		'''
			«t.comment»
			class «t.name(useGenPattern)»: public «IF t.base!==null»::«t.base.fqn.toString("::")»«ELSE»::Smp::Exception«ENDIF» {
			    «t.declareMembersGen(useGenPattern, VisibilityKind.PRIVATE)»
			};
			
			«t.uuidDeclaration»
		'''
	}

	override collectIncludes(Class type, IncludeAcceptor acceptor) {
		super.collectIncludes(type, acceptor)
		if (type.base === null)
			acceptor.mdkHeader("Smp/Exception.h")
	}

}
