/**
 * ******************************************************************************
 * Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * *****************************************************************************
 */
package org.eclipse.xsmp.model.xsmp.impl;

import org.eclipse.xsmp.util.XsmpUtil;

public class SourcePathImplCustom extends SourcePathImpl
{
  @Override
  public void setName(String newName)
  {
    super.setName(XsmpUtil.normalizePath(newName));
  }

} // SourcePathImplCustom
