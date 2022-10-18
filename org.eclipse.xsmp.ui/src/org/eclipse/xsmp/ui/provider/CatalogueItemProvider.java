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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.Namespace;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Catalogue} object.
 */
public class CatalogueItemProvider extends DocumentItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public CatalogueItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
  {
    super(adapterFactory);
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
      childrenFeatures.add(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER);
    }
    return childrenFeatures;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {

    switch (notification.getFeatureID(Catalogue.class))
    {
      case XcataloguePackage.CATALOGUE__MEMBER:
        updateChildren(notification);
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
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createNamespace()));

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean canContain(EObject owner, EStructuralFeature feature, Object value)
  {
    if (feature == XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER)
    {
      return value instanceof Namespace;
    }
    return super.canContain(owner, feature, value);
  }

}
