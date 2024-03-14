/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.AbstractInstance;
import org.eclipse.xsmp.model.xsmp.AbstractPath;
import org.eclipse.xsmp.model.xsmp.Array;
import org.eclipse.xsmp.model.xsmp.Component;
import org.eclipse.xsmp.model.xsmp.ComponentTypeReference;
import org.eclipse.xsmp.model.xsmp.Configuration;
import org.eclipse.xsmp.model.xsmp.Connection;
import org.eclipse.xsmp.model.xsmp.Container;
import org.eclipse.xsmp.model.xsmp.EventSink;
import org.eclipse.xsmp.model.xsmp.EventSource;
import org.eclipse.xsmp.model.xsmp.Factory;
import org.eclipse.xsmp.model.xsmp.FactoryTypeReference;
import org.eclipse.xsmp.model.xsmp.Field;
import org.eclipse.xsmp.model.xsmp.Instance;
import org.eclipse.xsmp.model.xsmp.InstanceDeclaration;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers;
import org.eclipse.xsmp.model.xsmp.Path;
import org.eclipse.xsmp.model.xsmp.Property;
import org.eclipse.xsmp.model.xsmp.Reference;
import org.eclipse.xsmp.model.xsmp.Simulator;
import org.eclipse.xsmp.model.xsmp.StringPath;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.TypeReference;
import org.eclipse.xsmp.model.xsmp.UuidTypeReference;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.util.Int64;
import org.eclipse.xsmp.util.PrimitiveType;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ComposedChecks;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class contains custom validation rules.
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@Singleton
@ComposedChecks(validators = {XsmpasbUniqueElementValidator.class })
public class XsmpasbValidator extends AbstractXsmpasbValidator
{

  @Inject
  protected IScopeProvider scopeProvider;

  protected NamedElement getTarget(AbstractPath path)
  {
    if (path instanceof final Path p)
    {
      final var segment = p.getSegment();
      if (segment == null)
      {
        return EcoreUtil2.getContainerOfType(path, NamedElementWithMembers.class);
      }
      return segment;
    }

    return null;
  }

  protected Type getType(TypeReference type)
  {
    if (type instanceof final ComponentTypeReference ref)
    {
      return ref.getComponent();
    }
    if (type instanceof final FactoryTypeReference factory)
    {
      return getType(factory.getFactory().getType());
    }
    return null;
  }

  @Check
  protected void checkConfiguration(Configuration elem)
  {

    final var field = XsmpUtil.getLastSegment(elem.getField());

    if (field instanceof final Path p)
    {
      final var segment = p.getSegment();

      if (segment == null || segment.eIsProxy())
      {
        // ignore
      }
      else if (segment instanceof final Field f)
      {
        var type = f.getType();
        for (final var index : p.getIndex())
        {
          if (type instanceof final Array ar)
          {
            // check size
            type = ar.getItemType();
            final var size = safeExpression(ar.getSize(), PrimitiveType::int64Value, Int64.ZERO,
                    Int64.MAX_VALUE);
            safeExpression(index, v -> v, Int64.ZERO, size.subtract(Int64.ONE));

          }
          else
          {
            error("is not an array", p, XsmpPackage.Literals.PATH__SEGMENT);
          }
        }

        checkExpression(type, elem.getValue());
      }
      else if (elem instanceof final Property property)
      {
        checkExpression(property.getType(), elem.getValue());
      }
      else
      {
        error("Only Fields and Properties can be configured",
                XsmpPackage.Literals.CONFIGURATION__FIELD);
      }

    }
    else if (!(field instanceof StringPath))
    {
      error("Only Fields and Properties can be configured",
              XsmpPackage.Literals.CONFIGURATION__FIELD);
    }
  }

  boolean areCompatibles(Type t1, Type t2)
  {
    // TODO check types are equivalent
    return t1 == t2;
  }

