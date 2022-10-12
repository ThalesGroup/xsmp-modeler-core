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
package org.eclipse.xsmp.ui.cdt;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IName;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.ICompositeType;
import org.eclipse.cdt.core.dom.ast.IEnumeration;
import org.eclipse.cdt.core.dom.ast.IEnumerator;
import org.eclipse.cdt.core.dom.ast.IFunction;
import org.eclipse.cdt.core.dom.ast.IMacroBinding;
import org.eclipse.cdt.core.dom.ast.IParameter;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.ITypedef;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPTemplateDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPTemplateParameter;
import org.eclipse.cdt.core.dom.ast.gnu.c.ICASTKnRFunctionDeclarator;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.index.IIndexBinding;
import org.eclipse.cdt.core.index.IIndexName;
import org.eclipse.cdt.core.index.IndexFilter;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ISourceReference;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.ui.text.CHeuristicScanner;
import org.eclipse.cdt.internal.ui.util.EditorUtility;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.cdt.ui.text.ICPartitions;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

/**
 * @author daveluy
 */
@SuppressWarnings("restriction")
public class SourceHovers
{

  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;

  private String getHtml(ICProject project, IIndex index, IName[] declNames, IIndexBinding b)
    throws CoreException
  {

    for (final IName name : declNames)
    {

      final var elem = OpenCppFileHandler.getCElementForName(project, index, name);

      if (elem instanceof ISourceReference)
      {
        final var handle = (ISourceReference) elem;
        final var tu = handle.getTranslationUnit();

        // Try with the indexer.
        final var source = computeSourceForName(name, b, tu);

        if (source == null || source.trim().isEmpty())
        {
          continue;
        }
        return "<pre><code>" + source + "</code></pre>";
      }
    }
    return null;
  }

  /**
   * @param target
   * @return the cpp source code of the target
   */
  public String hoverText(NamedElement target)
  {

    final var project = CoreModel.getDefault().getCModel()
            .getCProject(EcoreUtil.getURI(target).segment(1));
    IIndex index = null;

    // we need a read-lock on the index
    try
    {
      index = CCorePlugin.getIndexManager().getIndex(project);
      index.acquireReadLock();
      try
      {
        // find bindings for name
        final var bindings = index.findBindings(getPatterns(target), IndexFilter.ALL,
                new NullProgressMonitor());
        if (bindings.length == 0)
        {
          return null;
        }
        // find references for the binding
        final var b = bindings[0];

        // Convert binding to CElement.

        IName[] declNames = index.findDefinitions(b);

        var res = getHtml(project, index, declNames, b);
        if (res == null)
        {
          declNames = index.findDeclarations(b);

          res = getHtml(project, index, declNames, b);
        }
        return res;

      }
      finally
      {
        index.releaseReadLock();
      }
    }
    catch (final CoreException e)
    {
      CUIPlugin.log(e);
    }
    catch (final InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }

    return null;
  }

  private char[][] getPatterns(NamedElement target)
  {
    final var qfn = qualifiedNameProvider.getFullyQualifiedName(target);
    if (qfn == null)
    {
      return new char[0][];
    }
    final var count = qfn.getSegmentCount();
    final var result = new char[count][];
    for (var i = 0; i < count; ++i)
    {
      result[i] = qfn.getSegment(i).toCharArray();
    }
    return result;
  }

