/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
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
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.validation.NamesAreUniqueValidationHelper;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public class UuidsAreUniqueValidationHelper extends NamesAreUniqueValidationHelper
{

  @Override
  protected EStructuralFeature getNameFeature(EObject object)
  {
    return XsmpPackage.Literals.TYPE__UUID;
  }

  @Override
  protected void doCheckUniqueIn(IEObjectDescription description, Context context,
          ValidationMessageAcceptor acceptor)
  {
    final var object = description.getEObjectOrProxy();
    Preconditions.checkArgument(!object.eIsProxy());

    final var clusterType = XsmpPackage.Literals.TYPE;
    if (!clusterType.isSuperTypeOf(description.getEClass()))
    {
      return;
    }
    final var uuid = description.getUserData("uuid");
    if (uuid == null)
    {
      return;
    }

    final var validationScope = context.getValidationScope(description, clusterType);
    if (validationScope.isEmpty())
    {
      return;
    }

    final Iterable<IEObjectDescription> sameUuids = Iterables
            .filter(validationScope.getExportedObjects(), o -> uuid.equals(o.getUserData("uuid")));

    if (sameUuids instanceof Collection< ? > && ((Collection< ? >) sameUuids).size() <= 1)
    {
      return;
    }

    for (final IEObjectDescription candidate : sameUuids)
    {
      final var otherObject = candidate.getEObjectOrProxy();

      if (object != otherObject && !otherObject.eIsProxy()
              || !candidate.getEObjectURI().equals(description.getEObjectURI()))
      {
        createDuplicateNameError(description, clusterType, acceptor);
        return;

      }
    }
  }

  @Override
  public String getDuplicateNameErrorMessage(IEObjectDescription description, EClass clusterType,
          EStructuralFeature feature)
  {
    return "Duplicated UUID " + description.getUserData("uuid");
  }

  @Override
  protected String getErrorCode()
  {
    return org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider.INVALID_UUID;
  }

  @Override
  protected ImmutableSet<EClass> getClusterTypes()
  {
    return ImmutableSet.of(XsmpPackage.Literals.TYPE);
  }
}
