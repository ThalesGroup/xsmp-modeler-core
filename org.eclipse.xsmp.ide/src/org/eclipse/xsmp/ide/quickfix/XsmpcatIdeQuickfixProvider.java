/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.quickfix;

import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xtext.ide.editor.quickfix.AbstractDeclarativeIdeQuickfixProvider;
import org.eclipse.xtext.ide.editor.quickfix.DiagnosticResolutionAcceptor;
import org.eclipse.xtext.ide.editor.quickfix.QuickFix;

import com.google.gson.JsonArray;

public class XsmpcatIdeQuickfixProvider extends AbstractDeclarativeIdeQuickfixProvider
{

  @QuickFix(XsmpcatIssueCodesProvider.INVALID_UUID)
  public void generateUUID(DiagnosticResolutionAcceptor acceptor)
  {
    acceptor.accept("Generate UUID", e -> {

      if (e instanceof Type)
      {
        ((Type) e).setUuid(UUID.randomUUID().toString());
      }
    });

  }

  @QuickFix(XsmpcatIssueCodesProvider.HIDDEN_ELEMENT)
  public void changeVisibility(DiagnosticResolutionAcceptor acceptor)
  {
    acceptor.accept("Change Type visibility", (issue, e) -> e2 -> {
      final var data = (JsonArray) issue.getData();

      final var feature = e.eClass().getEStructuralFeature(data.get(1).getAsString());
      final var elem = (EObject) e.eGet(feature);

      if (elem instanceof final VisibilityElement elemcast && !elem.eIsProxy())
      {
        elemcast.setVisibility(VisibilityKind.getByName(data.get(2).getAsString()));
        // FIXME not working
      }
    });
  }

}
