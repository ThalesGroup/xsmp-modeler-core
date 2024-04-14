/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
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
import java.util.function.Consumer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xml.type.impl.XMLTypeFactoryImpl;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.Region;
import org.eclipse.xsmp.model.xsmp.Document;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xtext.ide.serializer.IChangeSerializer;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.formatting2.ContentFormatter;
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
public class XsmpDocumentProvider extends XtextDocumentProvider
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
  private XsmpPreferenceAccess xsmpPreferenceAccess;

  @Override
  protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document,
          boolean overwrite)
    throws CoreException
  {
    final var doc = xtextDocumentUtil.getXtextDocument(document);

    final var action = doc.modify(state -> {
      // Do not perform save actions if doc has syntax errors
      if (!state.getParseResult().hasSyntaxErrors())
      {
        return performSaveActions(monitor, state);
      }
      return (Consumer<IDocument>) d -> {
      };
    });
    action.accept(document);

    super.doSaveDocument(monitor, element, doc, overwrite);
  }

  protected static XMLTypeFactoryImpl factory = new XMLTypeFactoryImpl();

  protected Consumer<IDocument> performSaveActions(IProgressMonitor monitor, XtextResource state)
  {

    final var project = projectProvider.getProjectContext(state);

    monitor.beginTask("Updating Document", 2);

    final var serializer = serializerProvider.get();
    serializer.setUpdateCrossReferences(false);
    serializer.setUpdateRelatedFiles(false);
    serializer.setProgressMonitor(monitor);

    final var modified = generateMissingUUIDs(serializer, state)
            || updateDocumentDate(serializer, state, project);

    return document -> {
      if (modified)
      {
        applyModifications(monitor, serializer);
      }
      if (xsmpPreferenceAccess.isFormatSourceCode(project))
      {
        formatSourceCode(monitor, document);
      }
    };
  }

  protected boolean updateDocumentDate(IChangeSerializer serializer, Resource resource,
          IProject project)
  {
    if (xsmpPreferenceAccess.isUpdateDocumentDate(project))
    {
      serializer.addModification((Document) resource.getContents().get(0), r -> r
              .setDate(new Date(Instant.now().truncatedTo(ChronoUnit.SECONDS).toEpochMilli())));
      return true;
    }
    return false;
  }

  protected boolean generateMissingUUIDs(IChangeSerializer serializer, Resource resource)
  {
    var modified = false;

    final var it = resource.getAllContents();
    while (it.hasNext())
    {
      final var obj = it.next();
      if (obj instanceof final Type t)
      {
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
        // ignore
      }
      change.dispose();
    }
  }

  protected void formatSourceCode(IProgressMonitor monitor, IDocument document)
  {
    monitor.beginTask("Format document", 10);
    DocumentRewriteSession rewriteSession = null;
    if (document instanceof final IDocumentExtension4 extension)
    {
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
      if (document instanceof final IDocumentExtension4 extension)
      {
        extension.stopRewriteSession(rewriteSession);
      }
    }

    monitor.done();
  }

}
