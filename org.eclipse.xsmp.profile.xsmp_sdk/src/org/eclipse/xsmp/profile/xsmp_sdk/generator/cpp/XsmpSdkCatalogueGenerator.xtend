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

import org.eclipse.xsmp.generator.cpp.CatalogueGenerator
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xsmp.xcatalogue.Model
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkCatalogueGenerator extends CatalogueGenerator {

    override dispatch CharSequence registerComponent(Model it) {
        '''
            // Register factory for Model «name»
            simulator->RegisterFactory(::Xsmp::Factory::Create<«id»>(
                                "«name»", // name
                                «description()», // description
                                ::simulator, // simulator
                                «uuid()», // UUID
                                "«id»"// type name
                                ));
        '''
    }

    override collectIncludes(Catalogue it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)

        if (requireFactory)
            acceptor.userSource("Xsmp/Factory.h")
    }

}