  @Check
  protected void checkConnection(Connection elem)
  {

    final var lastFrom = XsmpUtil.getLastSegment(elem.getFrom());
    final var from = getTarget(lastFrom);

    final var lastTo = XsmpUtil.getLastSegment(elem.getTo());
    final var to = getTarget(lastTo);

    if (from == null || to == null || from.eIsProxy() || to.eIsProxy())
    {
      // ignore opaque connection
    }
    // output to input
    else if (from instanceof final Field outputField && to instanceof final Field inputField)
    {
      // check types are compatibles

      if (!outputField.isOutput())
      {
        error("The field is not an output", XsmpPackage.Literals.CONNECTION__FROM);
      }

      if (!inputField.isInput())
      {
        error("The field is not an input", XsmpPackage.Literals.CONNECTION__TO);
      }

      if (!areCompatibles(outputField.getType(), inputField.getType()))
      {
        error("Cannot connect a Field of Type " + outputField.getType().getName() + " to "
                + inputField.getType().getName() + ".", XsmpPackage.Literals.CONNECTION__TO);
      }
    }
    // event source to event sink
    else if (from instanceof final EventSource source && to instanceof final EventSink sink)
    {
      // check types are compatibles

      if (source.getType() != sink.getType())
      {
        error("Invalid EventType. Expecting " + source.getType().getName() + ".",
                XsmpPackage.Literals.CONNECTION__TO);
      }
    }

    // reference to instance
    else if (from instanceof final Reference reference
            && to instanceof final AbstractInstance instance)
    {
      // check types are compatibles
      if (!xsmpUtil.isBaseOf(reference.getInterface(), getType(instance.getType())))
      {
        error("The instance does not implements the Interface " + reference.getInterface().getName()
                + ".", XsmpPackage.Literals.CONNECTION__TO);
      }

    }
    else
    {
      error("Cannot connect " + from.eClass().getName() + " to " + to.eClass().getName() + ".",
              XsmpPackage.Literals.CONNECTION__TO);

    }

  }

  protected void checkDuplicate(NamedElementWithMembers element, TypeReference type)
  {

    EObject reference;
    if (type instanceof final ComponentTypeReference ref)
    {
      reference = ref.getComponent();
    }
    else if (type instanceof final FactoryTypeReference ref)
    {
      reference = ref.getFactory();
    }
    else
    {
      reference = null;
    }

    if (reference != null && !reference.eIsProxy())
    {
      final var scope = scopeProvider.getScope(reference, XsmpPackage.Literals.PATH__SEGMENT);
      checkDuplicate(element, scope);
    }

  }

  protected void checkDuplicate(NamedElementWithMembers element, IScope scope)
  {

    final var rootFqn = xsmpUtil.fqn(element).getSegmentCount();
    for (var member : element.getMember())
    {

      if (member instanceof final InstanceDeclaration declaration)
      {
        member = declaration.getInstance();
      }

      if (member instanceof final NamedElement elem)
      {

        final var shadowed = scope.getSingleElement(xsmpUtil.fqn(elem).skipFirst(rootFqn));
        if (shadowed != null)
        {
          error(elem.eClass().getName() + " '" + elem.getName() + "' shadows "
                  + shadowed.getEClass().getName() + " '" + shadowed.getQualifiedName() + "'",
                  member, XsmpPackage.Literals.NAMED_ELEMENT__NAME);
        }
      }
    }

  }

  protected Component getBaseComponent(TypeReference base)
  {
    while (base != null)
    {
      if (base instanceof final ComponentTypeReference ref)
      {
        return ref.getComponent();
      }
      if (!(base instanceof final FactoryTypeReference ref))
      {
        return null;
      }
      base = ref.getFactory().getType();
    }

    return null;
  }

  private static final Pattern containerPattern = Pattern.compile(".*\\$\\w+");

