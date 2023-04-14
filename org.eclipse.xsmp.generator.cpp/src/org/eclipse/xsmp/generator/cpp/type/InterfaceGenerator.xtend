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
import org.eclipse.xsmp.xcatalogue.Interface
import org.eclipse.xsmp.xcatalogue.VisibilityKind

class InterfaceGenerator extends MemberGenerator<Interface> {

    override protected generateHeaderBody(Interface t) {
        '''
            «t.comment()»
            class «t.name»: public «t.genName» {
                public:
                    ~«t.name»() override = default;
                    «t.declareMembers(VisibilityKind.PUBLIC)»
            };
        '''
    }

    override protected generateHeaderGenBody(Interface t, boolean useGenPattern) {
        '''
            «t.comment»
            class «t.name(useGenPattern)»«FOR b : t.base BEFORE ' : ' SEPARATOR ', '»public virtual ::«b.fqn.toString("::")»«ENDFOR» {
                public:
                virtual ~«t.name(useGenPattern)»() = default;
                «t.declareMembersGen(useGenPattern, VisibilityKind.PUBLIC)»
            };
            
            «t.uuidDeclaration»
        '''
    }

    override protected generateSourceGenBody(Interface t, boolean useGenPattern) {
        '''
            «t.uuidDefinition»
            «t.defineMembersGen(useGenPattern)»
        '''
    }

    override collectIncludes(Interface type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        for (base : type.base)
            acceptor.include(base)
    }
}
