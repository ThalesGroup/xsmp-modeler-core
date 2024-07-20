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
import org.eclipse.xsmp.model.xsmp.Class
import org.eclipse.xsmp.model.xsmp.Operation
import org.eclipse.xsmp.model.xsmp.VisibilityKind

class ExceptionGenerator extends ClassGenerator {


    protected override CharSequence base(Class it) {
        ''': public «IF base!==null»«base.id»«ELSE»::Smp::Exception«ENDIF»'''
    }


    override collectIncludes(Class type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        if (type.base === null)
            acceptor.userHeader("Smp/Exception.h")
    }

    override protected generateHeaderGenBody(Class it, boolean useGenPattern) {

        val constructor = !noConstructor && !member.filter(Operation).exists[o|o.constructor && o.parameter.empty]
        val destructor = !noDestructor
        '''
            «IF useGenPattern»class «name»;«ENDIF»
            «comment»
            class «name(useGenPattern)»«base()»
            {
                «IF useGenPattern»friend class «id»;«ENDIF»
            public:
                static void _Register(::Smp::Publication::ITypeRegistry* registry);
                
                «IF constructor»«name(useGenPattern)»() = delete;«ENDIF»
                «IF destructor»~«name(useGenPattern)»() noexcept override = default;«ENDIF»
                «name(useGenPattern)»(const «name(useGenPattern)»&) = default;
                
                «declareMembersGen(useGenPattern, VisibilityKind.PUBLIC)»
            };
            
            «uuidDeclaration»
        '''
    }
}
