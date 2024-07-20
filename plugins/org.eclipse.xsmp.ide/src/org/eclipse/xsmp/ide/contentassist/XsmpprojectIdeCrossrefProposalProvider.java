package org.eclipse.xsmp.ide.contentassist;

import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IdeCrossrefProposalProvider;
import org.eclipse.xtext.resource.IEObjectDescription;

public class XsmpprojectIdeCrossrefProposalProvider extends IdeCrossrefProposalProvider
{
  @Override
  protected ContentAssistEntry createProposal(IEObjectDescription candidate,
          CrossReference crossRef, ContentAssistContext context)
  {
    return getProposalCreator().createProposal(
            "\"" + getQualifiedNameConverter().toString(candidate.getName()) + "\"", context, e -> {
              e.setSource(candidate);
              e.setDescription(
                      candidate.getEClass() != null ? candidate.getEClass().getName() : null);
              e.setKind(ContentAssistEntry.KIND_REFERENCE);
            });
  }
}
