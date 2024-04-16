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
package org.eclipse.xsmp.profile.tas_mdk.generator.cpp.member

import com.google.inject.Inject
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.member.FieldGenerator
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.FieldHelper
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers

class TasMdkFieldGenerator extends FieldGenerator {

    @Inject extension FieldHelper

    override collectIncludes(Field element, IncludeAcceptor acceptor) {
        super.collectIncludes(element, acceptor)

        if (element.isTasMdkField)
            acceptor.userHeader("TasMdk/Field.h")
    }

    override Publish(Field element) {
        if (element.isTasMdkField)
            '''
                // Publish field «element.name»
                receiver->PublishField((Smp::IField*) &«element.name»);
            '''
        else
            super.Publish(element)
    }

    override initialize(NamedElementWithMembers container, Field f, boolean useGenPattern) {
        if (f.isTasMdkField)
            '''
                // «f.name» initialization
                «f.name»{"«f.name»", «f.description()», this, «f.viewKindCpp()», _type_registry, «f.type.uuid()»«IF f.^default !== null», «f.^default.generateExpression()»«ENDIF»}
            '''
        else
            super.initialize(container, f, useGenPattern)
    }

    override protected isDirectListInitialization(Field it) {
        !isStatic()
    }

}
