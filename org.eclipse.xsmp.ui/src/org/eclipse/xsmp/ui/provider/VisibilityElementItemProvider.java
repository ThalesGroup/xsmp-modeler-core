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
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.VisibilityElement} object.
 */
public class VisibilityElementItemProvider extends NamedElementItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public VisibilityElementItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addVisibilityPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Visibility feature.
   */
  protected void addVisibilityPropertyDescriptor(Object object)
  {

    itemPropertyDescriptors.add(new ItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_VisibilityElement_visibility_feature"),
            getString("_UI_PropertyDescriptor_description",
                    "_UI_VisibilityElement_visibility_feature", "_UI_VisibilityElement_type"),
            XcataloguePackage.Literals.VISIBILITY_ELEMENT__VISIBILITY, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null) {
      @Override
      public boolean canSetProperty(Object object)
      {
        final var parent = ((EObject) object).eContainer();
        if (parent != null)
        {
          final var eClass = parent.eClass();
          if (eClass == XcataloguePackage.Literals.STRUCTURE
                  || eClass == XcataloguePackage.Literals.INTERFACE)
          {
            return false;
          }
        }
        return true;
      }
    });

  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update
   * any cached children and by creating a viewer notification, which it passes to
   * {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(VisibilityElement.class))
    {
      case XcataloguePackage.VISIBILITY_ELEMENT__VISIBILITY:
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
  }

}
