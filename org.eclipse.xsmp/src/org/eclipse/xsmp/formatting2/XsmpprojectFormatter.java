/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.formatting2;

import static org.eclipse.xsmp.model.xsmp.XsmpPackage.Literals.METADATUM__DOCUMENTATION;

import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xsmp.model.xsmp.ProfileReference;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.ProjectReference;
import org.eclipse.xsmp.model.xsmp.SourceFolder;
import org.eclipse.xsmp.model.xsmp.ToolReference;
import org.eclipse.xsmp.services.XsmpprojectGrammarAccess;
import org.eclipse.xtext.formatting2.AbstractJavaFormatter;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;

import com.google.inject.Inject;

public class XsmpprojectFormatter extends AbstractJavaFormatter
{

  @Inject
  private XsmpprojectGrammarAccess ga;

  protected void format(Project project, IFormattableDocument doc)
  {
    format(project.getMetadatum(), doc);
    final var parentRegion = regionFor(project);
    final var kw = parentRegion.keyword(ga.getProjectAccess().getProjectKeyword_1());
    doc.prepend(kw, this::noSpace);
    doc.append(kw, this::oneSpace);

    doc.format(project.getProfile());

    for (final var tool : project.getTools())
    {
      doc.format(tool);
    }
    for (final var sourceFolder : project.getSourceFolders())
    {
      doc.format(sourceFolder);
    }
    for (final var dependency : project.getReferencedProjects())
    {
      doc.format(dependency);
    }
  }

  public ITextReplacer createDocumentationReplacer(ISemanticRegion description)
  {
    return new DocumentationReplacer(description);
  }

  protected void format(Metadatum parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    final var description = parentRegion.feature(METADATUM__DOCUMENTATION);

    doc.append(description, this::newLine);

    if (description != null)
    {
      doc.addReplacer(createDocumentationReplacer(description));
    }
  }

  protected void format(ProfileReference profile, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(profile);
    final var kw = parentRegion.keyword(ga.getProfileReferenceAccess().getProfileKeyword_0());
    doc.prepend(kw, it -> {
      it.setNewLines(1, 2, 3);
      it.lowPriority();
    });
    doc.append(kw, this::oneSpace);
  }

  protected void format(ToolReference tool, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(tool);
    final var kw = parentRegion.keyword(ga.getToolReferenceAccess().getToolKeyword_0());
    doc.prepend(kw, it -> {
      it.setNewLines(1, 2, 3);
      it.lowPriority();
    });
    doc.append(kw, this::oneSpace);
  }

  protected void format(SourceFolder sourceFolder, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(sourceFolder);
    final var kw = parentRegion.keyword(ga.getSourceFolderAccess().getSourceKeyword_0());
    doc.prepend(kw, it -> {
      it.setNewLines(1, 2, 3);
      it.lowPriority();
    });
    doc.append(kw, this::oneSpace);
  }

  protected void format(ProjectReference dependency, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(dependency);

    final var kw = parentRegion.keyword(ga.getProjectReferenceAccess().getDependencyKeyword_0());

    doc.prepend(kw, it -> {
      it.setNewLines(1, 2, 3);
      it.lowPriority();
    });

    doc.append(kw, this::oneSpace);
  }

}
