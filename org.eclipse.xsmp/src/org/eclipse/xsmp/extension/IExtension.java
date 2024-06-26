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
package org.eclipse.xsmp.extension;

import com.google.inject.Injector;

public interface IExtension
{
  /**
   * Get the extension ID
   *
   * @return the id
   */
  String getId();

  /**
   * Get the extension description
   *
   * @return the description
   */
  String getDescription();

  /**
   * Get the injector associated to a language
   *
   * @param languageName
   *          the language name
   * @return the injector associated to the language
   */
  Injector getInjector(String languageName);

}
