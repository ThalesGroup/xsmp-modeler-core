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
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Float}
 * object.
 */
public class FloatItemProvider extends SimpleTypeItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public FloatItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addPrimitiveTypePropertyDescriptor(object);
      addMinInclusivePropertyDescriptor(object);
      addMaxInclusivePropertyDescriptor(object);
      addUnitPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Primitive Type feature.
   */
  protected void addPrimitiveTypePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Float_primitiveType_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Float_primitiveType_feature",
                    "_UI_Float_type"),
            XcataloguePackage.Literals.FLOAT__PRIMITIVE_TYPE, true, false, true, null, null, null));
  }

  /**
   * This adds a property descriptor for the Min Inclusive feature.
   */
  protected void addMinInclusivePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Float_minInclusive_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Float_minInclusive_feature",
                    "_UI_Float_type"),
            XcataloguePackage.Literals.FLOAT__MIN_INCLUSIVE, true, false, false,
            ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Max Inclusive feature.
   */
  protected void addMaxInclusivePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Float_maxInclusive_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Float_maxInclusive_feature",
                    "_UI_Float_type"),
            XcataloguePackage.Literals.FLOAT__MAX_INCLUSIVE, true, false, false,
            ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Unit feature.
   */
  protected void addUnitPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Float_unit_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Float_unit_feature",
                    "_UI_Float_type"),
            XcataloguePackage.Literals.FLOAT__UNIT, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(org.eclipse.xsmp.xcatalogue.Float.class))
    {
      case XcataloguePackage.FLOAT__MIN_INCLUSIVE:
      case XcataloguePackage.FLOAT__MAX_INCLUSIVE:
      case XcataloguePackage.FLOAT__UNIT:
      case XcataloguePackage.FLOAT__MINIMUM:
      case XcataloguePackage.FLOAT__MAXIMUM:
      case XcataloguePackage.FLOAT__RANGE:
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      default:
        super.notifyChanged(notification);
    }
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that
   * can be created under this object.
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
    super.collectNewChildDescriptors(newChildDescriptors, object);
  }

}
