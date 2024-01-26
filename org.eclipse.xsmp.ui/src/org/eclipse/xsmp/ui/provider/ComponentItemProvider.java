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
package org.eclipse.xsmp.ui.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Container;
import org.eclipse.xsmp.xcatalogue.EntryPoint;
import org.eclipse.xsmp.xcatalogue.EventSink;
import org.eclipse.xsmp.xcatalogue.EventSource;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Reference;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.Component} object.
 */
public class ComponentItemProvider extends ReferenceTypeItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public ComponentItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  /**
   * This returns the property descriptors for the adapted class.
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
  {
    if (itemPropertyDescriptors == null)
    {
      super.getPropertyDescriptors(object);

      addAbstractPropertyDescriptor(object);
      addInterfacePropertyDescriptor(object);
      addBasePropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Abstract feature.
   */
  protected void addAbstractPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Component_abstract_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Component_abstract_feature",
                    "_UI_Component_type"),
            XcataloguePackage.Literals.COMPONENT__ABSTRACT, true, false, false,
            ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Interface feature.
   */
  protected void addInterfacePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Component_interface_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Component_interface_feature",
                    "_UI_Component_type"),
            XcataloguePackage.Literals.COMPONENT__INTERFACE, true, false, true, null, null, null));
  }

  /**
   * This adds a property descriptor for the Base feature.
   */
  protected void addBasePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Component_base_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Component_base_feature",
                    "_UI_Component_type"),
            XcataloguePackage.Literals.COMPONENT__BASE, true, false, true, null, null, null));
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing
   * the children that can be created under this object.
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
    super.collectNewChildDescriptors(newChildDescriptors, object);

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createAssociation()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createField()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createContainer()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createEntryPoint()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createEventSink()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createEventSource()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createReference()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean canContain(EObject owner, EStructuralFeature feature, Object value)
  {
    if (feature == XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER)
    {
      return value instanceof Operation || value instanceof Constant || value instanceof Property
              || value instanceof Association || value instanceof Field
              || value instanceof Container || value instanceof EntryPoint
              || value instanceof EventSink || value instanceof EventSource
              || value instanceof Reference;
    }
    return super.canContain(owner, feature, value);
  }

}
