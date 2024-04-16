/**
 *-------------------------------------------------------------------------
 * Copyright (C) 2021 THALES ALENIA SPACE FRANCE. All rights reserved
 *-------------------------------------------------------------------------
 */
package org.eclipse.xsmp.profile.tas_mdk.ui.quickfix;

import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.VisibilityElement;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.profile.tas_mdk.validation.TasMdkIssueCodes;
import org.eclipse.xsmp.ui.quickfix.XsmpcatQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

/**
 * Custom quickfixes. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#quick-fixes
 */
public class TasMdkQuickfixProvider extends XsmpcatQuickfixProvider
{

  @Fix(TasMdkIssueCodes.MISSING_DESCRIPTION_PART)
  public void addDescription(final Issue issue, IssueResolutionAcceptor acceptor)
  {
    acceptor.acceptMulti(issue, "Add Description", "Add a description to the element.",
            "upcase.png", element -> {
              if (element instanceof NamedElement)
              {
                ((NamedElement) element).setDescription(
                        element.eClass().getName() + " " + ((NamedElement) element).getName());
              }

            });
  }

  @Fix(TasMdkIssueCodes.INVALID_VISIBILITY_PART)
  public void removeVisibility(final Issue issue, IssueResolutionAcceptor acceptor)
  {
    acceptor.accept(issue, "Remove visibility", "Remove the element visibility.", "upcase.png",

            (element, context) -> {
              if (element instanceof VisibilityElement)
              {
                element.eUnset(XsmpPackage.Literals.VISIBILITY_ELEMENT__VISIBILITY);
              }

            });
  }

}