  @Check
  protected void checkInstanceDeclaration(InstanceDeclaration declaration)
  {
    final var path = XsmpUtil.getLastSegment(declaration.getContainer());

    if (path instanceof final Path p)
    {
      final var segment = p.getSegment();
      if (segment instanceof final Container container)
      {

        final var base = container.getType();
        final var instance = declaration.getInstance();

        final var derived = getBaseComponent(instance.getType());

        if (derived != null && !xsmpUtil.isBaseOf(base, derived))
        {

          // The Instance name does not implements the interface '' required by the Container ''
          error("The Instance '" + instance.getName() + "' does not implements the interface '"
                  + xsmpUtil.fqn(base) + "' required by the Container '" + container.getName()
                  + "'", XsmpPackage.Literals.INSTANCE_DECLARATION__INSTANCE);
        }

      }
      else if (segment != null)
      {
        error("Expecting a Container, got " + segment.eClass().getName(),
                XsmpPackage.Literals.INSTANCE_DECLARATION__CONTAINER);
      }
      else
      {

        error("Expecting a Container, got "
                + EcoreUtil2.getContainerOfType(path, NamedElement.class).eClass().getName(),
                XsmpPackage.Literals.INSTANCE_DECLARATION__CONTAINER);
      }
    }

    else if (path instanceof final StringPath p)
    {
      // check finish with :[a-zA-Z]\w* -> (ID(.ID)*)?$ID
      final var name = p.getName().substring(1, p.getName().length() - 1);
      if (!containerPattern.matcher(name).matches())
      {
        error("A Container path must match '(ID.)*:ID'.",
                XsmpPackage.Literals.INSTANCE_DECLARATION__CONTAINER);
      }
    }

  }

  @Check
  protected void checkInstance(Instance instance)
  {
    checkDuplicate(instance, instance.getType());
  }

  @Check
  protected void checkFactory(Factory factory)
  {
    checkDuplicate(factory, factory.getType());
  }

  @Check
  protected void checkUuidTypeReference(UuidTypeReference type)
  {
    final var uuid = XsmpUtil.stringLiteralToString(type.getUuid());
    // check that the UUID is valid
    if (!UUID_PATTERN.matcher(uuid).matches())
    {
      error("The UUID is invalid.", XsmpPackage.Literals.UUID_TYPE_REFERENCE__UUID);
    }
  }

  private static final Pattern pathPattern = Pattern
          .compile("\\$?[\\w]+(?:\\[\\d+\\])*(?:\\.\\$?[\\w]+(?:\\[\\d+\\])*)*");

  @Check
  protected void checkStringPath(StringPath path)
  {
    final var segment = XsmpUtil.stringLiteralToString(path.getName());
    // check that the path is valid
    if (!pathPattern.matcher(segment).matches())
    {
      error("The Path is invalid.", XsmpPackage.Literals.STRING_PATH__NAME);
    }
  }

  @Check
  protected void checkSimulator(Simulator simulator)
  {

    final Map<QualifiedName, IEObjectDescription> visited = new HashMap<>();

    // check that we do not shadow a base simulator
    final var size = simulator.getBase().size();
    for (var i = 0; i < size; ++i)
    {
      final var base = simulator.getBase().get(i);
      if (!base.eIsProxy())
      {

        final var scope = scopeProvider.getScope(base, XsmpPackage.Literals.PATH__SEGMENT);

        for (final var elem : scope.getAllElements())
        {

          if (elem.getEClass() != XsmpPackage.Literals.CONTAINER)
          {

            final var ori = visited.get(elem.getName());
            if (ori != null)
            {
              if (ori.getEObjectURI() != elem.getEObjectURI())
              {
                error(elem.getEClass().getName() + " `" + elem.getQualifiedName() + "` shadows "
                        + ori.getEClass().getName() + " `" + ori.getQualifiedName()
                        + "' defined by a previous base.", XsmpPackage.Literals.SIMULATOR__BASE, i);
              }
            }
            else
            {
              visited.put(elem.getName(), elem);
            }

          }

        }
        // check no conflict between the current simulator and the base
        checkDuplicate(simulator, scope);
      }
    }

    // check that epoch and missionStart are DateTime expression
    if (simulator.getEpoch() != null)
    {
      safeExpression(simulator.getEpoch(), PrimitiveType::dateTimeValue);
    }
    if (simulator.getMissionStart() != null)
    {
      safeExpression(simulator.getMissionStart(), PrimitiveType::dateTimeValue);
    }

  }

}
