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
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.xcatalogue.Document
import org.eclipse.xsmp.util.QualifiedNames
import org.eclipse.xsmp.generator.cpp.GeneratorUtil

@Singleton
class SimSatGeneratorExtension extends GeneratorUtil {

    override CharSequence uuidDeclaration(Type it) {
        ''' 
            /// Universally unique identifier of type «name».
            class UuidProvider_«name»
            {
            public:
                static constexpr ::Smp::Uuid UUID «generateUUID()»;
            };
        '''
    }

    override CharSequence uuidDefinition(Type it) {
        '''
            /// Definition of the UuidProvider constexpr for «name»
            constexpr Smp::Uuid UuidProvider_«name»::UUID;
        '''
    }

    override CharSequence uuid(Type it) {
        val fqn = fqn

        // In Smp, Uuids are in a separate namespace ::Smp::Uuids::
        if (fqn.startsWith(QualifiedNames.Smp))
            '''::Smp::Uuids::Uuid_«name»'''
        else
            '''«(eContainer as NamedElement).id»::UuidProvider_«name»::UUID'''
    }

    override String name(Document elem) {
        elem.name.replace('_', '.')
    }
}
