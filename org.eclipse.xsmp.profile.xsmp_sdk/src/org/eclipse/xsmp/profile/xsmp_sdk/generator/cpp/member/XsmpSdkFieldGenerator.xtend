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
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member

import com.google.inject.Inject
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.member.FieldGenerator
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.FieldHelper
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class XsmpSdkFieldGenerator extends FieldGenerator {

    @Inject extension FieldHelper

    override collectIncludes(Field it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)

        if (isCdkField && eContainer instanceof Component)
            acceptor.userHeader("Xsmp/Field.h")
    }

    override Publish(Field it) {
        if (isCdkField)
            '''
                // Publish field «name»
                receiver->PublishField(&«name»);
            '''
        else
            super.Publish(it)
    }

    override initialize(NamedElementWithMembers container, Field it, boolean useGenPattern) {
        if (isCdkField)
            '''
                // «name» initialization
                «name»{simulator->GetTypeRegistry(), «type.uuid()», "«name»", «description()», this, «viewKind»«IF ^default !== null», «^default.generateExpression()»«ENDIF»}
            '''
        else
            super.initialize(container, it, useGenPattern)
    }

}
