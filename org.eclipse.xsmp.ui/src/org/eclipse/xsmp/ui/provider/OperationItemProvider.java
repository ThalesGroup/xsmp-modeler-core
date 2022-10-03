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
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.xsmp.util.ElementUtil;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Operation} object.
 */
public class OperationItemProvider extends VisibilityElementItemProvider
{
  @Inject
  private ElementUtil elementUtil;

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
            getResourceLocator(), getString("_UI_Constructor_raisedException_feature"),
            getString("_UI_PropertyDescriptor_description",
                    "_UI_Constructor_raisedException_feature", "_UI_Constructor_type"),
            XcataloguePackage.Literals.OPERATION__RAISED_EXCEPTION, true, false, true, null, null,
            null));
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
      childrenFeatures.add(XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER);
      childrenFeatures.add(XcataloguePackage.Literals.OPERATION__PARAMETER);
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
   * This returns the label styled text for the adapted class.
   */
  @Override
  public StyledString getStyledText(Object object)
  {
    final var op = (Operation) object;
    final var styledLabel = super.getStyledText(object);
    styledLabel.append("(" + op.getParameter().stream().map(this::getParameterType)
            .collect(Collectors.joining(", ")) + ")");
    styledLabel.append(
            " : " + (op.getReturnParameter() == null ? "void"
                    : getParameterType(op.getReturnParameter())),
            StyledString.Style.DECORATIONS_STYLER);

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

    switch (notification.getFeatureID(Operation.class))
    {

      case XcataloguePackage.OPERATION__PARAMETER:
      case XcataloguePackage.OPERATION__RETURN_PARAMETER:
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
            .add(createChildParameter(XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER,
                    XcatalogueFactory.eINSTANCE.createParameter()));
    newChildDescriptors.add(createChildParameter(XcataloguePackage.Literals.OPERATION__PARAMETER,
            XcatalogueFactory.eINSTANCE.createParameter()));
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

  /**
   * This returns Operation.gif.
   */
  @Override
  public Object getImage(Object object)
  {
    final var ope = (Operation) object;
    if (ope.eContainer() != null)
    {
      return getResourceLocator().getImage("full/obj16/" + ope.eClass().getName() + "_"
              + ope.getRealVisibility().getLiteral() + ".png");
    }
    return getResourceLocator().getImage("full/obj16/" + ope.eClass().getName() + "_public.png");
  }

  protected String getParameterType(Parameter p)
  {
    final var label = new StringBuilder();
    if (elementUtil.isConst(p))
    {
      label.append("const ");
    }
    final var type = p.getType();
    if (type != null && !type.eIsProxy())
    {
      label.append(qualifiedNameProvider.getFullyQualifiedName(p.getType()).toString("::"));
    }

    switch (elementUtil.kind(p))
    {
      case BY_PTR:
        label.append("*");
        break;
      case BY_REF:
        label.append("&");
        break;
      default:
        break;
    }

    return label.toString();

  }

}
