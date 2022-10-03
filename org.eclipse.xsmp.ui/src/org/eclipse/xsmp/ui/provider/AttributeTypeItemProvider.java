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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.AttributeType} object.
 */
public class AttributeTypeItemProvider extends TypeItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public AttributeTypeItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addAllowMultiplePropertyDescriptor(object);
      addUsagePropertyDescriptor(object);
      addTypePropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Allow Multiple feature.
   */
  protected void addAllowMultiplePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_AttributeType_allowMultiple_feature"),
            getString("_UI_PropertyDescriptor_description",
                    "_UI_AttributeType_allowMultiple_feature", "_UI_AttributeType_type"),
            XcataloguePackage.Literals.ATTRIBUTE_TYPE__ALLOW_MULTIPLE, true, false, false,
            ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Usage feature.
   */
  protected void addUsagePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_AttributeType_usage_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_AttributeType_usage_feature",
                    "_UI_AttributeType_type"),
            XcataloguePackage.Literals.ATTRIBUTE_TYPE__USAGE, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Usage feature.
   */
  protected void addTypePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_AttributeType_type_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_AttributeType_type_feature",
                    "_UI_AttributeType_type"),
            XcataloguePackage.Literals.ATTRIBUTE_TYPE__TYPE, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Usage feature.
   */
  protected void addDefaultPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_AttributeType_default_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_AttributeType_default_feature",
                    "_UI_AttributeType_type"),
            XcataloguePackage.Literals.ATTRIBUTE_TYPE__DEFAULT, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate
   * feature for an {@link org.eclipse.emf.edit.command.AddCommand},
   * {@link org.eclipse.emf.edit.command.RemoveCommand} or
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
   */
  @Override
  public Collection< ? extends EStructuralFeature> getChildrenFeatures(Object object)
  {
    return super.getChildrenFeatures(object);
  }

  /**
   *
   */
  @Override
  protected EStructuralFeature getChildFeature(Object object, Object child)
  {
    // Check the type of the specified child object and return the proper feature to use for
    // adding (see {@link AddCommand}) it as a child.

    return super.getChildFeature(object, child);
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(AttributeType.class))
    {
      case XcataloguePackage.ATTRIBUTE_TYPE__ALLOW_MULTIPLE:
      case XcataloguePackage.ATTRIBUTE_TYPE__USAGE:
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case XcataloguePackage.ATTRIBUTE_TYPE__TYPE:
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      default:
        super.notifyChanged(notification);
    }
  }

}
