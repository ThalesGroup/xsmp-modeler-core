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
package org.eclipse.xsmp.ui.quickfix;

import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

/**
 * Custom quickfixes. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#quick-fixes
 */
public class XsmpcatQuickfixProvider
        extends org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider
{

  @Fix(XsmpcatIssueCodesProvider.INVALID_UUID)
  public void generateUUID(final Issue issue, IssueResolutionAcceptor acceptor)
  {
    acceptor.acceptMulti(issue, "Generate UUID", "Generate a random UUID.", "upcase.png", e -> {
      if (e instanceof Type)
      {
        ((Type) e).setUuid(UUID.randomUUID().toString());
      }
    });
  }

  @Fix(XsmpcatIssueCodesProvider.HIDDEN_ELEMENT)
  public void changeVisibility(final Issue issue, IssueResolutionAcceptor acceptor)
  {
    acceptor.accept(issue,
            "Change visibility of '" + issue.getData()[0] + "' to '" + issue.getData()[2] + "'.",
            "", "upcase.png", (e, ctx) -> {
              final var feature = e.eClass().getEStructuralFeature(issue.getData()[1]);
              final var elem = (EObject) e.eGet(feature);

              if (elem instanceof VisibilityElement && !elem.eIsProxy()) // TODO check if elem
                                                                         // resource is editable ?
              {
                ((VisibilityElement) elem)
                        .setVisibility(VisibilityKind.getByName(issue.getData()[2]));
              }
            });
  }

}
