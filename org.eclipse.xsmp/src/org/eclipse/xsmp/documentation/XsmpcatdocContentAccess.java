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
package org.eclipse.xsmp.documentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xsmp.xcatalogue.impl.NamedElementImplCustom;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

public class XsmpcatdocContentAccess
{

  private static final String BLOCK_TAG_START = "<dl>"; //$NON-NLS-1$

  private static final String BLOCK_TAG_END = "</dl>"; //$NON-NLS-1$

  private static final String BLOCK_TAG_TITLE_START = "<dt>"; //$NON-NLS-1$

  private static final String BLOCK_TAG_TITLE_END = "</dt>"; //$NON-NLS-1$

  private static final String BLOCK_TAG_ENTRY_START = "<dd>"; //$NON-NLS-1$

  private static final String BLOCK_TAG_ENTRY_END = "</dd>"; //$NON-NLS-1$

  private static final String PARAM_NAME_START = "<b>"; //$NON-NLS-1$

  private static final String PARAM_NAME_END = "</b> "; //$NON-NLS-1$

  /**
   * Either an IMember or an IPackageFragment.
   */
  private final NamedElement fElement;

  private final Documentation xsmpcatdoc;

  private StringBuilder fBuf;

  private int fLiteralContent;

  private XsmpcatdocContentAccess(NamedElement element)
  {

    fElement = element;

    xsmpcatdoc = element.getMetadatum().getXsmpcatdoc();
  }

  private String toHTML()
  {
    fBuf = new StringBuilder();
    fLiteralContent = 0;

    elementToHTML();

    final var result = fBuf.toString();
    fBuf = null;
    return result;
  }

  private void elementToHTML()
  {

    TagElement deprecatedTag = null;
    TagElement returnTag = null;
    TagElement start = null;

    final List<TagElement> usages = new ArrayList<>();
    final List<TagElement> versions = new ArrayList<>(1);
    final List<TagElement> creator = new ArrayList<>(0);
    final List<TagElement> singlecast = new ArrayList<>(1);
    final List<TagElement> date = new ArrayList<>(1);
    final List<TagElement> unit = new ArrayList<>(1);
    final List<TagElement> category = new ArrayList<>();
    final List<TagElement> title = new ArrayList<>(1);
    final List<TagElement> param = new ArrayList<>();
    final List<TagElement> throwsTag = new ArrayList<>();
    final List<TagElement> rest = new ArrayList<>();

    final var tags = xsmpcatdoc.tags();
    for (final TagElement tag : tags)
    {
      final var tagName = tag.getTagName();
      if (tagName == null)
      {
        start = tag;

      }
      else
      {
        switch (tagName)
        {
          case "@usage":
            usages.add(tag);
            break;
          case "@version":
            versions.add(tag);
            break;
          case "@creator":
            creator.add(tag);
            break;
          case "@param":
            param.add(tag);
            break;
          case "@throws":
            throwsTag.add(tag);
            break;
          case "@deprecated":
            if (deprecatedTag == null)
            {
              deprecatedTag = tag; // only shows the first deprecated tag
            }
            break;
          case "@return":
            if (returnTag == null)
            {
              returnTag = tag; // only shows the first return tag
            }
            break;
          case "@uuid":
            // ignore this tag
            break;
          case "@date":
            date.add(tag);
            break;
          case "@singlecast":
            singlecast.add(tag);
            break;
          case "@title":
            title.add(tag);
            break;
          case "@unit":
            unit.add(tag);
            break;
          case "@category":
            category.add(tag);
            break;
          default:
            rest.add(tag);
            break;
        }
      }
    }

    if (deprecatedTag != null)
    {
      handleDeprecatedTag(deprecatedTag);
    }
    if (start != null)
    {
      handleContentElements(start.fragments());
    }
    String[] exceptionNames = null;

    String[] exceptionDescriptions = null;

    if (fElement instanceof Operation fMethod)
    {
      exceptionNames = new String[fMethod.getRaisedException().size()];
      exceptionDescriptions = new String[exceptionNames.length];
      for (var i = 0; i < exceptionNames.length; ++i)
      {
        exceptionNames[i] = fMethod.getRaisedException().get(i).getName();
        exceptionDescriptions[i] = fMethod.getRaisedException().get(i).getDescription();
      }

    }

    if (exceptionNames != null || !versions.isEmpty() || !param.isEmpty() || returnTag != null
            || throwsTag != null || !creator.isEmpty() || !date.isEmpty() || !singlecast.isEmpty()
            || !category.isEmpty() || !title.isEmpty() || !usages.isEmpty() || !unit.isEmpty()
            || !rest.isEmpty() || fBuf.length() > 0)
    {
      fBuf.append(BLOCK_TAG_START);
      handleParameterTags(param);
      handleReturnTag(returnTag);
      handleExceptionTags(throwsTag, exceptionNames, exceptionDescriptions);
      handleBlockTags("Version:", versions);
      handleBlockTags("Author:", creator);
      handleBlockTags("Usage:", usages);
      handleBlockTags("Date:", date);
      handleBlockTags("Title:", title);
      handleBlockTags("Unit:", unit);
      handleBlockTags("Catagories:", category);
      handleBlockTags("Singlecast:", singlecast);

      handleBlockTags(rest);
      fBuf.append(BLOCK_TAG_END);
    }
  }

