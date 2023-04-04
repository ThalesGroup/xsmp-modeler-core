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
package org.eclipse.xsmp.profile.simsat.generator.cpp

import org.eclipse.xsmp.generator.cpp.CatalogueGenerator
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xsmp.xcatalogue.Service
import org.eclipse.xsmp.xcatalogue.Model

class SimSatCatalogueGenerator extends CatalogueGenerator {

    override dispatch CharSequence registerComponent(Model model) {
        '''
            // Register factory for Model «model.name»
            simulator->RegisterFactory( new ::esa::ecss::smp::cdk::Factory<::«model.fqn.toString("::")»>(
                                "«model.name»", // name
                                «model.description()», // description
                                «globalNamespaceName»::simulator, // simulator
                                «model.uuidQfn» // UUID
                                ));
        '''
    }

    override dispatch CharSequence registerComponent(Service service) {
        '''
            // Register Service «service.name»
            simulator->AddService( new ::«service.fqn.toString("::")»(
                                "«service.name»", // name
                                «service.description()», // description
                                «globalNamespaceName»::simulator, // parent
                                «globalNamespaceName»::simulator // parent
                                ));
        '''
    }

    override collectIncludes(Catalogue type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        if (type.requireFactory)
            acceptor.mdkSource("esa/ecss/smp/cdk/Factory.h")
    }

    override CharSequence globalNamespaceName() {
        ''''''
    }
}
