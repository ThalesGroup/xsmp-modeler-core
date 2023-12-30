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
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.Operation} object.
 */
public class OperationItemProvider extends VisibilityElementItemProvider
{

  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public OperationItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addRaisedExceptionPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Raised Exception feature.
   */
  protected void addRaisedExceptionPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Operation_raisedException_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Operation_raisedException_feature",
                    "_UI_Operation_type"),
            XcataloguePackage.Literals.OPERATION__RAISED_EXCEPTION, true, false, true, null, null,
            null));
  }

  /**
   * This specifies how to implement {@link #getChildren} and is used to deduce an
   * appropriate feature for an {@link org.eclipse.emf.edit.command.AddCommand},
   * {@link org.eclipse.emf.edit.command.RemoveCommand} or
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
   */
  @Override
  public Collection< ? extends EStructuralFeature> getChildrenFeatures(Object object)
  {
    if (childrenFeatures == null)
    {
      super.getChildrenFeatures(object);
      childrenFeatures.add(XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER);
      childrenFeatures.add(XcataloguePackage.Literals.OPERATION__PARAMETER);
    }
    return childrenFeatures;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update
   * any cached children and by creating a viewer notification, which it passes to
   * {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {

    switch (notification.getFeatureID(Operation.class))
    {

      case XcataloguePackage.OPERATION__PARAMETER:
      case XcataloguePackage.OPERATION__RETURN_PARAMETER:
        updateChildren(notification);
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), true, false));
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
            .add(createChildParameter(XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER,
                    XcatalogueFactory.eINSTANCE.createParameter()));
    newChildDescriptors.add(createChildParameter(XcataloguePackage.Literals.OPERATION__PARAMETER,
            XcatalogueFactory.eINSTANCE.createParameter()));
  }

  /**
   * This returns the label text for
   * {@link org.eclipse.emf.edit.command.CreateChildCommand}.
   */
  @Override
  public String getCreateChildText(Object owner, Object feature, Object child,
          Collection< ? > selection)
  {
    final var childFeature = feature;
    final var childObject = child;

    final var qualify = childFeature == XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER;

    if (qualify)
    {
      return getString("_UI_CreateChild_text2",
              new Object[]{
                getTypeText(childObject),
                getFeatureText(childFeature),
                getTypeText(owner) });
    }
    return super.getCreateChildText(owner, feature, child, selection);
  }

}
