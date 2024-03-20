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
import org.eclipse.xsmp.model.xsmp.Constant
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers

import org.eclipse.xsmp.model.xsmp.String

class ConstantGenerator extends AbstractMemberGenerator<Constant> {

    /** return true if the CDK String type has a constexpr constructor (true for aggregate types too) */
    protected def boolean stringTypeIsConstexpr() {
        true
    }

    /** declare a constant in the generated header*/
    override declareGen(NamedElementWithMembers parent, Constant it, boolean useGenPattern) {

        if (!stringTypeIsConstexpr && type instanceof String)
            '''
                «comment»
                static const «type.id» «name»;
            '''
        else
            '''
                «comment»
                static constexpr «type.id» «name»«IF value !==null»«value.generateExpression()»«ELSE»{}«ENDIF»;
            '''
    }

    /** define the symbol of the constant in the generated source */
    override defineGen(NamedElementWithMembers parent, Constant it, boolean useGenPattern) {

        if (!stringTypeIsConstexpr && type instanceof String)
            '''
                const «type.id» «parent.name(useGenPattern)»::«name»«IF value !==null»«value.generateExpression()»«ELSE»{}«ENDIF»;
            '''
        else
            '''
                constexpr «type.id» «parent.name(useGenPattern)»::«name»;
            '''
    }

    override collectIncludes(Constant it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        acceptor.include(type)
        value?.include(acceptor)
    }

}
