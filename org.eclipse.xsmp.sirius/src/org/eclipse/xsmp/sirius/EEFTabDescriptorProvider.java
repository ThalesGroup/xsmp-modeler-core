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
package org.eclipse.xsmp.sirius;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.eef.properties.ui.api.AbstractEEFSection;
import org.eclipse.eef.properties.ui.api.AbstractEEFTabDescriptor;
import org.eclipse.eef.properties.ui.api.EEFTabbedPropertySheetPage;
import org.eclipse.eef.properties.ui.api.IEEFSection;
import org.eclipse.eef.properties.ui.api.IEEFSectionDescriptor;
import org.eclipse.eef.properties.ui.api.IEEFTabDescriptor;
import org.eclipse.eef.properties.ui.api.IEEFTabDescriptorProvider;
import org.eclipse.eef.properties.ui.api.IEEFTabbedPropertySheetPageContributor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.BasicDetailViewCache;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailViewManager;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.common.collect.ImmutableSet;

public class EEFTabDescriptorProvider implements IEEFTabDescriptorProvider
{

  Set<String> contributors = ImmutableSet.of("org.eclipse.sirius.diagram.ui",
          "org.eclipse.sirius.ui.tools.views.model.explorer");

  @Override
  public Collection<IEEFTabDescriptor> get(IWorkbenchPart part, ISelection selection,
          IEEFTabbedPropertySheetPageContributor contributor)
  {
    if (contributors.contains(contributor.getContributorId()))
    {
      final AbstractEEFTabDescriptor descriptor = new EMFFormsTabDescriptor();
      final IEEFSectionDescriptor section = new EMFFormsSectionDescriptor();
      descriptor.setSectionDescriptors(Collections.singletonList(section));
      return Collections.singleton(descriptor);
    }
    return Collections.emptyList();

  }

  protected static Object getSelectedObject(ISelection currentSelection)
  {
    var selectedObject = currentSelection instanceof StructuredSelection
            ? ((StructuredSelection) currentSelection).getFirstElement()
            : null;

    if (selectedObject != null)
    {

      final var desc = new SiriusInputDescriptor(selectedObject);
      selectedObject = desc.getSemanticElement();
    }
    return selectedObject;
  }

  private static class EMFFormsTabDescriptor extends AbstractEEFTabDescriptor
  {

    @Override
    public String getCategory()
    {
      return "EMFForms";
    }

    @Override
    public String getId()
    {
      return EMFFormsTabDescriptor.class.getName();
    }

    @Override
    public String getLabel()
    {
      return "General";
    }

  }

  private static class EMFFormsSectionDescriptor implements IEEFSectionDescriptor
  {

    @Override
    public String getId()
    {
      return EMFFormsSectionDescriptor.class.getName();
    }

    @Override
    public IEEFSection getSectionClass()
    {
      return new EMFFormsPropertySection();
    }

    @Override
    public String getTargetTab()
    {
      return null;
    }

    @Override
    public IFilter getFilter()
    {
      return object -> true;
    }

    @Override
    public List<String> getInputTypes()
    {
      return new ArrayList<>();
    }

    @Override
    public int getEnablesFor()
    {
      return ENABLES_FOR_ANY;
    }

    @Override
    public boolean appliesTo(IWorkbenchPart part, ISelection selection)
    {

      final var selectedObject = getSelectedObject(selection);
      return selectedObject instanceof EObject && XcataloguePackage.eINSTANCE
              .equals(((EObject) selectedObject).eClass().getEPackage());
    }

    @Override
    public String getAfterSection()
    {
      return TOP;
    }

  }

  private static class EMFFormsPropertySection extends AbstractEEFSection
  {
    private DetailViewManager detailManager;

    @Override
    public void createControls(Composite parent,
            EEFTabbedPropertySheetPage aTabbedPropertySheetPage)
    {
      detailManager = new DetailViewManager(parent);
      detailManager.layoutDetailParent(parent);
      detailManager.setCache(new BasicDetailViewCache(30));
    }

    @Override
    public void setInput(IWorkbenchPart workbenchPart, ISelection currentSelection)
    {
      super.setInput(workbenchPart, currentSelection);

      final var selectedObject = getSelectedObject(currentSelection);

      // clear the current view
      detailManager.cacheCurrentDetail();

      // process the EObject
      if (selectedObject instanceof EObject)
      {

        final var eObject = EObject.class.cast(selectedObject);

        // if the view for this eObject is cached, reuse it
        if (detailManager.isCached(eObject))
        {
          detailManager.activate(eObject).getViewModelContext();
        }
        else
        {
          // create a new view for this eObject
          final var view = detailManager.getDetailView(eObject);
          view.setReadonly(view.isEffectivelyReadonly());
          final var modelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view,
                  eObject);
          detailManager.render(modelContext, ECPSWTViewRenderer.INSTANCE::render);
        }
      }

    }
  }
}
