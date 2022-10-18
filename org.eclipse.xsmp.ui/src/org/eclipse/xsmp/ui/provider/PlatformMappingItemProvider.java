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
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.PlatformMapping;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.PlatformMapping}
 * object.
 */
public class PlatformMappingItemProvider extends GenericItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemStyledLabelProvider
{
  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public PlatformMappingItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addNamePropertyDescriptor(object);
      addTypePropertyDescriptor(object);
      addNamespacePropertyDescriptor(object);
      addLocationPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Name feature.
   */
  protected void addNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_PlatformMapping_name_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_PlatformMapping_name_feature",
                    "_UI_PlatformMapping_type"),
            XcataloguePackage.Literals.PLATFORM_MAPPING__NAME, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Type feature.
   */
  protected void addTypePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_PlatformMapping_type_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_PlatformMapping_type_feature",
                    "_UI_PlatformMapping_type"),
            XcataloguePackage.Literals.PLATFORM_MAPPING__TYPE, true, true, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Namespace feature.
   */
  protected void addNamespacePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_PlatformMapping_namespace_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_PlatformMapping_namespace_feature",
                    "_UI_PlatformMapping_type"),
            XcataloguePackage.Literals.PLATFORM_MAPPING__NAMESPACE, true, true, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Location feature.
   */
  protected void addLocationPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_PlatformMapping_location_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_PlatformMapping_location_feature",
                    "_UI_PlatformMapping_type"),
            XcataloguePackage.Literals.PLATFORM_MAPPING__LOCATION, true, true, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This returns the label text for the adapted class.
   */
  @Override
  public final String getText(Object object)
  {
    return ((PlatformMapping) object).getName();
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {

    switch (notification.getFeatureID(PlatformMapping.class))
    {
      case XcataloguePackage.PLATFORM_MAPPING__NAME:
      case XcataloguePackage.PLATFORM_MAPPING__TYPE:
      case XcataloguePackage.PLATFORM_MAPPING__NAMESPACE:
        updateChildren(notification);
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      default:
        super.notifyChanged(notification);
    }
  }

}
