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

import org.eclipse.xsmp.model.xsmp.Type

abstract class AbstractTypeGenerator<T extends Type> extends org.eclipse.xsmp.generator.cpp.AbstractFileGenerator<T> {

    override protected collectIncludes(org.eclipse.xsmp.generator.cpp.IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        // all types must include the ITypeRegistry
        acceptor.userHeader("Smp/Publication/ITypeRegistry.h")
    }
}
