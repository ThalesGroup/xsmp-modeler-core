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
package org.eclipse.xsmp.tool.smp.generator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.Array;
import org.eclipse.xsmp.model.xsmp.Association;
import org.eclipse.xsmp.model.xsmp.Attribute;
import org.eclipse.xsmp.model.xsmp.AttributeType;
import org.eclipse.xsmp.model.xsmp.Catalogue;
import org.eclipse.xsmp.model.xsmp.Constant;
import org.eclipse.xsmp.model.xsmp.Container;
import org.eclipse.xsmp.model.xsmp.EntryPoint;
import org.eclipse.xsmp.model.xsmp.Enumeration;
import org.eclipse.xsmp.model.xsmp.EnumerationLiteral;
import org.eclipse.xsmp.model.xsmp.EventSink;
import org.eclipse.xsmp.model.xsmp.EventSource;
import org.eclipse.xsmp.model.xsmp.EventType;
import org.eclipse.xsmp.model.xsmp.Field;
import org.eclipse.xsmp.model.xsmp.Interface;
import org.eclipse.xsmp.model.xsmp.Model;
import org.eclipse.xsmp.model.xsmp.Namespace;
import org.eclipse.xsmp.model.xsmp.NativeType;
import org.eclipse.xsmp.model.xsmp.Operation;
import org.eclipse.xsmp.model.xsmp.Parameter;
import org.eclipse.xsmp.model.xsmp.PrimitiveType;
import org.eclipse.xsmp.model.xsmp.Property;
import org.eclipse.xsmp.model.xsmp.Reference;
import org.eclipse.xsmp.model.xsmp.Service;
import org.eclipse.xsmp.model.xsmp.Structure;
import org.eclipse.xsmp.model.xsmp.ValueReference;
import org.eclipse.xsmp.model.xsmp.util.XsmpSwitch;
import org.eclipse.xsmp.tool.smp.core.types.TypesFactory;
import org.eclipse.xsmp.tool.smp.smdl.catalogue.CatalogueFactory;

final class ElementCreator extends XsmpSwitch<EObject>
{

  @Override
  public EObject caseArray(final Array object)
  {
    return TypesFactory.eINSTANCE.createArray();
  }

  @Override
  public EObject caseAssociation(final Association object)
  {
    return TypesFactory.eINSTANCE.createAssociation();
  }

  @Override
  public EObject caseAttribute(final Attribute object)
  {
    return TypesFactory.eINSTANCE.createAttribute();
  }

  @Override
  public EObject caseAttributeType(final AttributeType object)
  {
    return TypesFactory.eINSTANCE.createAttributeType();
  }

  @Override
  public EObject caseCatalogue(final Catalogue object)
  {
    return CatalogueFactory.eINSTANCE.createCatalogue();
  }

  @Override
  public EObject caseClass(final org.eclipse.xsmp.model.xsmp.Class object)
  {
    return TypesFactory.eINSTANCE.createClass();
  }

  @Override
  public EObject caseConstant(final Constant object)
  {
    return TypesFactory.eINSTANCE.createConstant();
  }

  @Override
  public EObject caseEnumeration(final Enumeration object)
  {
    return TypesFactory.eINSTANCE.createEnumeration();
  }

  @Override
  public EObject caseEnumerationLiteral(final EnumerationLiteral object)
  {
    return TypesFactory.eINSTANCE.createEnumerationLiteral();
  }

  @Override
  public EObject caseException(final org.eclipse.xsmp.model.xsmp.Exception object)
  {
    return TypesFactory.eINSTANCE.createException();
  }

  @Override
  public EObject caseField(final Field object)
  {
    return TypesFactory.eINSTANCE.createField();
  }

  @Override
  public EObject caseFloat(final org.eclipse.xsmp.model.xsmp.Float object)
  {
    return TypesFactory.eINSTANCE.createFloat();
  }

  @Override
  public EObject caseInteger(final org.eclipse.xsmp.model.xsmp.Integer object)
  {
    return TypesFactory.eINSTANCE.createInteger();
  }

  @Override
  public EObject caseNativeType(final NativeType object)
  {
    return TypesFactory.eINSTANCE.createNativeType();
  }

  @Override
  public EObject caseOperation(final Operation object)
  {
    return TypesFactory.eINSTANCE.createOperation();
  }

  @Override
  public EObject caseParameter(final Parameter object)
  {
    return TypesFactory.eINSTANCE.createParameter();
  }

  @Override
  public EObject casePrimitiveType(final PrimitiveType object)
  {
    return TypesFactory.eINSTANCE.createPrimitiveType();
  }

  @Override
  public EObject caseProperty(final Property object)
  {
    return TypesFactory.eINSTANCE.createProperty();
  }

  @Override
  public EObject caseString(final org.eclipse.xsmp.model.xsmp.String object)
  {
    return TypesFactory.eINSTANCE.createString();
  }

  @Override
  public EObject caseStructure(final Structure object)
  {
    return TypesFactory.eINSTANCE.createStructure();
  }

  @Override
  public EObject caseService(final Service object)
  {
    return CatalogueFactory.eINSTANCE.createService();
  }

  @Override
  public EObject caseModel(final Model object)
  {
    return CatalogueFactory.eINSTANCE.createModel();
  }

  @Override
  public EObject caseEventSink(final EventSink object)
  {
    return CatalogueFactory.eINSTANCE.createEventSink();
  }

  @Override
  public EObject caseEventSource(final EventSource object)
  {
    return CatalogueFactory.eINSTANCE.createEventSource();
  }

  @Override
  public EObject caseEventType(final EventType object)
  {
    return CatalogueFactory.eINSTANCE.createEventType();
  }

  @Override
  public EObject caseReference(final Reference object)
  {
    return CatalogueFactory.eINSTANCE.createReference();
  }

  @Override
  public EObject caseNamespace(final Namespace object)
  {
    return CatalogueFactory.eINSTANCE.createNamespace();
  }

  @Override
  public EObject caseInterface(final Interface object)
  {
    return CatalogueFactory.eINSTANCE.createInterface();
  }

  @Override
  public EObject caseContainer(final Container object)
  {
    return CatalogueFactory.eINSTANCE.createContainer();
  }

  @Override
  public EObject caseEntryPoint(final EntryPoint object)
  {
    return CatalogueFactory.eINSTANCE.createEntryPoint();
  }

  @Override
  public EObject caseValueReference(final ValueReference object)
  {
    return TypesFactory.eINSTANCE.createValueReference();
  }

}