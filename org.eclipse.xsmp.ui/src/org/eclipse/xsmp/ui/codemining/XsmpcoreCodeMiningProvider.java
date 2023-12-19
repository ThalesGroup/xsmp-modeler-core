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
package org.eclipse.xsmp.ui.codemining;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.codemining.ICodeMining;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.codemining.AbstractXtextCodeMiningProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;

@SuppressWarnings("restriction")
public class XsmpcoreCodeMiningProvider extends AbstractXtextCodeMiningProvider {
	@Override
	protected void createCodeMinings(IDocument document, XtextResource resource, CancelIndicator indicator,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
		
		// TODO: implement me
		// use acceptor.accept(super.createNewLineHeaderCodeMining(...)) to add a new code mining to the final list
		
		// example:
		// acceptor.accept(createNewLineHeaderCodeMining(1, document, "Header annotation"));
	}
}
