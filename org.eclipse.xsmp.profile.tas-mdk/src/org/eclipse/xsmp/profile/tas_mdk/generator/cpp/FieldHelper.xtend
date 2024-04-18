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
package org.eclipse.xsmp.profile.tas_mdk.generator.cpp

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.model.xsmp.Type
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.model.xsmp.NamedElement
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.EcoreUtil2

class FieldHelper {

    @Inject
    extension XsmpUtil

    /**
     * Cdk field are output or failure or forcible
     */
    def CharSequence field_fqn(Type t) {
        '''::«EcoreUtil2.getContainerOfType(t.eContainer, NamedElement)?.fqn.toString("::")»::_«t.name»'''
    }

    def boolean isTasMdkField(Field f) {
        return !f.isStatic &&
            (f.output || f.input || f.isFailure || f.isForcible || f.ofOutput || f.ofInput || f.ofFailure ||
                f.type.isTasMdkFieldType)
    }

    private def dispatch boolean isTasMdkFieldType(Type t) {
        return false
    }

    private def dispatch boolean isTasMdkFieldType(Structure t) {
        return t.member.filter(Field).exists[it.isTasMdkField]
    }

    private def dispatch boolean isTasMdkFieldType(Array t) {
        return t.itemType.isTasMdkFieldType
    }

    def Boolean isOfInput(NamedElement o) {
        o.attributeBoolValue(QualifiedName.create("TasMdk", "OfInput")).orElse(false)
    }

    def Boolean isOfOutput(NamedElement o) {
        o.attributeBoolValue(QualifiedName.create("TasMdk", "OfOutput")).orElse(false)
    }

    def Boolean isOfFailure(NamedElement o) {
        o.attributeBoolValue(QualifiedName.create("TasMdk", "OfFailure")).orElse(false)
    }

}