  private void handleDeprecatedTag(TagElement tag)
  {
    fBuf.append("<p><b>"); //$NON-NLS-1$
    fBuf.append("Deprecated.");
    fBuf.append("</b> <i>"); //$NON-NLS-1$
    handleContentElements(tag.fragments());
    fBuf.append("</i></p>"); //$NON-NLS-1$
  }

  private void handleContentElements(List< ? extends TextElement> nodes)
  {
    handleContentElements(nodes, false);
  }

  private void handleContentElements(List< ? extends TextElement> nodes,
          boolean skipLeadingWhitespace)
  {
    TextElement previousNode = null;
    for (final TextElement child : nodes)
    {
      if (previousNode != null)
      {
        final var previousEnd = previousNode.getStartPosition() + previousNode.getLength();
        final var childStart = child.getStartPosition();
        if (previousEnd != childStart)
        {
          // Need to preserve whitespace before a node that's not
          // directly following the previous node (e.g. on a new line)
          // due to https://bugs.eclipse.org/bugs/show_bug.cgi?id=206518 :

          for (final INode node : NodeModelUtils.findNodesForFeature(fElement.getMetadatum(),
                  XcataloguePackage.Literals.METADATUM__DOCUMENTATION))
          {
            final var textWithStars = node.getText().substring(previousEnd, childStart);
            final var text = removeDocLineIntros(textWithStars);
            fBuf.append(text);
          }
        }
      }
      previousNode = child;

      var text = child.getText();
      if (skipLeadingWhitespace)
      {
        text = text.replaceFirst("^\\s", ""); //$NON-NLS-1$ //$NON-NLS-2$
      }
      // workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=233481 :
      text = text.replaceAll("(\r\n?|\n)([ \t]*\\*)", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
      handleText(text);

    }
  }

  private String removeDocLineIntros(String textWithStars)
  {
    final var lineBreakGroup = "(\\r\\n?|\\n)"; //$NON-NLS-1$
    final var noBreakSpace = "[^\r\n&&\\s]"; //$NON-NLS-1$
    return textWithStars
            .replaceAll(lineBreakGroup + noBreakSpace + "*\\*" /* + noBreakSpace + '?' */, "$1"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  private void handleText(String text)
  {
    if (fLiteralContent == 0)
    {
      fBuf.append(text);
    }
    else
    {
      appendEscaped(fBuf, text);
    }
  }

  private static void appendEscaped(StringBuilder buf, String text)
  {
    var nextToCopy = 0;
    final var length = text.length();
    for (var i = 0; i < length; i++)
    {
      final var ch = text.charAt(i);
      String rep = null;
      switch (ch)
      {
        case '&':
          rep = "&amp;"; //$NON-NLS-1$
          break;
        case '"':
          rep = "&quot;"; //$NON-NLS-1$
          break;
        case '<':
          rep = "&lt;"; //$NON-NLS-1$
          break;
        case '>':
          rep = "&gt;"; //$NON-NLS-1$
          break;
        default:
          break;
      }
      if (rep != null)
      {
        if (nextToCopy < i)
        {
          buf.append(text.substring(nextToCopy, i));
        }
        buf.append(rep);
        nextToCopy = i + 1;
      }
    }
    if (nextToCopy < length)
    {
      buf.append(text.substring(nextToCopy));
    }
  }

  private void handleBlockTags(String title, List<TagElement> tags)
  {
    if (tags.isEmpty())
    {
      return;
    }

    handleBlockTagTitle(title);

    for (final TagElement tag : tags)
    {
      fBuf.append(BLOCK_TAG_ENTRY_START);

      handleContentElements(tag.fragments());

      fBuf.append(BLOCK_TAG_ENTRY_END);
    }
  }

  private void handleReturnTag(TagElement tag)
  {
    if (tag == null)
    {
      return;
    }

    handleBlockTagTitle("Returns:");
    fBuf.append(BLOCK_TAG_ENTRY_START);

    handleContentElements(tag.fragments());

    fBuf.append(BLOCK_TAG_ENTRY_END);
  }

  private void handleBlockTags(List<TagElement> tags)
  {
    for (final TagElement tag : tags)
    {
      handleBlockTagTitle(tag.getTagName());
      fBuf.append(BLOCK_TAG_ENTRY_START);
      handleContentElements(tag.fragments());
      fBuf.append(BLOCK_TAG_ENTRY_END);
    }
  }

  private void handleBlockTagTitle(String title)
  {
    fBuf.append(BLOCK_TAG_TITLE_START);
    fBuf.append(title);
    fBuf.append(BLOCK_TAG_TITLE_END);
  }

  private void handleExceptionTags(List<TagElement> throwTag, String[] exceptionNames,
          String[] exceptionDescriptions)
  {
    if (exceptionNames == null || exceptionNames.length == 0)
    {
      return;
    }

    handleBlockTagTitle("Throws:");

    for (var i = 0; i < exceptionDescriptions.length; i++)
    {
      final var description = exceptionDescriptions[i];
      final var name = exceptionNames[i];
      if (name != null)
      {
        fBuf.append(BLOCK_TAG_ENTRY_START);
        fBuf.append(name);
        if (description != null)
        {
          fBuf.append(CONCAT_STRING);
          fBuf.append(description);
        }
        fBuf.append(BLOCK_TAG_ENTRY_END);
      }
    }
    for (final TagElement tag : throwTag)
    {
      fBuf.append(BLOCK_TAG_ENTRY_START);
      handleContentElements(tag.fragments());
      fBuf.append(BLOCK_TAG_ENTRY_END);
    }
  }

  private static final String CONCAT_STRING = " - ";

  private void handleParameterTags(List<TagElement> param)
  {
    if (param.isEmpty())
    {
      return;
    }
    handleBlockTagTitle("Parameters:");

    for (final TagElement p : param)
    {
      final var description = p.fragments().stream().skip(1).map(TextElement::getText)
              .collect(Collectors.joining(System.lineSeparator()));
      final var name = p.fragments().stream().map(TextElement::getText).findFirst().orElse(null);
      if (name != null)
      {
        fBuf.append(BLOCK_TAG_ENTRY_START);
        fBuf.append(PARAM_NAME_START);

        fBuf.append(name);

        fBuf.append(PARAM_NAME_END);
        if (description != null)
        {
          fBuf.append(description);
        }
        fBuf.append(BLOCK_TAG_ENTRY_END);
      }
    }
  }

  public static String getHTMLContent(NamedElement element) // throws CoreException
  {
    if (element instanceof NamedElementImplCustom)
    {

      return new XsmpcatdocContentAccess(element).toHTML();
    }
    return null;
  }

}
