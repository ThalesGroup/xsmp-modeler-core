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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.xsmp.ui.editor.model.XsmpPreferenceAccess;
import org.eclipse.xsmp.ui.extension.Extension;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.builder.preferences.Messages;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;
import org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@SuppressWarnings("restriction")
public class XsmpPropertyPage extends PropertyPage
{

  private IProject project;

  private final List<Extension> availableExtensions = Extension.getTools();

  private final List<Extension> availableProfiles = Extension.getProfiles();

  private List<Extension> extensions;

  private Extension activeProfile;

  @Inject
  private PreferenceStoreAccessImpl preferenceStoreAccessImpl;

  private TableViewer extensionsList;

  private Combo profileCombo;

  private String languageName;

  @Override
  protected IPreferenceStore doGetPreferenceStore()
  {
    return preferenceStoreAccessImpl.getWritablePreferenceStore(project);

  }

  protected String unknownId(String id)
  {
    return id + " (Not Installed)";
  }

  private List<Extension> getExtensions()
  {
    return getExtensions(Extension.getToolsIds(getPreferenceStore()));
  }

  private List<Extension> getExtensions(Collection<String> ids)
  {
    final var result = new ArrayList<Extension>();

    for (final var id : ids)
    {

      var ext = availableExtensions.stream().filter(e -> id.equals(e.getId())).findFirst()
              .orElse(null);
      if (ext == null)
      {
        ext = new Extension(id, unknownId(id), null, null);
        availableExtensions.add(ext);
      }
      result.add(ext);
    }
    return result;
  }

  private Extension getProfile()
  {
    final var id = Extension.getProfileId(getPreferenceStore());

    return getProfile(id);
  }

  private Extension getProfile(String id)
  {
    var profile = availableProfiles.stream().filter(e -> id.equals(e.getId())).findFirst()
            .orElse(null);
    if (profile == null)
    {
      profile = new Extension(id, unknownId(id), null, null);
      availableProfiles.add(profile);
    }
    return profile;
  }

  private List<Extension> getDefaultExtensions()
  {
    return getExtensions(Extension.getDefaultToolsIds(getPreferenceStore()));
  }

  private Extension getDefaultProfile()
  {
    return getProfile(getPreferenceStore().getDefaultString(XsmpPreferenceAccess.PREF_PROFILE));
  }

