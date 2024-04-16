/**
 * -------------------------------------------------------------------------
 * Copyright (C) 2021 THALES ALENIA SPACE FRANCE. All rights reserved
 * -------------------------------------------------------------------------
 */
package org.eclipse.xsmp.profile.tas_mdk.ui.wizard;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.xsmp.profile.tas_mdk.ui.wizard.messages"; //$NON-NLS-1$
	
	public static String GramCatalogueProject_Label;
	public static String GramCatalogueProject_Description;
	
	static {
	// initialize resource bundle
	NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	private Messages() {
	}
}
