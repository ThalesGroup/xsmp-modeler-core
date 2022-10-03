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
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.Namespace;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Namespace} object.
 */
public class NamespaceItemProvider extends NamedElementItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public NamespaceItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

    }
    return itemPropertyDescriptors;
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

    switch (notification.getFeatureID(Namespace.class))
    {
      case XcataloguePackage.NAMESPACE__MEMBER:
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

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createAttributeType()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createEnumeration()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createNativeType()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createPrimitiveType()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createArray()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createFloat()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createInteger()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createString()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createValueReference()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createEventType()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createStructure()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createClass()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createException()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createInterface()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createModel()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER,
                    XcatalogueFactory.eINSTANCE.createService()));

  }

}
