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

import org.eclipse.xsmp.generator.cpp.CatalogueGenerator
import org.eclipse.xsmp.model.xsmp.Catalogue
import org.eclipse.xsmp.model.xsmp.Model
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.model.xsmp.Service
import org.eclipse.xsmp.generator.cpp.GeneratorUtil
import com.google.inject.Inject

class TasMdkCatalogueGenerator extends CatalogueGenerator {
    
    @Inject extension GeneratorUtil

    protected override dispatch CharSequence registerComponent(Model model) {
        '''
            // Register factory for Model «model.name»
            simulator->RegisterFactory(new ::TasMdk::Factory(
                                "«model.name»", // name
                                «model.description()», // description
                                simulator, // parent
                                «model.uuid()», // UUID
                                "«model.id»",// type name
                                [](::Smp::String8 name,
                                   ::Smp::String8 description,
                                   ::Smp::IObject* parent,
                                   ::Smp::Publication::ITypeRegistry* type_registry) {
                                        return new «model.id»(name, description, parent, type_registry);
                                   }, // instantiation callback
                                typeRegistry // type registry
                                ));
        '''

    }

    protected override dispatch CharSequence registerComponent(Service service) {
        '''
            // Register Service «service.name»
            simulator->AddService( new «service.id»(
                                "«service.name»", // name
                                «service.description()», // description
                                simulator, // parent
                                typeRegistry // type registry
                                ));
        '''
    }

    override collectIncludes(Catalogue type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        if (type.requireFactory)
            acceptor.userSource("TasMdk/Factory.h")
    }

}
