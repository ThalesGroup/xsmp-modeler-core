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
import org.eclipse.xsmp.tool.smp.core.types.TypesFactory;
import org.eclipse.xsmp.tool.smp.smdl.catalogue.CatalogueFactory;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Container;
import org.eclipse.xsmp.xcatalogue.EntryPoint;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.EventSink;
import org.eclipse.xsmp.xcatalogue.EventSource;
import org.eclipse.xsmp.xcatalogue.EventType;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.Model;
import org.eclipse.xsmp.xcatalogue.Namespace;
import org.eclipse.xsmp.xcatalogue.NativeType;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Reference;
import org.eclipse.xsmp.xcatalogue.Service;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.ValueReference;
import org.eclipse.xsmp.xcatalogue.util.XcatalogueSwitch;

final class ElementCreator extends XcatalogueSwitch<EObject>
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
  public EObject caseClass(final org.eclipse.xsmp.xcatalogue.Class object)
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
  public EObject caseException(final org.eclipse.xsmp.xcatalogue.Exception object)
  {
    return TypesFactory.eINSTANCE.createException();
  }

  @Override
  public EObject caseField(final Field object)
  {
    return TypesFactory.eINSTANCE.createField();
  }

  @Override
  public EObject caseFloat(final org.eclipse.xsmp.xcatalogue.Float object)
  {
    return TypesFactory.eINSTANCE.createFloat();
  }

  @Override
  public EObject caseInteger(final org.eclipse.xsmp.xcatalogue.Integer object)
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
  public EObject caseString(final org.eclipse.xsmp.xcatalogue.String object)
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