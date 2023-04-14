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
package org.eclipse.xsmp.profile.simsat.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.member.ConstantGenerator
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.String

class SimSatConstantGenerator extends ConstantGenerator {

    override declareGen(NamedElementWithMembers type, Constant element, boolean useGenPattern) {

        if (element.type instanceof String)
            '''
                «element.comment»
                static const ::«element.type.fqn.toString("::")» «element.name»;
            '''
        else
            super.declareGen(type, element, useGenPattern)

    }

    override defineGen(NamedElementWithMembers type, Constant element, boolean useGenPattern) {
        if (element.type instanceof String)
            '''
                const ::«element.type.fqn.toString("::")» «type.name(useGenPattern)»::«element.name»«element.value.generateExpression(element.type, type)»;
            '''
        else
            super.defineGen(type, element, useGenPattern)
    }

}
