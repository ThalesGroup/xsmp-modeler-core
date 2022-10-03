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
package org.eclipse.xsmp.generator.cpp;

import org.eclipse.xsmp.xcatalogue.Type;

import com.google.inject.ImplementedBy;

/**
 * The generation strategy define if a type is only generated in src-gen/include-gen folder . In the
 * other case
 *
 * @author daveluy
 */
@ImplementedBy(GeneratorStrategy.class)
public interface IGenerationStrategy
{

  boolean useGenerationGapPattern(Type t);

}
