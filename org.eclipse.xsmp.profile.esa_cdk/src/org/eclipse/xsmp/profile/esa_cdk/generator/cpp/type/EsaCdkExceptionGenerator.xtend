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
package org.eclipse.xsmp.profile.esa_cdk.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.type.ExceptionGenerator
import org.eclipse.xsmp.xcatalogue.Class

class EsaCdkExceptionGenerator extends ExceptionGenerator {

    protected override CharSequence base(Class it) {
        ''': public «IF base!==null»«base.id»«ELSE»::esa::ecss::smp::cdk::Exception«ENDIF»'''
    }

    override collectIncludes(Class it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        if (base === null)
            acceptor.userHeader("esa/ecss/smp/cdk/Exception.h")
    }

}
