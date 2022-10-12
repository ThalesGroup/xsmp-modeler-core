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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.xsmp.ui.mdk.MdkHelper;
import org.eclipse.xtext.builder.internal.Activator;
import org.eclipse.xtext.builder.preferences.Messages;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock;
import org.eclipse.xtext.ui.preferences.ScrolledPageContent;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class MdkConfigurationBlock extends OptionsConfigurationBlock
{

  protected static final String[] BOOLEAN_VALUES = {IPreferenceStore.TRUE, IPreferenceStore.FALSE };

  protected static final int INDENT_AMOUNT = 32;

  public static final String SETTINGS_SECTION_NAME = "XsmpcatBuilderConfigurationBlock"; //$NON-NLS-1$

  public static final String PROPERTY_PREFIX = "BuilderConfiguration";

  @Inject
  private IPreferenceStoreAccess preferenceStoreAccess;

  @Override
  public void setProject(IProject project)
  {
    super.setProject(project);
    setPreferenceStore(preferenceStoreAccess.getWritablePreferenceStore(project));
  }

  @Override
  protected Control doCreateContents(Composite parent)
  {
    setShell(parent.getShell());
    final var mainComp = new Composite(parent, SWT.NONE);
    mainComp.setFont(parent.getFont());
    final var layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    mainComp.setLayout(layout);
    final var othersComposite = createBuildPathTabContent(mainComp);
    final var gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
    othersComposite.setLayoutData(gridData);
    validateSettings(null, null, null);
    return mainComp;
  }

  private Composite createBuildPathTabContent(Composite parent)
  {
    final var columns = 3;
    final var pageContent = new ScrolledPageContent(parent);
    final var layout = new GridLayout();
    layout.numColumns = columns;
    layout.marginHeight = 0;
    layout.marginWidth = 0;

    final var composite = pageContent.getBody();
    composite.setLayout(layout);

    createGeneralSectionItems(composite);

    registerKey(getIsProjectSpecificPropertyKey(getPropertyPrefix()));
    final var section = Activator.getDefault().getDialogSettings()
            .getSection(SETTINGS_SECTION_NAME);
    restoreSectionExpansionStates(section);
    return pageContent;
  }

  protected void createGeneralSectionItems(Composite composite)
  {

    // do not provide extensions for global configuration
    if (getProject() != null)
    {
      final List<String> ids = new ArrayList<>();
      final List<String> labels = new ArrayList<>();

      for (final IConfigurationElement ext : MdkHelper.INSTANCE.getExtensions())
      {
        ids.add(ext.getAttribute("id"));
        labels.add(ext.getAttribute("name"));
      }

      ids.add(MdkHelper.INSTANCE.getDefaultValue());
      labels.add("Undefined");

      final var currentValue = getValue(MdkHelper.INSTANCE.getSetting());

      if (!ids.contains(currentValue) && currentValue != null && !currentValue.isEmpty())
      {

        ids.add(currentValue);
        labels.add(currentValue + " (Not Installed)");
      }

      addComboBox(composite, "Mdk", MdkHelper.INSTANCE.getSetting(), 2,
              ids.toArray(new String[ids.size()]), labels.toArray(new String[labels.size()]));

    }

  }

  @Override
  protected String getValue(String key)
  {
    var value = super.getValue(key);
    if (value.isEmpty() && key.equals(MdkHelper.INSTANCE.getSetting()))
    {
      value = "default";
    }
    return value;
  }

  @Override
  public boolean hasProjectSpecificOptions(IProject project)
  {
    return true;
  }

  @Override
  protected void validateSettings(String changedKey, String oldValue, String newValue)
  {
    // ignore
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
  protected String[] getFullBuildDialogStrings(boolean workspaceSettings)
  {
    final var title = Messages.BuilderConfigurationBlock_SettingsChanged_Title;
    String message;
    if (workspaceSettings)
    {
      message = Messages.BuilderConfigurationBlock_SettingsChanged_WorkspaceBuild;
    }
    else
    {
      message = Messages.BuilderConfigurationBlock_SettingsChanged_ProjectBuild;
    }
    return new String[]{title, message };
  }

  @Override
  protected Job getBuildJob(IProject project)
  {
    final Job buildJob = new OptionsConfigurationBlock.BuildJob(
            Messages.BuilderConfigurationBlock_BuildJob_Title0, project);
    buildJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
    buildJob.setUser(true);
    return buildJob;
  }

  @Override
  public String getPropertyPrefix()
  {
    return PROPERTY_PREFIX;
  }

}
