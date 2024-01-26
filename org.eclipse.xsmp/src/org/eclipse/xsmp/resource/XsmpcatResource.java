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
package org.eclipse.xsmp.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Triple;

/**
 * A customized resource that do not cache unresolvable proxies
 */
public class XsmpcatResource extends LazyLinkingResource
{

  @Override
  protected boolean isUnresolveableProxyCacheable(Triple<EObject, EReference, INode> triple)
  {
    // do not cache unresolveable proxy
    return false;
  }

}
