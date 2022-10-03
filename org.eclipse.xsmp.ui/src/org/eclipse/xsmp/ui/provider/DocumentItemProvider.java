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
import org.eclipse.xsmp.xcatalogue.Document;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Document}
 * object.
 */
public class DocumentItemProvider extends NamedElementItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public DocumentItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addTitlePropertyDescriptor(object);
      addDatePropertyDescriptor(object);
      addCreatorPropertyDescriptor(object);
      addVersionPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Title feature.
   */
  protected void addTitlePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Document_title_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Document_title_feature",
                    "_UI_Document_type"),
            XcataloguePackage.Literals.DOCUMENT__TITLE, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Date feature.
   */
  protected void addDatePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Document_date_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Document_date_feature",
                    "_UI_Document_type"),
            XcataloguePackage.Literals.DOCUMENT__DATE, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Creator feature.
   */
  protected void addCreatorPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Document_creator_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Document_creator_feature",
                    "_UI_Document_type"),
            XcataloguePackage.Literals.DOCUMENT__CREATOR, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Version feature.
   */
  protected void addVersionPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Document_version_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Document_version_feature",
                    "_UI_Document_type"),
            XcataloguePackage.Literals.DOCUMENT__VERSION, true, false, false,
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
    if (childrenFeatures == null)
    {
      super.getChildrenFeatures(object);
      childrenFeatures.add(XcataloguePackage.Literals.DOCUMENT__IMPORT_SECTION);
    }
    return childrenFeatures;
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

    switch (notification.getFeatureID(Document.class))
    {
      case XcataloguePackage.DOCUMENT__TITLE:
      case XcataloguePackage.DOCUMENT__DATE:
      case XcataloguePackage.DOCUMENT__CREATOR:
      case XcataloguePackage.DOCUMENT__VERSION:
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case XcataloguePackage.DOCUMENT__IMPORT_SECTION:
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), true, false));
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

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.DOCUMENT__IMPORT_SECTION,
                    XcatalogueFactory.eINSTANCE.createImportSection()));
  }

}
