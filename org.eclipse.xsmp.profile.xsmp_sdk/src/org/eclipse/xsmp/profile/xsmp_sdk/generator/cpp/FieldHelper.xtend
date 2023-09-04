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
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp

import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.util.XsmpUtil

class FieldHelper {

    @Inject
    extension XsmpUtil

    /**
     * Cdk field are output or failure or forcible
     */
    def boolean isCdkField(Field f) {
        return f.output || f.isFailure || f.isForcible || f.type.isCdkFieldType
    }

    private def dispatch boolean isCdkFieldType(Type t) {
        return false
    }

    private def dispatch boolean isCdkFieldType(Structure t) {
        return t.member.filter(Field).exists[it.isCdkField]
    }

    private def dispatch boolean isCdkFieldType(Array t) {
        return t.itemType.isCdkFieldType
    }

    def boolean isFailureField(Field f) {
        return f.isFailure || f.type.isFailureFieldType
    }

    private def dispatch boolean isFailureFieldType(Type t) {
        return false
    }

    private def dispatch boolean isFailureFieldType(Structure t) {
        return t.member.filter(Field).exists[it.isFailureField]
    }

    private def dispatch boolean isFailureFieldType(Array t) {
        return t.itemType.isFailureFieldType
    }

}
