/**
 *-------------------------------------------------------------------------
 * Copyright (C) 2021 THALES ALENIA SPACE FRANCE. All rights reserved
 *-------------------------------------------------------------------------
 */
package org.eclipse.xsmp.profile.tas_mdk.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.profile.tas_mdk.ui.quickfix.TasMdkQuickfixProvider;
import org.eclipse.xsmp.ui.XsmpcatUiModule;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class TasMdkUiModule extends XsmpcatUiModule
{

  public TasMdkUiModule(AbstractUIPlugin plugin)
  {
    super(plugin);
  }

  @Override
  public Class< ? extends IssueResolutionProvider> bindIssueResolutionProvider()
  {
    return TasMdkQuickfixProvider.class;
  }
}
