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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.Class} object.
 */
public class ClassItemProvider extends StructureItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public ClassItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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
            getResourceLocator(), getString("_UI_Class_abstract_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Class_abstract_feature",
                    "_UI_Class_type"),
            XcataloguePackage.Literals.CLASS__ABSTRACT, true, false, false,
            ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Base feature.
   */
  protected void addBasePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Class_base_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Class_base_feature",
                    "_UI_Class_type"),
            XcataloguePackage.Literals.CLASS__BASE, true, false, true, null, null, null));
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update
   * any cached children and by creating a viewer notification, which it passes to
   * {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {

    switch (notification.getFeatureID(org.eclipse.xsmp.xcatalogue.Class.class))
    {
      case XcataloguePackage.CLASS__ABSTRACT:
        updateChildren(notification);
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      default:
        super.notifyChanged(notification);
    }
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
                    XcatalogueFactory.eINSTANCE.createOperation()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createAssociation()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createProperty()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean canContain(EObject owner, EStructuralFeature feature, Object value)
  {
    if (feature == XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER)
    {
      return value instanceof Operation || value instanceof Association || value instanceof Property
              || value instanceof Constant || value instanceof Field;
    }
    return super.canContain(owner, feature, value);
  }

}
