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
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.ParameterDirectionKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Parameter} object.
 */
public class ParameterItemProvider extends NamedElementItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public ParameterItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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
      addDirectionPropertyDescriptor(object);
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
            getResourceLocator(), getString("_UI_Parameter_type_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Parameter_type_feature",
                    "_UI_Parameter_type"),
            XcataloguePackage.Literals.PARAMETER__TYPE, true, false, true, null, null, null));
  }

  /**
   * This adds a property descriptor for the Default feature.
   */
  protected void addDefaultPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Parameter_default_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Parameter_default_feature",
                    "_UI_Parameter_type"),
            XcataloguePackage.Literals.PARAMETER__DEFAULT, false, false, false, null, null, null));
  }

  /**
   * This adds a property descriptor for the Direction feature.
   */
  protected void addDirectionPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(new ItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Parameter_direction_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Parameter_direction_feature",
                    "_UI_Parameter_type"),
            XcataloguePackage.Literals.PARAMETER__DIRECTION, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null) {
      @Override
      public Collection< ? > getChoiceOfValues(Object object)
      {

        Collection< ? > choices = super.getChoiceOfValues(object);
        if (object instanceof EObject)
        {
          final var eObject = (EObject) object;
          if (eObject.eContainingFeature() == XcataloguePackage.Literals.OPERATION__PARAMETER)
          {
            choices.remove(ParameterDirectionKind.RETURN);
          }
          else
          {
            choices = Collections.singletonList(ParameterDirectionKind.RETURN);
          }
        }
        return choices;
      }
    });
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(Parameter.class))
    {
      case XcataloguePackage.PARAMETER__DIRECTION:
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
