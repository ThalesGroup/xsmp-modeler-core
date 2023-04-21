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
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.xcatalogue.BinaryOperation;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.BinaryOperation} object.
 */
public class BinaryOperationItemProvider extends ExpressionItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public BinaryOperationItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addFeaturePropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Feature feature.
   */
  protected void addFeaturePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_BinaryOperation_feature_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_BinaryOperation_feature_feature",
                    "_UI_BinaryOperation_type"),
            XcataloguePackage.Literals.BINARY_OPERATION__FEATURE, true, false, false,
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
      childrenFeatures.add(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND);
      childrenFeatures.add(XcataloguePackage.Literals.BINARY_OPERATION__RIGHT_OPERAND);
    }
    return childrenFeatures;
  }

  /**
   * This returns the label styled text for the adapted class.
   */
  @Override
  public Object getStyledText(Object object)
  {
    final var label = ((BinaryOperation) object).getFeature();
    final var styledLabel = new StyledString();
    if (label == null || label.length() == 0)
    {
      styledLabel.append(getString("_UI_BinaryOperation_type"),
              StyledString.Style.QUALIFIER_STYLER);
    }
    else
    {
      styledLabel.append(getString("_UI_BinaryOperation_type"), StyledString.Style.QUALIFIER_STYLER)
              .append(" " + label);
    }
    return styledLabel;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(BinaryOperation.class))
    {
      case XcataloguePackage.BINARY_OPERATION__FEATURE:
        fireNotifyChanged(
                new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case XcataloguePackage.BINARY_OPERATION__LEFT_OPERAND:
      case XcataloguePackage.BINARY_OPERATION__RIGHT_OPERAND:
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
            .add(createChildParameter(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND,
                    XcatalogueFactory.eINSTANCE.createCollectionLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND,
                    XcatalogueFactory.eINSTANCE.createBooleanLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND,
                    XcatalogueFactory.eINSTANCE.createIntegerLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND,
                    XcatalogueFactory.eINSTANCE.createFloatingLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND,
                    XcatalogueFactory.eINSTANCE.createStringLiteral()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND,
                    XcatalogueFactory.eINSTANCE.createBinaryOperation()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND,
                    XcatalogueFactory.eINSTANCE.createUnaryOperation()));

    newChildDescriptors
            .add(createChildParameter(XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND,
                    XcatalogueFactory.eINSTANCE.createNamedElementReference()));

  }

  /**
   * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
   */
  @Override
  public String getCreateChildText(Object owner, Object feature, Object child,
          Collection< ? > selection)
  {
    final var childFeature = feature;
    final var childObject = child;

    final var qualify = childFeature == XcataloguePackage.Literals.BINARY_OPERATION__LEFT_OPERAND
            || childFeature == XcataloguePackage.Literals.BINARY_OPERATION__RIGHT_OPERAND;

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
