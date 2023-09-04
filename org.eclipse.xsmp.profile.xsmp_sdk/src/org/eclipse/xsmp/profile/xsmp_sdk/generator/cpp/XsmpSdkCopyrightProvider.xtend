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
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp

import com.google.inject.Singleton
import org.eclipse.xsmp.XsmpVersion
import org.eclipse.xsmp.generator.cpp.CppCopyrightNoticeProvider

/** 
 * @author daveluy
 */
@Singleton
class XsmpSdkCopyrightProvider extends CppCopyrightNoticeProvider {

    protected override generatedBy() {
        '''
            XsmpSdkGenerator-«XsmpVersion.VERSION»
        '''
    }
}