  private String computeSourceForName(IName name, IBinding binding, ITranslationUnit tUnit)
    throws CoreException
  {
    final var fMonitor = new NullProgressMonitor();
    final var fileLocation = name.getFileLocation();
    if (fileLocation == null)
    {
      return null;
    }
    var nodeOffset = fileLocation.getNodeOffset();
    var nodeLength = fileLocation.getNodeLength();
    final var fileName = fileLocation.getFileName();

    var location = Path.fromOSString(fileName);
    var locationKind = LocationKind.LOCATION;

    // Try to resolve path to a resource for proper encoding (bug 221029)
    final var file = EditorUtility.getWorkspaceFileAtLocation(location, tUnit);
    if (file != null)
    {
      location = file.getFullPath();
      locationKind = LocationKind.IFILE;
      if (name instanceof IIndexName)
      {
        // Need to adjust index offsets to current offsets
        // in case file has been modified since last index time.
        final var indexName = (IIndexName) name;
        final var timestamp = indexName.getFile().getTimestamp();
        final var converter = CCorePlugin.getPositionTrackerManager().findPositionConverter(file,
                timestamp);
        if (converter != null)
        {
          final var currentLocation = converter
                  .historicToActual(new Region(nodeOffset, nodeLength));
          nodeOffset = currentLocation.getOffset();
          nodeLength = currentLocation.getLength();
        }
      }
    }

    final var mgr = FileBuffers.getTextFileBufferManager();
    mgr.connect(location, locationKind, fMonitor);
    final var buffer = mgr.getTextFileBuffer(location, locationKind);
    try
    {
      final IRegion nameRegion = new Region(nodeOffset, nodeLength);
      final var nameOffset = nameRegion.getOffset();
      final int sourceStart;
      final int sourceEnd;
      final var doc = buffer.getDocument();
      if (nameOffset >= doc.getLength() || nodeLength <= 0)
      {
        return null;
      }
      if (binding instanceof IMacroBinding)
      {
        final var partition = TextUtilities.getPartition(doc, ICPartitions.C_PARTITIONING,
                nameOffset, false);
        if (!ICPartitions.C_PREPROCESSOR.equals(partition.getType()))
        {
          return null;
        }
        final var directiveStart = partition.getOffset();

        sourceStart = directiveStart;

        sourceEnd = directiveStart + partition.getLength();
      }
      else
      {
        // Expand source range to include preceding comment, if any
        final var isKnR = isKnRSource(name);
        sourceStart = computeSourceStart(doc, nameOffset, binding, isKnR);
        if (sourceStart == CHeuristicScanner.NOT_FOUND)
        {
          return null;
        }
        sourceEnd = computeSourceEnd(doc, nameOffset + nameRegion.getLength(), binding,
                name.isDefinition(), isKnR);
      }
      return buffer.getDocument().get(sourceStart, sourceEnd - sourceStart);
    }
    catch (final BadLocationException e)
    {
      CUIPlugin.log(e);
    }
    finally
    {
      mgr.disconnect(location, LocationKind.LOCATION, fMonitor);
    }

    return null;
  }

