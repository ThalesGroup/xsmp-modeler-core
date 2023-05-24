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
package org.eclipse.xsmp.profile.xsmp_sdk.generator

import com.google.inject.Singleton
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xsmp.generator.cpp.GeneratorUtil
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xsmp.xcatalogue.Document
import org.eclipse.xsmp.xcatalogue.XcataloguePackage
import org.eclipse.xtext.naming.QualifiedName

@Singleton
class XsmpSdkGeneratorExtension extends GeneratorUtil {

    override dependentPackages(Catalogue t) {
        var dependencies = super.dependentPackages(t)

        var cdk = EcoreUtil.resolve(
            resourceDescriptionProvider.getResourceDescriptions(t.eResource.resourceSet).getExportedObjects(
                XcataloguePackage.Literals.CATALOGUE, QualifiedName.create("xsmp_sdk"), false).head.EObjectOrProxy,
            t) as Catalogue

        if (cdk !== t)
            dependencies += cdk
        return dependencies
    }

    override String name(Document elem) {
        elem.name.replace('_', '.')
    }
}
