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
package org.eclipse.xsmp.profile.xsmp_sdk.validation;

import org.eclipse.xsmp.validation.XsmpcatValidator;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescriptionsProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class contains custom validation rules. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@Singleton
public class XsmpSdkValidator extends XsmpcatValidator
{
  @Inject
  protected IResourceDescriptionsProvider resourceDescriptionProvider;

  @Override
  protected void checkCatalogue(Catalogue doc)
  {
    super.checkCatalogue(doc);
    final var it = resourceDescriptionProvider
            .getResourceDescriptions(doc.eResource().getResourceSet()).getExportedObjects(
                    XcataloguePackage.Literals.CATALOGUE, QualifiedName.create("xsmp_cdk"), false);

    if (!it.iterator().hasNext())
    {
      error("Could not find the xsmp.cdk Catalogue.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }
  }
}
