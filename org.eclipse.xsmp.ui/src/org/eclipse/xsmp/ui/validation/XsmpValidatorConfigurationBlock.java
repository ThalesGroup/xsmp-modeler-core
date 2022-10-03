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
package org.eclipse.xsmp.ui.validation;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider;
import org.eclipse.xtext.ui.validation.AbstractValidatorConfigurationBlock;

@SuppressWarnings("restriction")
public class XsmpValidatorConfigurationBlock extends AbstractValidatorConfigurationBlock
{

  protected static final String SETTINGS_SECTION_NAME = "Xsmpcat";

  @Override
  protected void fillSettingsPage(Composite composite, int nColumns, int defaultIndent)
  {
    addComboBox(XsmpcatIssueCodesProvider.DEPRECATED_MODEL_PART, "Deprecated Model Part", composite,
            defaultIndent);
  }

  @Override
  public void dispose()
  {
    storeSectionExpansionStates(getDialogSettings());
    super.dispose();
  }

  @Override
  protected IDialogSettings getDialogSettings()
  {
    final var dialogSettings = super.getDialogSettings();
    final var section = dialogSettings.getSection(SETTINGS_SECTION_NAME);
    if (section == null)
    {
      return dialogSettings.addNewSection(SETTINGS_SECTION_NAME);
    }
    return section;
  }
}
