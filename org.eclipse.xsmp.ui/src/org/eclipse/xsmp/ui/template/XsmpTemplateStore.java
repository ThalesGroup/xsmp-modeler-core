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
package org.eclipse.xsmp.ui.template;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.ui.internal.XsmpActivator;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateStore;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * A specialized template store that get templates from current plugin and if
 * not found fallback on the default one
 *
 * @author yannick
 */
@SuppressWarnings({"deprecation", "restriction" })
@Singleton
public class XsmpTemplateStore extends XtextTemplateStore
{

  @Inject
  public XsmpTemplateStore(ContextTypeRegistry registry, IPreferenceStore store,
          @Named(Constants.LANGUAGE_NAME) String key, AbstractUIPlugin plugin)
  {
    super(registry, store, key, plugin);
  }

  @Override
  protected URL getTemplateFileURL(AbstractUIPlugin plugin)
  {

    final var templatePath = "templates/" + getKey() + ".xml";
    // get template from the current plugin
    final var url = plugin.getBundle().getEntry(templatePath);

    if (url != null)
    {
      return url;
    }

    // if the bundle is not ready then there is no template
    final var bundle = Platform.getBundle(XsmpActivator.PLUGIN_ID);
    if (!BundleUtility.isReady(bundle))
    {
      return null;
    }
    // retrieve template from default plugin
    return bundle.getEntry(templatePath);

  }
}
