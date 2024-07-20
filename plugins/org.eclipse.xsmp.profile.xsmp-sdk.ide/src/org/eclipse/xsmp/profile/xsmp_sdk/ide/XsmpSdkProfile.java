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
package org.eclipse.xsmp.profile.xsmp_sdk.ide;

import org.eclipse.xsmp.profile.xsmp_sdk.AbstractXsmpSdkProfile;
import org.eclipse.xtext.ISetup;

import com.google.inject.Singleton;

@Singleton
public class XsmpSdkProfile extends AbstractXsmpSdkProfile
{
  public XsmpSdkProfile()
  {
    initialize();
  }

  @Override
  protected ISetup createSetup(String language)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(language))
    {
      return new XsmpSdkIdeSetup();
    }
    return null;
  }

}
