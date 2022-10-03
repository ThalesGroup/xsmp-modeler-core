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
package org.eclipse.xsmp.validation;

import org.eclipse.xtext.preferences.PreferenceKey;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.SeverityConverter;

public class XsmpcatIssueCodesProvider extends ConfigurableIssueCodesProvider
{
  protected static final String ISSUE_CODE_PREFIX = "org.eclipse.xsmp..";

  public static final String DUPLICATE_ENUMERATION_VALUE = ISSUE_CODE_PREFIX
          + "invalid_enumeration_value";

  public static final String INVALID_MEMBER_TYPE = ISSUE_CODE_PREFIX + "invalid_member_type";

  public static final String INVALID_MODIFIER = ISSUE_CODE_PREFIX + "invalid_modifier";

  public static final String INVALID_UUID = ISSUE_CODE_PREFIX + "invalidUUID";

  public static final String HIDDEN_ELEMENT = ISSUE_CODE_PREFIX + "hiddenElement";

  public static final String NAME_IS_RESERVED_KEYWORD = ISSUE_CODE_PREFIX
          + "name_is_reserved_keyword";

  public static final String DUPLICATE_SERVICE_NAME = ISSUE_CODE_PREFIX + "duplicate_service_name";

  public static final String DEPRECATED_MODEL_PART = ISSUE_CODE_PREFIX + "deprecatedModelPart";

  @Override
  protected void initialize(IAcceptor<PreferenceKey> acceptor)
  {
    super.initialize(acceptor);
    acceptor.accept(create(DEPRECATED_MODEL_PART, SeverityConverter.SEVERITY_WARNING));
  }
}
