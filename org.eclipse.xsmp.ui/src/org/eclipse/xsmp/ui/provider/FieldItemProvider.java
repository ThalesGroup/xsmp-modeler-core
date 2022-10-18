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

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Field}
 * object.
 */
public class FieldItemProvider extends VisibilityElementItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public FieldItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addTypePropertyDescriptor(object);
      addDefaultPropertyDescriptor(object);
      addTransientPropertyDescriptor(object);
      addInputPropertyDescriptor(object);
      addOutputPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Type feature.
   */
  protected void addTypePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Field_type_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Field_type_feature",
                    "_UI_Field_type"),
            XcataloguePackage.Literals.FIELD__TYPE, true, false, true, null, null, null));
  }

  /**
   * This adds a property descriptor for the Default feature.
   */
  protected void addDefaultPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Field_default_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Field_default_feature",
                    "_UI_Field_type"),
            XcataloguePackage.Literals.FIELD__DEFAULT, false, true, false, null, null, null));
  }

  /**
   * This adds a property descriptor for the Transient feature.
   */
  protected void addTransientPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Field_transient_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Field_transient_feature",
                    "_UI_Field_type"),
            XcataloguePackage.Literals.FIELD__TRANSIENT, true, false, false,
            ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Input feature.
   */
  protected void addInputPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Field_input_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Field_input_feature",
                    "_UI_Field_type"),
            XcataloguePackage.Literals.FIELD__INPUT, true, false, false,
            ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Output feature.
   */
  protected void addOutputPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Field_output_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Field_output_feature",
                    "_UI_Field_type"),
            XcataloguePackage.Literals.FIELD__OUTPUT, true, false, false,
            ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {

    switch (notification.getFeatureID(Field.class))
    {
      // case XcataloguePackage.FIELD__TRANSIENT:
      case XcataloguePackage.FIELD__INPUT:
      case XcataloguePackage.FIELD__OUTPUT:
      case XcataloguePackage.FIELD__TYPE:
        updateChildren(notification);
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      default:
        super.notifyChanged(notification);
    }
  }

}
