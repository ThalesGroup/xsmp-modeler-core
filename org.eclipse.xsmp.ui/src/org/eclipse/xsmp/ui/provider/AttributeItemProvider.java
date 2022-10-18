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

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.Attribute}
 * object.
 */
public class AttributeItemProvider extends GenericItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemStyledLabelProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public AttributeItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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
      addValuePropertyDescriptor(object);
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
            getResourceLocator(), getString("_UI_Attribute_type_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Attribute_type_feature",
                    "_UI_Attribute_type"),
            XcataloguePackage.Literals.ATTRIBUTE__TYPE, true, false, true, null, null, null));
  }

  /**
   * This adds a property descriptor for the Value feature.
   */
  protected void addValuePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_Attribute_value_feature"),
            getString("_UI_PropertyDescriptor_description", "_UI_Attribute_value_feature",
                    "_UI_Attribute_type"),
            XcataloguePackage.Literals.ATTRIBUTE__VALUE, true, false, false, null, null, null));
  }

  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  /**
   * This returns the label text for the adapted class.
   */
  @Override
  public String getText(Object object)
  {

    final var attribute = (Attribute) object;
    final var type = attribute.getType();

    final var fqn = qualifiedNameProvider.getFullyQualifiedName(type);
    return fqn == null ? getString("_UI_Attribute_type") : fqn.toString("::");

  }

}
