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
package org.eclipse.xsmp.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.xsmp.ui.editor.model.SaveActionsPreferenceAccess;
import org.eclipse.xtext.builder.internal.Activator;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock;
import org.eclipse.xtext.ui.preferences.ScrolledPageContent;
import org.eclipse.xtext.ui.util.PixelConverter;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class SaveActionsConfigurationBlock extends OptionsConfigurationBlock
{

  public static final String PROPERTY_PREFIX = "SaveActions";

  public static final String SETTINGS_SECTION_NAME = "SaveActionsBlock"; //$NON-NLS-1$

  @Inject
  private IPreferenceStoreAccess preferenceStoreAccess;

  @Inject
  private IDialogSettings dialogSettings;

  private PixelConverter fPixelConverter;

  @Override
  public void setProject(IProject project)
  {
    super.setProject(project);
    setPreferenceStore(preferenceStoreAccess.getWritablePreferenceStore(project));
  }

  @Override
  protected Control doCreateContents(Composite parent)
  {
    fPixelConverter = new PixelConverter(parent);
    setShell(parent.getShell());

    final var mainComp = new Composite(parent, SWT.NONE);
    mainComp.setFont(parent.getFont());
    final var layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    mainComp.setLayout(layout);

    final var commonComposite = createStyleTabContent(mainComp);
    final var gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
    gridData.heightHint = fPixelConverter.convertHeightInCharsToPixels(20);
    commonComposite.setLayoutData(gridData);
    validateSettings(null, null, null);
    registerKey(getIsProjectSpecificPropertyKey(getPropertyPrefix()));
    final var section = Activator.getDefault().getDialogSettings()
            .getSection(SETTINGS_SECTION_NAME);
    restoreSectionExpansionStates(section);
    return mainComp;
  }

  protected static final String[] BOOLEAN_VALUES = {IPreferenceStore.TRUE, IPreferenceStore.FALSE };

  private Composite createStyleTabContent(Composite mainComp)
  {
    final var nColumns = 3;
    final var sc1 = new ScrolledPageContent(mainComp);

    final var composite = sc1.getBody();
    final var layout = new GridLayout(nColumns, false);
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    composite.setLayout(layout);

    final var description = new Label(composite, SWT.LEFT | SWT.WRAP);
    description.setFont(description.getFont());
    description.setText("Perform the selected actions on save");
    description.setLayoutData(
            new GridData(GridData.BEGINNING, GridData.CENTER, true, false, nColumns - 1, 1));

    // fillSettingsPage(composite, nColumns, 0);
    // addAdditionalComponentsToSettingsPage(composite, nColumns, 0);
    addCheckBox(composite, "Format source code", SaveActionsPreferenceAccess.PREF_FORMAT,
            BOOLEAN_VALUES, 0);
    addCheckBox(composite, "Organize imports", SaveActionsPreferenceAccess.PREF_ORGANIZE_IMPORTS,
            BOOLEAN_VALUES, 0);
    addCheckBox(composite, "Update document date",
            SaveActionsPreferenceAccess.PREF_UPDATE_DOCUMENT_DATE, BOOLEAN_VALUES, 0);
    new Label(composite, SWT.NONE); // TODO what's this?
    restoreSectionExpansionStates(getDialogSettings());
    return sc1;
  }

  @Override
  protected boolean processChanges(IWorkbenchPreferenceContainer container)
  {
    savePreferences();
    return true;
  }

  @Override
  protected Job getBuildJob(IProject project)
  {

    return null;
  }

  @Override
  protected String[] getFullBuildDialogStrings(boolean workspaceSettings)
  {
    // TODO Auto-generated method stub
    return null;
  }

  protected IDialogSettings getDialogSettings()
  {
    return dialogSettings;
  }

  @Override
  public void dispose()
  {
    final var settings = Activator.getDefault().getDialogSettings()
            .addNewSection(SETTINGS_SECTION_NAME);
    storeSectionExpansionStates(settings);
    super.dispose();
  }

  @Override
  protected void validateSettings(String changedKey, String oldValue, String newValue)
  {
  }

  @Override
  public String getPropertyPrefix()
  {
    return PROPERTY_PREFIX;
  }
}
