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
package org.eclipse.xsmp.ui.editor.model;

import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpPreferenceAccess
{
  public static final String PREF_FORMAT = "format"; //$NON-NLS-1$

  public static final String PREF_UPDATE_DOCUMENT_DATE = "update_document_date"; //$NON-NLS-1$

  private IPreferenceStoreAccess preferenceStoreAccess;

  public static class Initializer implements IPreferenceStoreInitializer
  {
    @Override
    public void initialize(IPreferenceStoreAccess preferenceStoreAccess)
    {
      final var store = preferenceStoreAccess.getWritablePreferenceStore();
      store.setDefault(PREF_FORMAT, true);
      store.setDefault(PREF_UPDATE_DOCUMENT_DATE, true);
    }
  }

  @Inject
  public void setPreferenceStoreAccess(IPreferenceStoreAccess preferenceStoreAccess)
  {
    this.preferenceStoreAccess = preferenceStoreAccess;
  }

  public boolean isFormatSourceCode(Object context)
  {
    final var preferenceStore = preferenceStoreAccess.getContextPreferenceStore(context);
    return !preferenceStore.contains(PREF_FORMAT) || preferenceStore.getBoolean(PREF_FORMAT);
  }

  public void setFormatSourceCode(Object context, boolean enabled)
  {
    final var preferenceStore = preferenceStoreAccess.getWritablePreferenceStore(context);
    preferenceStore.setValue(PREF_FORMAT, enabled);
  }

  public boolean isUpdateDocumentDate(Object context)
  {
    final var preferenceStore = preferenceStoreAccess.getContextPreferenceStore(context);
    return !preferenceStore.contains(PREF_UPDATE_DOCUMENT_DATE)
            || preferenceStore.getBoolean(PREF_UPDATE_DOCUMENT_DATE);
  }

  public void setUpdateDocumentDate(Object context, boolean enabled)
  {
    final var preferenceStore = preferenceStoreAccess.getWritablePreferenceStore(context);
    preferenceStore.setValue(PREF_UPDATE_DOCUMENT_DATE, enabled);
  }

}
