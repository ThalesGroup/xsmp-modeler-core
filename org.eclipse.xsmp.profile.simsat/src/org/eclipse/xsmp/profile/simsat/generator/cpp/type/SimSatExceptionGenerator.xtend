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
import org.eclipse.xsmp.generator.cpp.type.ExceptionGenerator
import org.eclipse.xsmp.xcatalogue.Class

class SimSatExceptionGenerator extends ExceptionGenerator {


    protected override CharSequence base(Class t) {
        ''': public «IF t.base!==null»::«t.base.fqn.toString("::")»«ELSE»::esa::ecss::smp::cdk::Exception«ENDIF»'''
    }


    override collectIncludes(Class type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        if (type.base === null)
            acceptor.mdkHeader("esa/ecss/smp/cdk/Exception.h")
    }

}