  private int computeSourceStart(IDocument doc, int nameOffset, IBinding binding, boolean isKnR)
    throws BadLocationException
  {
    var sourceStart = nameOffset;
    final var scanner = new CHeuristicScanner(doc);
    if (binding instanceof IParameter)
    {
      if (isKnR)
      {
        sourceStart = scanner.scanBackward(nameOffset, CHeuristicScanner.UNBOUND,
                new char[]{')', ';' });
      }
      else
      {
        sourceStart = scanner.scanBackward(nameOffset, CHeuristicScanner.UNBOUND,
                new char[]{'>', '(', ',' });
        if (sourceStart > 0 && doc.getChar(sourceStart) == '>')
        {
          sourceStart = scanner.findOpeningPeer(sourceStart - 1, '<', '>');
          if (sourceStart > 0)
          {
            sourceStart = scanner.scanBackward(sourceStart, CHeuristicScanner.UNBOUND,
                    new char[]{'(', ',' });
          }
        }
      }
      if (sourceStart == CHeuristicScanner.NOT_FOUND)
      {
        return sourceStart;
      }
      sourceStart = scanner.findNonWhitespaceForward(sourceStart + 1, nameOffset);
      if (sourceStart == CHeuristicScanner.NOT_FOUND)
      {
        sourceStart = nameOffset;
      }
    }
    else if (binding instanceof ICPPTemplateParameter)
    {
      sourceStart = scanner.scanBackward(nameOffset, CHeuristicScanner.UNBOUND,
              new char[]{'>', '<', ',' });
      if (sourceStart > 0 && doc.getChar(sourceStart) == '>')
      {
        sourceStart = scanner.findOpeningPeer(sourceStart - 1, '<', '>');
        if (sourceStart > 0)
        {
          sourceStart = scanner.scanBackward(sourceStart, CHeuristicScanner.UNBOUND,
                  new char[]{'<', ',' });
        }
      }
      if (sourceStart == CHeuristicScanner.NOT_FOUND)
      {
        return sourceStart;
      }
      sourceStart = scanner.findNonWhitespaceForward(sourceStart + 1, nameOffset);
      if (sourceStart == CHeuristicScanner.NOT_FOUND)
      {
        sourceStart = nameOffset;
      }
    }
    else if (binding instanceof IEnumerator)
    {
      sourceStart = scanner.scanBackward(nameOffset, CHeuristicScanner.UNBOUND,
              new char[]{'{', ',' });
      if (sourceStart == CHeuristicScanner.NOT_FOUND)
      {
        return sourceStart;
      }
      sourceStart = scanner.findNonWhitespaceForward(sourceStart + 1, nameOffset);
      if (sourceStart == CHeuristicScanner.NOT_FOUND)
      {
        sourceStart = nameOffset;
      }
    }
    else
    {
      final boolean expectClosingBrace;
      IType type = null;
      if (binding instanceof ITypedef)
      {
        type = ((ITypedef) binding).getType();
      }
      else if (binding instanceof IVariable)
      {
        type = ((IVariable) binding).getType();
      }
      expectClosingBrace = (type instanceof ICompositeType || type instanceof IEnumeration)
              && !(binding instanceof IVariable);
      final var nameLine = doc.getLineOfOffset(nameOffset);
      sourceStart = nameOffset;
      int commentBound;
      if (isKnR)
      {
        commentBound = scanner.scanBackward(sourceStart, CHeuristicScanner.UNBOUND,
                new char[]{')', ';' });
      }
      else
      {
        commentBound = scanner.scanBackward(sourceStart, CHeuristicScanner.UNBOUND,
                new char[]{'{', '}', ';' });
      }
      while (expectClosingBrace && commentBound > 0 && doc.getChar(commentBound) == '}')
      {
        final var openingBrace = scanner.findOpeningPeer(commentBound - 1, '{', '}');
        if (openingBrace != CHeuristicScanner.NOT_FOUND)
        {
          sourceStart = openingBrace - 1;
        }
        if (isKnR)
        {
          commentBound = scanner.scanBackward(sourceStart, CHeuristicScanner.UNBOUND,
                  new char[]{')', ';' });
        }
        else
        {
          commentBound = scanner.scanBackward(sourceStart, CHeuristicScanner.UNBOUND,
                  new char[]{'{', '}', ';' });
        }
      }
      if (commentBound == CHeuristicScanner.NOT_FOUND)
      {
        commentBound = -1; // unbound
      }
      sourceStart = Math.min(sourceStart, doc.getLineOffset(nameLine));

      final var nextNonWS = scanner.findNonWhitespaceForward(commentBound + 1, sourceStart);
      if (nextNonWS != CHeuristicScanner.NOT_FOUND)
      {
        final var nextNonWSLine = doc.getLineOfOffset(nextNonWS);
        final var lineOffset = doc.getLineOffset(nextNonWSLine);
        if (doc.get(lineOffset, nextNonWS - lineOffset).trim().isEmpty())
        {
          sourceStart = doc.getLineOffset(nextNonWSLine);
        }
        // }
      }
    }
    return sourceStart;
  }

  /**
   * Determines if the name is part of a KnR function definition.
   *
   * @param name
   * @return {@code true} if the name is part of a KnR function
   */
  private boolean isKnRSource(IName name)
  {
    if (name instanceof IASTName)
    {
      var node = (IASTNode) name;
      while (node.getParent() != null)
      {
        if (node instanceof ICASTKnRFunctionDeclarator)
        {
          return node.getParent() instanceof IASTFunctionDefinition;
        }
        node = node.getParent();
      }
    }
    return false;
  }

