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
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.NamedElement} object.
 */
public class NamedElementItemProvider extends GenericItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemStyledLabelProvider
{
  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public NamedElementItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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
      addDescriptionPropertyDescriptor(object);
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
            getResourceLocator(), getString("_UI_NamedElement_name_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_NamedElement_name_feature",
                    "_UI_NamedElement_type"),
            XcataloguePackage.Literals.NAMED_ELEMENT__NAME, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Description feature.
   */
  protected void addDescriptionPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_NamedElement_description_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_NamedElement_description_feature",
                    "_UI_NamedElement_type"),
            XcataloguePackage.Literals.NAMED_ELEMENT__DESCRIPTION, true, true, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This returns the label text for the adapted class.
   */
  @Override
  public final String getText(Object object)
  {
    final var fqn = qualifiedNameProvider.getFullyQualifiedName((EObject) object);
    if (fqn != null)
    {
      return fqn.toString("::");
    }
    return "";
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {

    switch (notification.getFeatureID(NamedElement.class))
    {
      case XcataloguePackage.NAMED_ELEMENT__NAME:
      case XcataloguePackage.NAMED_ELEMENT__DEPRECATED:
        updateChildren(notification);
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      default:
        super.notifyChanged(notification);
    }
  }

}
