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
package org.eclipse.xsmp.design;

import org.eclipse.eef.properties.ui.api.IEEFTabDescriptor;
import org.eclipse.eef.properties.ui.api.IEEFTabDescriptorFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This class is an EEF Tab descriptor filter that filter all tabs except the EMF forms tab
 *
 * @author daveluy
 */
public class EEFTabDescriptorFilter implements IEEFTabDescriptorFilter
{

  /**
   * Only filter the "General" tab
   */
  @Override
  public boolean filter(IEEFTabDescriptor tabDescriptor, IWorkbenchPart part, ISelection selection)
  {

    return "General".equals(tabDescriptor.getText());
  }
}
