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
import org.eclipse.xsmp.xcatalogue.Class
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.VisibilityKind

class ExceptionGenerator extends ClassGenerator {


    protected override CharSequence base(Class t) {
        ''': public «IF t.base!==null»::«t.base.fqn.toString("::")»«ELSE»::Smp::Exception«ENDIF»'''
    }


    override collectIncludes(Class type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        if (type.base === null)
            acceptor.mdkHeader("Smp/Exception.h")
    }

    override protected generateHeaderGenBody(Class t, boolean useGenPattern) {

        val constructor = !t.noConstructor && !t.member.filter(Operation).exists[it.constructor && it.parameter.empty]
        val destructor = !t.noDestructor
        '''
            «t.comment»
            class «t.name(useGenPattern)»«t.base()»
            {
            public:
                static void _Register(::Smp::Publication::ITypeRegistry* registry);
                
                «IF constructor»«t.name(useGenPattern)»() = delete;«ENDIF»
                «IF destructor»~«t.name(useGenPattern)»() noexcept override = default;«ENDIF»
                «t.name(useGenPattern)»(const «t.name(useGenPattern)»&) = default;
                
                «t.declareMembersGen(useGenPattern, VisibilityKind.PUBLIC)»
            };
            
            «t.uuidDeclaration»
        '''
    }
}
