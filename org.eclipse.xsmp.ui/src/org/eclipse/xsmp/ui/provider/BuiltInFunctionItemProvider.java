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
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.BuiltInFunction} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 */
public class BuiltInFunctionItemProvider extends BuiltInExpressionItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   */
  @Inject
  public BuiltInFunctionItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  /**
   * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
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
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   */
  @Override
  public Collection< ? extends EStructuralFeature> getChildrenFeatures(Object object)
  {
    if (childrenFeatures == null)
    {
      super.getChildrenFeatures(object);
      childrenFeatures.add(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER);
    }
    return childrenFeatures;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   */
  @Override
  protected EStructuralFeature getChildFeature(Object object, Object child)
  {
    // Check the type of the specified child object and return the proper feature to use for
    // adding (see {@link AddCommand}) it as a child.

    return super.getChildFeature(object, child);
  }

  /**
   * This returns BuiltInFunction.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
   */
  @Override
  public Object getImage(Object object)
  {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/BuiltInFunction"));
  }

  /**
   * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   */
  @Override
  public String getText(Object object)
  {
    return ((StyledString) getStyledText(object)).getString();
  }

  /**
   * This returns the label styled text for the adapted class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   */
  @Override
  public Object getStyledText(Object object)
  {
    final var label = ((BuiltInFunction) object).getName();
    final var styledLabel = new StyledString();
    if (label == null || label.length() == 0)
    {
      styledLabel.append(getString("_UI_BuiltInFunction_type"),
              StyledString.Style.QUALIFIER_STYLER);
    }
    else
    {
      styledLabel.append(getString("_UI_BuiltInFunction_type"), StyledString.Style.QUALIFIER_STYLER)
              .append(" " + label);
    }
    return styledLabel;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(BuiltInFunction.class))
    {
      case XcataloguePackage.BUILT_IN_FUNCTION__PARAMETER:
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), true, false));
        return;
    }
    super.notifyChanged(notification);
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that
   * can be created under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
    super.collectNewChildDescriptors(newChildDescriptors, object);

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createCollectionLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createBooleanLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createIntegerLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createFloatingLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createStringLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createBinaryOperation()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createUnaryOperation()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createEnumerationLiteralReference()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createBuiltInConstant()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BUILT_IN_FUNCTION__PARAMETER,
                    XcatalogueFactory.eINSTANCE.createBuiltInFunction()));
  }

}
