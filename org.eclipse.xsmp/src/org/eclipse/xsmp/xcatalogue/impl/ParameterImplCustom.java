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
package org.eclipse.xsmp.xcatalogue.impl;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.documentation.TagElement;
import org.eclipse.xsmp.documentation.TextElement;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.ParameterDirectionKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class ParameterImplCustom extends ParameterImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public ParameterDirectionKind getDirection()
  {
    if (eContainingFeature() == XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER)
    {
      return ParameterDirectionKind.RETURN;
    }
    return super.getDirection();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName()
  {

    var name = super.getName();
    if (name == null
            && eContainingFeature() == XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER)
    {
      name = "return";
    }
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDirection(ParameterDirectionKind newDirection)
  {
    if (eContainingFeature() != XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER
            && ParameterDirectionKind.RETURN != newDirection)
    {
      super.setDirection(newDirection);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  protected <T> T getFeature(EStructuralFeature feature, Object defaultValue)
  {
    return (T) defaultValue;
  }

  @Override
  protected void setFeature(EAttribute feature, Object value)
  {
  }

  @Override
  public String getDescription()
  {
    final var doc = ((NamedElement) eContainer()).getMetadatum().getXsmpcatdoc();

    if (eContainingFeature() == XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER)
    {
      return doc.tags().stream().filter(t -> "@return".equals(t.getTagName()))
              .map(t -> printFragment(t.fragments())).findFirst().orElse(null);
    }
    return doc.tags().stream()
            .filter(t -> "@param".equals(t.getTagName()) && t.fragments().size() > 1
                    && t.fragments().get(0).getText().equals(name))
            .map(t -> t.fragments().stream().skip(1).map(TextElement::getText)
                    .collect(Collectors.joining(System.lineSeparator())))
            .findFirst().orElse(null);

  }

  @Override
  public void setDescription(String newDescription)
  {
    final var doc = ((NamedElement) eContainer()).getMetadatum().getXsmpcatdoc();

    if (eContainingFeature() == XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER)
    {
      var tag = doc.tags().stream().filter(t -> "@return".equals(t.getTagName())).findFirst()
              .orElse(null);
      if (newDescription == null || newDescription.isEmpty())
      {
        doc.tags().remove(tag);
      }
      else
      {
        if (tag == null)
        {
          tag = new TagElement(-1, "@return");
          doc.tags().add(tag);
        }
        else
        {
          tag.fragments().clear();
        }

        for (final String line : newDescription.split(System.lineSeparator()))
        {
          tag.fragments().add(new TextElement(-1, line));
        }
      }
    }
    else
    {
      var tag = doc
              .tags().stream().filter(t -> "@param".equals(t.getTagName())
                      && !t.fragments().isEmpty() && t.fragments().get(0).getText().equals(name))
              .findFirst().orElse(null);
      if (newDescription == null || newDescription.isEmpty())
      {
        doc.tags().remove(tag);
      }
      else
      {
        if (tag == null)
        {
          tag = new TagElement(-1, "@param");
          doc.tags().add(tag);
        }
        else
        {
          tag.fragments().clear();
        }
        tag.fragments().add(new TextElement(-1, name));
        for (final String line : newDescription.split(System.lineSeparator()))
        {
          tag.fragments().add(new TextElement(-1, line));
        }
      }
    }
    ((NamedElement) eContainer()).getMetadatum().setDocumentation(doc.toString());
  }

} // ParameterImplCustom
