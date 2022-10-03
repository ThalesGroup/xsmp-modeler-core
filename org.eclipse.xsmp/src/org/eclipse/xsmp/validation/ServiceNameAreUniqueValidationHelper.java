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
package org.eclipse.xsmp.validation;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.validation.NamesAreUniqueValidationHelper;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public class ServiceNameAreUniqueValidationHelper extends NamesAreUniqueValidationHelper
{

  @Override
  protected EStructuralFeature getNameFeature(EObject object)
  {
    return XcataloguePackage.Literals.NAMED_ELEMENT__NAME;
  }

  @Override
  protected void doCheckUniqueIn(IEObjectDescription description, Context context,
          ValidationMessageAcceptor acceptor)
  {
    final var object = description.getEObjectOrProxy();
    Preconditions.checkArgument(!object.eIsProxy());

    final var clusterType = getClusterType(description);
    if (clusterType == null)
    {
      return;
    }
    final var name = description.getName().getLastSegment();
    if (name == null)
    {
      return;
    }

    final var validationScope = context.getValidationScope(description, clusterType);
    if (validationScope.isEmpty())
    {
      return;
    }

    final Iterable<IEObjectDescription> sameNames = Iterables.filter(
            validationScope.getExportedObjects(),
            o -> XcataloguePackage.Literals.SERVICE.isSuperTypeOf(o.getEClass())
                    && name.equals(o.getName().getLastSegment()));

    if (sameNames instanceof Collection< ? > && ((Collection< ? >) sameNames).size() <= 1)
    {
      return;
    }

    for (final IEObjectDescription candidate : sameNames)
    {
      final var otherObject = candidate.getEObjectOrProxy();

      if (object != otherObject && getAssociatedClusterType(candidate.getEClass()) == clusterType
              && !otherObject.eIsProxy()
              || !candidate.getEObjectURI().equals(description.getEObjectURI()))
      {
        createDuplicateNameError(description, clusterType, acceptor);
        return;

      }
    }
  }

  @Override
  protected EClass getClusterType(IEObjectDescription description)
  {
    return description.getEClass() == XcataloguePackage.Literals.SERVICE
            ? XcataloguePackage.Literals.SERVICE
            : null;
  }

  @Override
  protected ImmutableSet<EClass> getClusterTypes()
  {
    return ImmutableSet.of(XcataloguePackage.Literals.SERVICE);
  }

  @Override
  public String getDuplicateNameErrorMessage(IEObjectDescription description, EClass clusterType,
          EStructuralFeature feature)
  {
    return "Duplicate Service Name " + description.getName().getLastSegment();
  }

  @Override
  protected String getErrorCode()
  {
    return org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider.DUPLICATE_SERVICE_NAME;
  }

}
