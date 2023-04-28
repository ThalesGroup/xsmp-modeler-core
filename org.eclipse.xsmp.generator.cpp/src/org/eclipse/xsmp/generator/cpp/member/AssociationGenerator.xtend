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
package org.eclipse.xsmp.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.Association
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class AssociationGenerator extends AbstractMemberGenerator<Association> {

    /** declare an association in the generated header */
    override declareGen(NamedElementWithMembers parent, Association it, boolean useGenPattern) {

        '''    
            «comment»
            «IF isConst»const «ENDIF»«IF isStatic»static «ENDIF»«IF isMutable»mutable «ENDIF»«type.id»«IF isByPointer»*«ENDIF» «name»;
        '''
    }

    override collectIncludes(Association it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        acceptor.include(type)
    }

    /** initialize an association */
    override initialize(NamedElementWithMembers parent, Association it, boolean useGenPattern) {
        if (!isStatic)
            '''
                // «name» initialization
                «name» «IF ^default !== null»«^default.generateExpression()»«ELSE»{ }«ENDIF»
            '''
    }

}
