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

import static com.google.common.collect.Sets.newHashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * Check that modifier are valid
 */
public class ModifierValidator
{
  public static final String PUBLIC_MODIFIER = "public";

  public static final String PROTECTED_MODIFIER = "protected";

  public static final String PRIVATE_MODIFIER = "private";

  private final Set<String> allowedModifiers;

  private final String allowedModifiersAsString;

  public ModifierValidator(List<String> allowedModifiers)
  {
    this.allowedModifiers = newHashSet(allowedModifiers);

    if (allowedModifiers.isEmpty())
    {
      allowedModifiersAsString = "";
    }
    else
    {
      allowedModifiersAsString = "; only "
              + allowedModifiers.stream().collect(Collectors.joining(", ")) + " are permitted";
    }
  }

  protected void checkModifiers(VisibilityElement member, ValidationMessageAcceptor acceptor)
  {

    final Set<String> seenModifiers = newHashSet();
    var visibilitySeen = false;
    var accessSeen = false;
    final var memberName = member.eClass().getName() + " " + member.getName();

    for (var i = 0; i < member.getModifiers().size(); ++i)
    {
      final var modifier = member.getModifiers().get(i);
      if (!allowedModifiers.contains(modifier))
      {
        error("Illegal modifier for the " + memberName + allowedModifiersAsString, member, i,
                acceptor);
      }
      if (!seenModifiers.add(modifier))
      {
        error("Duplicate modifier '" + modifier + "' for the " + memberName, member, i, acceptor);
        continue;
      }

      switch (modifier)
      {
        case PUBLIC_MODIFIER:
        case PROTECTED_MODIFIER:
        case PRIVATE_MODIFIER:
          if (visibilitySeen)
          {
            error("The " + memberName + " can only set one of public / protected / private", member,
                    i, acceptor);
          }
          visibilitySeen = true;
          break;
        case "readWrite":
        case "readOnly":
        case "writeOnly":
          if (accessSeen)
          {
            error("The " + memberName + " can only set one of readWrite / readOnly / writeOnly",
                    member, i, acceptor);
          }
          accessSeen = true;
          break;
        default:
          break;
      }

    }
  }

  protected void error(String message, EObject source, int index,
          ValidationMessageAcceptor acceptor)
  {
    acceptor.acceptError(message, source, XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS,
            index, XsmpcatIssueCodesProvider.INVALID_MODIFIER);
  }

  protected void issue(String message, EObject source, int index,
          ValidationMessageAcceptor acceptor, String code, String... issueData)
  {
    acceptor.acceptWarning(message, source,
            XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS, index, code, issueData);
  }

}
