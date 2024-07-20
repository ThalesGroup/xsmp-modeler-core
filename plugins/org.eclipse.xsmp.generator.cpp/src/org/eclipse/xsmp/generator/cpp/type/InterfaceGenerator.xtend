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
import org.eclipse.xsmp.model.xsmp.Interface
import org.eclipse.xsmp.model.xsmp.VisibilityKind

class InterfaceGenerator extends AbstractTypeWithMembersGenerator<Interface> {

    override protected generateHeaderBody(Interface it) {
        '''
            «comment()»
            class «name»: public «nameGen» {
                public:
                    ~«name»() override = default;
                    «declareMembers(VisibilityKind.PUBLIC)»
            };
        '''
    }

    override protected generateHeaderGenBody(Interface it, boolean useGenPattern) {
        '''
            «comment»
            class «name(useGenPattern)»«FOR b : base BEFORE ' : ' SEPARATOR ', '»public virtual «b.id»«ENDFOR» {
                public:
                virtual ~«name(useGenPattern)»() = default;
                «declareMembersGen(useGenPattern, VisibilityKind.PUBLIC)»
            };
            
            «uuidDeclaration»
        '''
    }

    override protected generateSourceGenBody(Interface it, boolean useGenPattern) {
        '''
            «uuidDefinition»
            «defineMembersGen(useGenPattern)»
        '''
    }

    override collectIncludes(Interface it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)

        for (b : base)
            acceptor.include(b)
    }
}
