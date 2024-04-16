/**
 *-------------------------------------------------------------------------
 * Copyright (C) 2021 THALES ALENIA SPACE FRANCE. All rights reserved
 *-------------------------------------------------------------------------
 */
package org.eclipse.xsmp.profile.tas_mdk.validation;

import org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider;
import org.eclipse.xtext.preferences.PreferenceKey;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.SeverityConverter;

public class TasMdkIssueCodes extends XsmpcatIssueCodesProvider
{

  public static final String INVALID_VISIBILITY_PART = ISSUE_CODE_PREFIX + "invalidVisibility";

  public static final String INVALID_NAMING_CONVENTION_PART = ISSUE_CODE_PREFIX
          + "invalidNamingConvention";

  public static final String MISSING_DESCRIPTION_PART = ISSUE_CODE_PREFIX + "missingDescription";

  @Override
  protected void initialize(IAcceptor<PreferenceKey> acceptor)
  {
    super.initialize(acceptor);
    acceptor.accept(create(INVALID_VISIBILITY_PART, SeverityConverter.SEVERITY_ERROR));
    acceptor.accept(create(INVALID_NAMING_CONVENTION_PART, SeverityConverter.SEVERITY_ERROR));
    acceptor.accept(create(MISSING_DESCRIPTION_PART, SeverityConverter.SEVERITY_ERROR));
  }
}
