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
package org.eclipse.xsmp;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.Constants;

public interface XsmpConstants extends Constants
{

  String XSMP_PROJECT_FILENAME = "xsmp.project";

  String ORG_ECLIPSE_XSMP_XSMPCAT = "org.eclipse.xsmp.Xsmpcat";

  String ORG_ECLIPSE_XSMP_XSMPCORE = "org.eclipse.xsmp.Xsmpcore";

  String ORG_ECLIPSE_XSMP_XSMPPROJECT = "org.eclipse.xsmp.Xsmpproject";

  URI XSMP_PROJECT_EXTENSIONS_URI = URI.createURI("extension:/xsmp.project");
}
