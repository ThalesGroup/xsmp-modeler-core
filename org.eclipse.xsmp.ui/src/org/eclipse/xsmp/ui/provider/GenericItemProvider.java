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
import java.util.UUID;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyCommand.Helper;
import org.eclipse.emf.edit.command.InitializeCopyCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.xsmp.ui.XsmpUIPlugin;
import org.eclipse.xsmp.ui.contentassist.XsmpcatReferenceProposalCreator;
import org.eclipse.xsmp.ui.labeling.XsmpcatLabelProvider;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xtext.ui.IImageHelper;

import com.google.common.collect.Iterators;
import com.google.inject.Inject;

public class GenericItemProvider extends ItemProviderAdapter implements IItemLabelProvider
{
  private static final Logger log = Logger.getLogger(GenericItemProvider.class);

  @Inject
  protected XsmpcatReferenceProposalCreator referenceProposalCreator;

  @Inject
  protected IImageHelper imageHelper;

  public GenericItemProvider(AdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  /**
   * Use the Scope provider to retrieve the EReference list
   */
  @Override
  protected ItemPropertyDescriptor createItemPropertyDescriptor(AdapterFactory adapterFactory,
          ResourceLocator resourceLocator, String displayName, String description,
          EStructuralFeature feature, boolean isSettable, boolean multiLine, boolean sortChoices,
          Object staticImage, String category, String[] filterFlags, Object propertyEditorFactory)
  {
    return new ItemPropertyDescriptor(adapterFactory, resourceLocator, displayName, description,
            feature, isSettable, multiLine, sortChoices, staticImage, category, filterFlags,
            propertyEditorFactory) {

      /**
       * {@inheritDoc}
       */
      @Override
      protected Collection< ? > getComboBoxObjects(Object object)
      {
        if (object instanceof EObject)
        {

          final var eObject = (EObject) object;

          final var eClass = eObject.eClass();
          if (parentReferences != null)
          {
            final Collection<Object> result = new UniqueEList<>();
            for (final EReference element : parentReferences)
            {
              result.addAll(getReachableObjectsOfType(eObject, eClass.getFeatureType(element)));
            }
            return result;
          }

          if (feature != null)
          {
            final var eGenericType = eClass.getFeatureType(feature);
            if (feature instanceof EReference)
            {
              final var reference = (EReference) feature;
              // check if reference.isContainment();

              final var elements = referenceProposalCreator.getReachableObjects(eObject, reference);

              final List<EObject> candidates = new ArrayList<>();
              Iterators.addAll(candidates, elements.iterator());
              if (!feature.isMany() && !candidates.contains(null))
              {
                candidates.add(null);
              }
              return candidates;
            }
            final var eType = eGenericType.getERawType();
            if (eType instanceof EEnum)
            {
              final var eEnum = (EEnum) eType;
              final List<Enumerator> enumerators = new ArrayList<>();
              for (final EEnumLiteral eEnumLiteral : eEnum.getELiterals())
              {
                enumerators.add(eEnumLiteral.getInstance());
              }
              return enumerators;
            }
            final var eDataType = (EDataType) eType;
            final var enumeration = ExtendedMetaData.INSTANCE.getEnumerationFacet(eDataType);
            if (!enumeration.isEmpty())
            {
              final List<Object> enumerators = new ArrayList<>();
              for (final String enumerator : enumeration)
              {
                enumerators.add(EcoreUtil.createFromString(eDataType, enumerator));
              }
              return enumerators;
            }
            for (var baseType = ExtendedMetaData.INSTANCE
                    .getBaseType(eDataType); baseType != null; baseType = ExtendedMetaData.INSTANCE
                            .getBaseType(baseType))
            {
              if (baseType instanceof EEnum)
              {
                final var eEnum = (EEnum) baseType;
                final List<Enumerator> enumerators = new ArrayList<>();
                enumerators.add(null);
                for (final EEnumLiteral eEnumLiteral : eEnum.getELiterals())
                {
                  enumerators.add(eEnumLiteral.getInstance());
                }
                return enumerators;
              }
            }
          }
        }

        return null;
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected CommandParameter createChildParameter(Object feature, Object child)
  {

    if (child instanceof NamedElement)
    {
      ((NamedElement) child).setName("element_name");
    }

    // Generate a random UUID for a new Type
    if (child instanceof Type)
    {
      ((Type) child).setUuid(UUID.randomUUID().toString());
    }
    return super.createChildParameter(feature, child);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Command createInitializeCopyCommand(EditingDomain domain, EObject owner, Helper helper)
  {
    return new InitializeCopyCommand(domain, owner, helper) {
      /**
       * {@inheritDoc}
       */
      @Override
      public void doExecute()
      {
        super.doExecute();
        if (copy instanceof Type)
        {
          ((Type) copy).setUuid(UUID.randomUUID().toString());

        }
        copy.eAllContents().forEachRemaining(obj -> {
          if (obj instanceof Type)
          {
            ((Type) obj).setUuid(UUID.randomUUID().toString());
          }
        });

      }
    };
  }

  protected boolean canContain(EObject owner, EStructuralFeature feature, Object value)
  {

    return true;

  }

  protected boolean canContain(EObject owner, EStructuralFeature feature,
          Collection< ? > collection)
  {

    return collection.stream().allMatch(value -> this.canContain(owner, feature, value));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Command createSetCommand(EditingDomain domain, EObject owner,
          EStructuralFeature feature, Object value, int index)
  {
    if (canContain(owner, feature, value))
    {
      return super.createSetCommand(domain, owner, feature, value, index);
    }
    return UnexecutableCommand.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Command createSetCommand(EditingDomain domain, EObject owner,
          EStructuralFeature feature, Object value)
  {
    if (canContain(owner, feature, value))
    {
      return super.createSetCommand(domain, owner, feature, value);
    }
    return UnexecutableCommand.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Command createMoveCommand(EditingDomain domain, EObject owner,
          EStructuralFeature feature, Object value, int index)
  {
    if (canContain(owner, feature, value))
    {
      return super.createMoveCommand(domain, owner, feature, value, index);
    }
    return UnexecutableCommand.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Command createAddCommand(EditingDomain domain, EObject owner,
          EStructuralFeature feature, Collection< ? > collection, int index)
  {
    if (canContain(owner, feature, collection))
    {
      return super.createAddCommand(domain, owner, feature, collection, index);
    }
    return UnexecutableCommand.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Command createReplaceCommand(EditingDomain domain, EObject owner,
          EStructuralFeature feature, Object value, Collection< ? > collection)
  {
    if (canContain(owner, feature, collection))
    {
      return super.createReplaceCommand(domain, owner, feature, value, collection);
    }
    return UnexecutableCommand.INSTANCE;
  }

  @Inject
  private XsmpcatLabelProvider labelProvider;

  @Override
  public final Object getImage(Object object)
  {
    try
    {
      return overlayImage(object, labelProvider.getImage(object));
    }
    catch (final Exception e)
    {
      log.warn("Image not found", e);
      return null;
    }
  }

  @Override
  protected final Object overlayImage(Object object, Object image)
  {
    return super.overlayImage(object, image);
  }

  /**
   * Return the resource locator for this item provider's resources.
   */
  @Override
  public final ResourceLocator getResourceLocator()
  {
    return XsmpUIPlugin.getInstance();
  }

  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);
  }
}