  @Override
  protected Control createContents(final Composite parent)
  {

    final var font = parent.getFont();

    final var composite = new Composite(parent, SWT.NONE);
    final var layout = GridLayoutFactory.fillDefaults().create();
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    composite.setFont(font);

    project = Adapters.adapt(getElement(), IProject.class);

    final var headerComposite = new Composite(composite, SWT.NONE);

    headerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 2));
    headerComposite.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).create());

    activeProfile = getProfile();

    final var profileLabel = new Label(headerComposite, SWT.LEFT | SWT.WRAP);
    profileLabel.setFont(JFaceResources.getDialogFont());
    profileLabel.setText("Profile");

    profileLabel.setLayoutData(new GridData());

    profileCombo = new Combo(headerComposite, SWT.READ_ONLY);
    profileCombo.setItems(availableProfiles.stream().map(Extension::getName)
            .collect(Collectors.toList()).toArray(new String[0]));
    profileCombo.setData(availableProfiles.stream().map(Extension::getId)
            .collect(Collectors.toList()).toArray(new String[0]));

    profileCombo.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        activeProfile = availableProfiles.get(profileCombo.getSelectionIndex());
      }
    });
    profileCombo.setFont(JFaceResources.getDialogFont());
    profileCombo.setVisibleItemCount(30);
    profileCombo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
    profileCombo.select(availableProfiles.indexOf(activeProfile));

    final var extensionLabel = new Label(headerComposite, SWT.WRAP);
    extensionLabel.setText("Additional Tools");

    final var extensionsComposite = new Composite(composite, SWT.NONE);
    extensionsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    extensionsComposite.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).create());
    extensionsList = new TableViewer(extensionsComposite);
    extensionsList.getTable().setFont(font);
    extensionsList.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    extensionsList.setLabelProvider(new ExtensionLabelProvider());
    extensionsList.setContentProvider(ArrayContentProvider.getInstance());

    extensions = getExtensions();

    extensionsList.setInput(extensions);

    final var buttonComposite = new Composite(extensionsComposite, SWT.NONE);
    buttonComposite.setLayout(GridLayoutFactory.swtDefaults().margins(0, 0).create());
    buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
    final var addButton = new Button(buttonComposite, SWT.PUSH);
    addButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
    addButton.setText("Add...");
    addButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e)
      {

        final var selectionDialog = new ElementListSelectionDialog(parent.getShell(),
                new ExtensionLabelProvider());
        selectionDialog.setMessage("Select a tool to add to the Project");
        selectionDialog.setTitle("Select Tool");
        final List<Extension> filteredContributors = new ArrayList<>();
        for (final Extension contributor : Extension.getTools())
        {
          if (!extensions.contains(contributor))
          {
            filteredContributors.add(contributor);
          }
        }
        selectionDialog.setElements(filteredContributors.toArray());
        if (selectionDialog.open() == Window.OK)
        {
          for (final Object item : selectionDialog.getResult())
          {
            final var contributor = (Extension) item;
            extensions.add(contributor);
          }
          extensionsList.refresh();
        }
      }
    });
    final var removeButton = new Button(buttonComposite, SWT.PUSH);
    removeButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
    removeButton.setText("Remove");
    removeButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e)
      {

        final var selection = extensionsList.getStructuredSelection();
        for (final Object item : selection)
        {
          extensions.remove(item);
        }
        extensionsList.refresh();
      }
    });
    extensionsList.addSelectionChangedListener(
            event -> removeButton.setEnabled(!extensionsList.getSelection().isEmpty()));
    extensionsList.setSelection(new StructuredSelection()); // Empty selection

    return composite;
  }

  private static class ExtensionLabelProvider extends LabelProvider
  {
    @Override
    public String getText(Object element)
    {
      return ((Extension) element).getName();
    }
  }

  /**
   * Handle the exception thrown when saving.
   *
   * @param e
   *          the exception
   */
  protected void handle(InvocationTargetException e)
  {
    IStatus error;

    final var target = e.getTargetException();
    if (target instanceof CoreException)
    {
      error = ((CoreException) target).getStatus();
    }
    else
    {
      var msg = target.getMessage();
      if (msg == null)
      {
        msg = "Internal error";
      }
      error = new Status(IStatus.ERROR, getPropertyPageID(), 1, msg, target);
    }
    ErrorDialog.openError(getControl().getShell(), null, null, error);
  }

  @Inject
  public void setLanguageName(@Named(Constants.LANGUAGE_NAME) String languageName)
  {
    this.languageName = languageName;
  }

  protected String getPropertyPageID()
  {
    return languageName + ".propertyPage";
  }

  /**
   * @see PreferencePage#performOk
   */
  @Override
  public boolean performOk()
  {
    return processChanges((IWorkbenchPreferenceContainer) getContainer());
  }

  @Override
  public void performApply()
  {
    processChanges((IWorkbenchPreferenceContainer) getContainer());
  }

  @Override
  protected void performDefaults()
  {
    // reset extensions
    extensions = getDefaultExtensions();
    extensionsList.setInput(extensions);

    // reset profile
    activeProfile = getDefaultProfile();
    profileCombo.select(availableProfiles.indexOf(activeProfile));

    super.performDefaults();
  }

  protected boolean processChanges(IWorkbenchPreferenceContainer container)
  {

    final var originalExtensions = getExtensions();
    final var originalProfile = getProfile();

    final var extensionsChanged = !extensions.equals(originalExtensions);
    final var profileChanged = activeProfile != originalProfile;
    if (extensionsChanged || profileChanged)
    {

      var doBuild = false;
      final var dialog = new MessageDialog(getShell(),
              Messages.BuilderConfigurationBlock_SettingsChanged_Title, null,
              Messages.BuilderConfigurationBlock_SettingsChanged_ProjectBuild,
              MessageDialog.QUESTION,
              new String[]{
                IDialogConstants.YES_LABEL,
                IDialogConstants.NO_LABEL,
                IDialogConstants.CANCEL_LABEL },
              2);
      final var res = dialog.open();
      if (res == Window.OK)
      {
        doBuild = true;
      }
      else if (res != Window.CANCEL)
      {
        return false;
      }
      savePreferences(profileChanged, extensionsChanged);

      if (doBuild)
      {
        container.registerUpdateJob(getBuildJob(project));
      }
    }
    return true;
  }

  protected void savePreferences(boolean cdkChanged, boolean contributorsChanged)
  {
    final var preferenceStore = getPreferenceStore();
    if (contributorsChanged)
    {
      if (extensions.equals(getDefaultExtensions()))
      {
        preferenceStore.setToDefault(XsmpPreferenceAccess.PREF_TOOLS);
      }
      else
      {
        preferenceStore.putValue(XsmpPreferenceAccess.PREF_TOOLS,
                extensions.stream().map(Extension::getId).collect(Collectors.joining(", ")));
      }
    }

    if (cdkChanged)
    {
      if (activeProfile.equals(getDefaultProfile()))
      {
        preferenceStore.setToDefault(XsmpPreferenceAccess.PREF_PROFILE);
        preferenceStore.setToDefault(XsmpPreferenceAccess.PREF_MDK);
      }
      else
      {
        preferenceStore.putValue(XsmpPreferenceAccess.PREF_PROFILE, activeProfile.getId());
        if (preferenceStore.contains(XsmpPreferenceAccess.PREF_MDK))
        {
          preferenceStore.putValue(XsmpPreferenceAccess.PREF_MDK, activeProfile.getId());
        }
      }
    }
    try
    {
      if (preferenceStore instanceof IPersistentPreferenceStore)
      {
        ((IPersistentPreferenceStore) preferenceStore).save();
      }
    }
    catch (final IOException e)
    {
      handle(new InvocationTargetException(e));
    }
  }

  protected Job getBuildJob(IProject project)
  {
    final Job buildJob = new OptionsConfigurationBlock.BuildJob(
            Messages.BuilderConfigurationBlock_BuildJob_Title0, project);
    buildJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
    buildJob.setUser(true);
    return buildJob;
  }

}