  private int computeSourceEnd(IDocument doc, int start, IBinding binding, boolean isDefinition,
          boolean isKnR)
    throws BadLocationException
  {
    var sourceEnd = start;
    final var scanner = new CHeuristicScanner(doc);
    // Expand forward to the end of the definition/declaration
    var searchBrace = false;
    var searchSemi = false;
    var searchComma = false;
    if (binding instanceof ICompositeType && isDefinition || binding instanceof IEnumeration
            || binding instanceof ICPPTemplateDefinition)
    {
      searchBrace = true;
    }
    else if (binding instanceof IFunction && isDefinition)
    {
      searchBrace = true;
    }
    else if (binding instanceof IParameter)
    {
      if (isKnR)
      {
        searchSemi = true;
      }
      else
      {
        searchComma = true;
      }
    }
    else if (binding instanceof IEnumerator || binding instanceof ICPPTemplateParameter)
    {
      searchComma = true;
    }
    else if (binding instanceof IVariable || binding instanceof ITypedef || !isDefinition)
    {
      searchSemi = true;
    }
    if (searchBrace)
    {
      final var brace = scanner.scanForward(start, CHeuristicScanner.UNBOUND, '{');
      if (brace != CHeuristicScanner.NOT_FOUND)
      {
        sourceEnd = scanner.findClosingPeer(brace + 1, '{', '}');
        if (sourceEnd == CHeuristicScanner.NOT_FOUND)
        {
          sourceEnd = doc.getLength();
        }
      }
      // Expand region to include whole line
      final var lineRegion = doc.getLineInformationOfOffset(sourceEnd);
      sourceEnd = lineRegion.getOffset() + lineRegion.getLength();
    }
    else if (searchSemi)
    {
      final var semi = scanner.scanForward(start, CHeuristicScanner.UNBOUND, ';');
      if (semi != CHeuristicScanner.NOT_FOUND)
      {
        sourceEnd = semi + 1;
      }
      // Expand region to include whole line
      final var lineRegion = doc.getLineInformationOfOffset(sourceEnd);
      sourceEnd = lineRegion.getOffset() + lineRegion.getLength();
    }
    else if (searchComma)
    {
      int bound;
      if (binding instanceof IParameter)
      {
        bound = scanner.findClosingPeer(start, '(', ')');
      }
      else if (binding instanceof ICPPTemplateParameter)
      {
        bound = scanner.findClosingPeer(start, '<', '>');
      }
      else if (binding instanceof IEnumerator)
      {
        bound = scanner.findClosingPeer(start, '{', '}');
      }
      else
      {
        bound = CHeuristicScanner.NOT_FOUND;
      }
      if (bound == CHeuristicScanner.NOT_FOUND)
      {
        bound = Math.min(doc.getLength(), start + 100);
      }
      final var comma = scanner.scanForward(start, bound, ',');
      if (comma == CHeuristicScanner.NOT_FOUND)
      {
        // last argument
        sourceEnd = bound;
      }
      else
      {
        sourceEnd = comma;
        // expand region to include whole line if rest is comment
        final var lineRegion = doc.getLineInformationOfOffset(sourceEnd);
        final var lineEnd = lineRegion.getOffset() + lineRegion.getLength();
        final var nextNonWS = scanner.findNonWhitespaceForwardInAnyPartition(sourceEnd + 1,
                lineEnd);
        if (nextNonWS != CHeuristicScanner.NOT_FOUND)
        {
          final var contentType = TextUtilities.getContentType(doc, ICPartitions.C_PARTITIONING,
                  nextNonWS, false);
          if (ICPartitions.C_MULTI_LINE_COMMENT.equals(contentType)
                  || ICPartitions.C_SINGLE_LINE_COMMENT.equals(contentType))
          {
            sourceEnd = lineEnd;
          }
        }
      }
    }
    return sourceEnd;
  }

}
