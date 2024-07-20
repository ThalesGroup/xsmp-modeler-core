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
package org.eclipse.xsmp.tool.smp.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;

public class SmpExtendedMetaData extends BasicExtendedMetaData
{
  private static final String NAMESPACE_OLD_CATALOGUE = "http://www.esa.int/2008/02/Smdl/Catalogue";

  private static final String NAMESPACE_OLD_TYPES = "http://www.esa.int/2008/02/Core/Types";

  private static final String NAMESPACE_OLD_ELEMENTS = "http://www.esa.int/2008/02/Core/Elements";

  private static final String NAMESPACE_CATALOGUE = "http://www.ecss.nl/smp/2019/Smdl/Catalogue";

  private static final String NAMESPACE_TYPES = "http://www.ecss.nl/smp/2019/Core/Types";

  private static final String NAMESPACE_ELEMENTS = "http://www.ecss.nl/smp/2019/Core/Elements";

  private boolean isOldNamespace(String namespace)
  {
    return namespace != null && (NAMESPACE_OLD_CATALOGUE.equals(namespace)
            || NAMESPACE_OLD_TYPES.equals(namespace) || NAMESPACE_OLD_ELEMENTS.equals(namespace));
  }

  private String computedNamespace(String namespace)
  {
    if (namespace == null)
    {
      return null;
    }
    if (NAMESPACE_OLD_CATALOGUE.equals(namespace))
    {
      namespace = NAMESPACE_CATALOGUE;
    }
    else if (NAMESPACE_OLD_TYPES.equals(namespace))
    {
      namespace = NAMESPACE_TYPES;
    }
    else if (NAMESPACE_OLD_ELEMENTS.equals(namespace))
    {
      namespace = NAMESPACE_ELEMENTS;
    }
    return namespace;
  }

  @Override
  public EPackage getPackage(String namespace)
  {
    return super.getPackage(computedNamespace(namespace));
  }

  @Override
  public String getNamespace(EPackage ePackage)
  {
    var namespace = super.getNamespace(ePackage);
    if (isOldNamespace(namespace))
    {
      namespace = computedNamespace(namespace);
      ePackage.setNsURI(namespace);
    }
    return namespace;
  }

  @Override
  public String getNamespace(EClassifier eClassifier)
  {
    return computedNamespace(super.getNamespace(eClassifier));
  }

  @Override
  public String getNamespace(EStructuralFeature eStructuralFeature)
  {
    var namespace = super.getExtendedMetaData(eStructuralFeature).getNamespace();
    if (isOldNamespace(namespace))
    {
      namespace = computedNamespace(namespace);
      super.getExtendedMetaData(eStructuralFeature).setNamespace(namespace);
    }
    return namespace;
  }

  @Override
  protected String getPackageNamespace(EStructuralFeature eStructuralFeature)
  {
    return computedNamespace(super.getPackageNamespace(eStructuralFeature));
  }

  @Override
  public String basicGetNamespace(EStructuralFeature eStructuralFeature)
  {
    return computedNamespace(super.basicGetNamespace(eStructuralFeature));
  }

  @Override
  public EStructuralFeature getElement(String namespace, String name)
  {
    return super.getElement(computedNamespace(namespace), name);
  }

  @Override
  public EStructuralFeature getElement(EClass eClass, String namespace, String name)
  {
    return super.getElement(eClass, computedNamespace(namespace), name);
  }

  @Override
  public EStructuralFeature demandFeature(String namespace, String name, boolean isElement,
          boolean isReference)
  {
    return super.demandFeature(computedNamespace(namespace), name, isElement, isReference);
  }

  @Override
  public EStructuralFeature demandFeature(String namespace, String name, boolean isElement)
  {
    return super.demandFeature(computedNamespace(namespace), name, isElement);
  }

  @Override
  public EClassifier demandType(String namespace, String name)
  {
    return super.demandType(computedNamespace(namespace), name);
  }

  @Override
  public EPackage demandPackage(String namespace)
  {
    return super.demandPackage(computedNamespace(namespace));
  }

  @Override
  public boolean matches(String wildcard, String namespace)
  {
    return super.matches(wildcard, computedNamespace(namespace));
  }

  @Override
  public boolean matches(List<String> wildcards, String namespace)
  {
    return super.matches(wildcards, computedNamespace(namespace));
  }

  @Override
  public EStructuralFeature getElementWildcardAffiliation(EClass eClass, String namespace,
          String name)
  {
    return super.getElementWildcardAffiliation(eClass, computedNamespace(namespace), name);
  }

  @Override
  public EStructuralFeature getAttributeWildcardAffiliation(EClass eClass, String namespace,
          String name)
  {
    return super.getAttributeWildcardAffiliation(eClass, computedNamespace(namespace), name);
  }

  @Override
  public EStructuralFeature getAttribute(EClass eClass, String namespace, String name)
  {
    return super.getAttribute(eClass, computedNamespace(namespace), name);
  }

  @Override
  public EStructuralFeature getLocalAttribute(EClass eClass, String namespace, String name)
  {
    return super.getLocalAttribute(eClass, computedNamespace(namespace), name);
  }

  @Override
  public EStructuralFeature getAttribute(String namespace, String name)
  {
    return super.getAttribute(computedNamespace(namespace), name);
  }

  @Override
  public EClassifier getType(String namespace, String name)
  {
    return super.getType(computedNamespace(namespace), name);
  }

  @Override
  public void setNamespace(EStructuralFeature eStructuralFeature, String namespace)
  {
    super.setNamespace(eStructuralFeature, computedNamespace(namespace));
  }

}
