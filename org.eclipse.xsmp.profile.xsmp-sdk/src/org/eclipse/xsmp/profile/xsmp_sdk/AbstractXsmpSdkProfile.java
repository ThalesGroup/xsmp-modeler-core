/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.profile.xsmp_sdk;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.extension.Profile;

public abstract class AbstractXsmpSdkProfile extends Profile
{
  public static final String PROFILE_ID = "org.eclipse.xsmp.profile.xsmp-sdk";

  @Override
  public String getId()
  {
    return PROFILE_ID;
  }

  @Override
  public String getDescription()
  {
    return "XSMP-SDK Profile";
  }

  @Override
  public Collection<String> getSupportedLanguages()
  {
    return Collections.singleton(XsmpConstants.ORG_ECLIPSE_XSMP_XSMPCAT);
  }
}
