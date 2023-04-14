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
package org.eclipse.xsmp.profile.simsat.generator

import com.google.inject.Singleton
import org.eclipse.xsmp.generator.cpp.GeneratorExtension
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.xcatalogue.Document
import org.eclipse.xsmp.util.QualifiedNames

@Singleton
class SimSatGeneratorExtension extends GeneratorExtension {

    override CharSequence uuidDeclaration(Type t) {

        ''' 
            /// Universally unique identifier of type «t.name».
            class UuidProvider_«t.name»
            {
            public:
                static constexpr ::Smp::Uuid UUID{«t.uuid()»};
            };
        '''
    }

    override CharSequence uuidDefinition(Type t) {
        '''
            /// Definition of the UuidProvider constexpr for «t.name»
            constexpr Smp::Uuid UuidProvider_«t.name»::UUID;
        '''
    }

    override CharSequence uuidQfn(Type t) {
        val fqn = qualifiedNameProvider.getFullyQualifiedName(t)

        // In Smp, Uuids are in a separate namespace ::Smp::Uuids::
        if (fqn.startsWith(QualifiedNames._Smp))
            '''::Smp::Uuids::Uuid_«t.name»'''
        else
            '''::«(t.eContainer as NamedElement).fqn.toString("::")»::UuidProvider_«t.name»::UUID'''
    }

    override String name(Document elem) {
        elem.name.replace('_', '.')
    }
}
