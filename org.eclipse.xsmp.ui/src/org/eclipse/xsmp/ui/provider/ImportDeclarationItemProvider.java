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
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.ImportDeclaration} object.
 *
 * @since 2.7
 */
public class ImportDeclarationItemProvider extends GenericItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemStyledLabelProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public ImportDeclarationItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
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

      addImportedTypePropertyDescriptor(object);
      addImportedNamespacePropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Imported Type feature.
   */
  protected void addImportedTypePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_ImportDeclaration_importedType_feature"),
            getString("_UI_PropertyDescriptor_description",
                    "_UI_ImportDeclaration_importedType_feature", "_UI_ImportDeclaration_type"),
            XcataloguePackage.Literals.IMPORT_DECLARATION__IMPORTED_TYPE, true, false, true, null,
            null, null));
  }

  /**
   * This adds a property descriptor for the Imported Namespace feature.
   */
  protected void addImportedNamespacePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(
            ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
            getResourceLocator(), getString("_UI_ImportDeclaration_importedNamespace_feature"),
            getString("_UI_PropertyDescriptor_description",
                    "_UI_ImportDeclaration_importedNamespace_feature",
                    "_UI_ImportDeclaration_type"),
            XcataloguePackage.Literals.IMPORT_DECLARATION__IMPORTED_NAMESPACE, true, false, true,
            null, null, null));
  }

  /**
   * This returns the label text for the adapted class.
   */
  @Override
  public String getText(Object object)
  {
    return ((StyledString) getStyledText(object)).getString();
  }

  /**
   * This returns the label styled text for the adapted class.
   */
  @Override
  public Object getStyledText(Object object)
  {
    return new StyledString(getString("_UI_ImportDeclaration_type"));
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
    super.notifyChanged(notification);
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
