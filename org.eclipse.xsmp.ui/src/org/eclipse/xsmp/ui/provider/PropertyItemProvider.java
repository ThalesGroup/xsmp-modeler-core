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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Property}
 * object.
 */
public class PropertyItemProvider extends VisibilityElementItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public PropertyItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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
      addAttachedFieldPropertyDescriptor(object);
      addGetRaisesPropertyDescriptor(object);
      addSetRaisesPropertyDescriptor(object);
      addAccessPropertyDescriptor(object);
      addCategoryPropertyDescriptor(object);
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
            getResourceLocator(), getString("_UI_Property_type_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Property_type_feature",
                    "_UI_Property_type"),
            XcataloguePackage.Literals.PROPERTY__TYPE, true, false, true, null, null, null));
  }

  /**
   * This adds a property descriptor for the Attached Field feature.
   */
  protected void addAttachedFieldPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Property_attachedField_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Property_attachedField_feature",
                    "_UI_Property_type"),
            XcataloguePackage.Literals.PROPERTY__ATTACHED_FIELD, true, false, true, null, null,
            null));
  }

  /**
   * This adds a property descriptor for the Get Raises feature.
   */
  protected void addGetRaisesPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Property_getRaises_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Property_getRaises_feature",
                    "_UI_Property_type"),
            XcataloguePackage.Literals.PROPERTY__GET_RAISES, true, false, true, null, null, null));
  }

  /**
   * This adds a property descriptor for the Set Raises feature.
   */
  protected void addSetRaisesPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Property_setRaises_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Property_setRaises_feature",
                    "_UI_Property_type"),
            XcataloguePackage.Literals.PROPERTY__SET_RAISES, true, false, true, null, null, null));
  }

  /**
   * This adds a property descriptor for the Access feature.
   */
  protected void addAccessPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Property_access_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Property_access_feature",
                    "_UI_Property_type"),
            XcataloguePackage.Literals.PROPERTY__ACCESS, true, false, false,
            ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Category feature.
   */
  protected void addCategoryPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Property_category_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Property_category_feature",
                    "_UI_Property_type"),
            XcataloguePackage.Literals.PROPERTY__CATEGORY, true, false, false,
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

    switch (notification.getFeatureID(Property.class))
    {
      case XcataloguePackage.PROPERTY__ACCESS:
      case XcataloguePackage.PROPERTY__CATEGORY:
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

  /**
   * This returns Operation.gif.
   */
  @Override
  public Object getImage(Object object)
  {
    final var ope = (Property) object;

    final List<Object> images = new ArrayList<>(2);
    if (ope.eContainer() != null)
    {
      images.add(imageHelper.getImage("full/obj16/" + ope.eClass().getName() + "_"
              + ope.getRealVisibility().getLiteral() + ".png"));
    }
    else
    {
      images.add(getResourceLocator()
              .getImage("full/obj16/" + ope.eClass().getName() + "_public.png"));
    }

    switch (ope.getAccess())
    {
      case READ_ONLY:
        images.add(imageHelper.getImage("full/ovr16/read.png"));
        break;
      case WRITE_ONLY:
        images.add(imageHelper.getImage("full/ovr16/write.png"));
        break;
      default:
        images.add(imageHelper.getImage("full/ovr16/read.png"));
        images.add(imageHelper.getImage("full/ovr16/write.png"));
        break;
    }
    return new ComposedImage(images);
  }

  /**
   * This returns the label styled text for the adapted class.
   */
  @Override
  public StyledString getStyledText(Object object)
  {
    final var elem = (Property) object;
    return text(elem, elem.getType());
  }
}
