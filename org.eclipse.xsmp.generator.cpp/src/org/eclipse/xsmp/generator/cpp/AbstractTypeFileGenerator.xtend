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
package org.eclipse.xsmp.generator.cpp

import org.eclipse.xsmp.xcatalogue.Type

abstract class AbstractTypeFileGenerator<T extends Type> extends AbstractFileGenerator<T> {

	override protected collectIncludes(IncludeAcceptor acceptor) {
		super.collectIncludes(acceptor)
		// all types must include the ITypeRegistry
		acceptor.mdkHeader("Smp/Publication/ITypeRegistry.h")
	}

}
