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
package org.eclipse.xsmp.ui.editor.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xml.type.impl.XMLTypeFactoryImpl;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.Region;
import org.eclipse.xsmp.xcatalogue.Document;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xtext.ide.serializer.IChangeSerializer;
import org.eclipse.xtext.ui.editor.formatting2.ContentFormatter;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.model.XtextDocumentUtil;
import org.eclipse.xtext.ui.refactoring2.ChangeConverter;
import org.eclipse.xtext.ui.refactoring2.LtkIssueAcceptor;
import org.eclipse.xtext.ui.resource.ProjectByResourceProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author daveluy
 */
@SuppressWarnings("restriction")
public class XsmpcatDocumentProvider extends XtextDocumentProvider
{

  @Inject
  private Provider<IChangeSerializer> serializerProvider;

  @Inject
  private ChangeConverter.Factory changeConverterFactory;

  @Inject
  private LtkIssueAcceptor issueAcceptor;

  @Inject
  private ContentFormatter formatter;

  @Inject
  private XtextDocumentUtil xtextDocumentUtil;

  @Inject
  private ProjectByResourceProvider projectProvider;

  @Inject
  private SaveActionsPreferenceAccess saveActionsPreferenceAccess;

  @Override
  protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document,
          boolean overwrite)
    throws CoreException
  {
    final var doc = xtextDocumentUtil.getXtextDocument(document);
    final boolean hasSyntaxerrors = doc.readOnly(state -> state.getParseResult().hasSyntaxErrors());

    // Do not perform save actions if doc has syntax errors to avoid to break the doc
    if (!hasSyntaxerrors)
    {
      performSaveActions(monitor, doc);
    }

    super.doSaveDocument(monitor, element, doc, overwrite);
  }

  protected static XMLTypeFactoryImpl factory = new XMLTypeFactoryImpl();

  protected void performSaveActions(IProgressMonitor monitor, IXtextDocument document)
  {

    final var project = document.readOnly(state -> projectProvider.getProjectContext(state));

    monitor.beginTask("Updating Document", 2);

    final var serializer = serializerProvider.get();
    serializer.setUpdateCrossReferences(false);
    serializer.setUpdateRelatedFiles(false);
    serializer.setProgressMonitor(monitor);

    final boolean isModified = document.readOnly(state -> {

      var modified = false;
      // update the document date
      if (saveActionsPreferenceAccess.isUpdateDocumentDate(project))
      {
        serializer.addModification((Document) state.getContents().get(0), r -> r
                .setDate(new Date(Instant.now().truncatedTo(ChronoUnit.SECONDS).toEpochMilli())));
        modified = true;
      }

      // generate the missing UUIDs
      modified |= generateMissingUUIDs(serializer, state);

      // organize imports
      if (saveActionsPreferenceAccess.isOrganizeImports(project))
      {
        // TODO
      }

      return modified;
    });
    if (isModified)
    {
      applyModifications(monitor, serializer);
    }
    if (saveActionsPreferenceAccess.isFormatSourceCode(project))
    {
      formatSourceCode(monitor, document);
    }
  }

  protected boolean generateMissingUUIDs(IChangeSerializer serializer, Resource resource)
  {
    var modified = false;
    final var it = resource.getAllContents();
    while (it.hasNext())
    {
      final var obj = it.next();
      if (obj instanceof Type)
      {
        final var t = (Type) obj;
        if (t.getUuid() == null)
        {
          serializer.addModification(t, r -> r.setUuid(UUID.randomUUID().toString()));
          modified = true;
        }
        it.prune();
      }
    }
    return modified;
  }

  protected void applyModifications(IProgressMonitor monitor, IChangeSerializer serializer)
  {

    final var converter = changeConverterFactory.create("Updating Document", null, issueAcceptor);

    serializer.applyModifications(converter);
    final var change = converter.getChange();
    if (change != null)
    {
      change.initializeValidationData(monitor);
      try
      {
        change.perform(monitor);
      }
      catch (final CoreException e)
      {
        e.printStackTrace();
      }
      change.dispose();
    }
  }

  protected void formatSourceCode(IProgressMonitor monitor, IXtextDocument document)
  {
    monitor.beginTask("Format document", 10);
    DocumentRewriteSession rewriteSession = null;
    if (document instanceof IDocumentExtension4)
    {
      final var extension = (IDocumentExtension4) document;
      final var type = document.getLength() > 1000 ? DocumentRewriteSessionType.SEQUENTIAL
              : DocumentRewriteSessionType.UNRESTRICTED_SMALL;
      rewriteSession = extension.startRewriteSession(type);
    }

    try
    {
      formatter.format(document, new Region(0, document.getLength()));
    }
    finally
    {
      if (document instanceof IDocumentExtension4)
      {
        final var extension = (IDocumentExtension4) document;
        extension.stopRewriteSession(rewriteSession);
      }
    }

    monitor.done();
  }

}
