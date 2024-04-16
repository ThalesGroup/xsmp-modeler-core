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
package org.eclipse.xsmp.profile.tas_mdk.validation;

import static org.eclipse.xsmp.profile.tas_mdk.validation.TasMdkIssueCodes.INVALID_NAMING_CONVENTION_PART;
import static org.eclipse.xsmp.profile.tas_mdk.validation.TasMdkIssueCodes.INVALID_VISIBILITY_PART;
import static org.eclipse.xsmp.profile.tas_mdk.validation.TasMdkIssueCodes.MISSING_DESCRIPTION_PART;

import org.eclipse.xsmp.model.xsmp.Catalogue;
import org.eclipse.xsmp.model.xsmp.Class;
import org.eclipse.xsmp.model.xsmp.Component;
import org.eclipse.xsmp.model.xsmp.EntryPoint;
import org.eclipse.xsmp.model.xsmp.EventSink;
import org.eclipse.xsmp.model.xsmp.EventSource;
import org.eclipse.xsmp.model.xsmp.EventType;
import org.eclipse.xsmp.model.xsmp.Field;
import org.eclipse.xsmp.model.xsmp.Model;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.Operation;
import org.eclipse.xsmp.model.xsmp.Parameter;
import org.eclipse.xsmp.model.xsmp.Reference;
import org.eclipse.xsmp.model.xsmp.Service;
import org.eclipse.xsmp.model.xsmp.SimpleType;
import org.eclipse.xsmp.model.xsmp.String;
import org.eclipse.xsmp.model.xsmp.VisibilityKind;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.profile.tas_mdk.AbstractTasMdkProfile;
import org.eclipse.xsmp.util.PrimitiveTypeKind;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.validation.AbstractXsmpContextValidator;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.validation.Check;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class contains custom validation rules. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@Singleton
public class TasMdkValidator extends AbstractXsmpContextValidator
{
  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  private XsmpUtil typeUtil;

  public static final QualifiedName tasMdkModelQfn = QualifiedName.create("TasMdk", "Model");

  public static final QualifiedName tasMdkServiceQfn = QualifiedName.create("TasMdk", "Service");

  @Override
  protected boolean isEnabledFor(IXsmpProjectConfig config)
  {
    return AbstractTasMdkProfile.PROFILE_ID.equals(config.getProfile());
  }

  @Check
  protected void checkField(Field n)
  {
    // String8 shall be forbidden
    if (typeUtil.getPrimitiveTypeKind(n.getType()) == PrimitiveTypeKind.STRING8
            && !(n.getType() instanceof String))
    {
      error("String8 type is forbidden for fields.", n, XsmpPackage.Literals.FIELD__TYPE);
    }

    switch (n.eContainer().eClass().getClassifierID())
    {
      case XsmpPackage.MODEL, XsmpPackage.SERVICE:
        if (n.getRealVisibility() == VisibilityKind.PUBLIC)
        {
          addIssue("A field cannot be public in Gram environment", n,
                  XsmpPackage.Literals.VISIBILITY_ELEMENT__VISIBILITY, INVALID_VISIBILITY_PART);
        }
        if (n.isInput() && n.isOutput())
        {
          error("A field cannot be an both an input and an output, it can be either an input or an output",
                  XsmpPackage.Literals.FIELD__INPUT);
        }
        else if (n.getName() != null)
        {

          // check naming convention of fields
          if (n.isInput() && !n.getName().startsWith("inp_"))
          {
            addIssue("Name of input field must start with inp_", n,
                    XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);

          }
          else if (n.isOutput() && !n.getName().startsWith("out_"))
          {
            addIssue("Name of output field must start with out_", n,
                    XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);
          }
          else if (!n.isOutput() && !n.isInput() && !n.getName().startsWith("fea_")
                  && !n.getName().startsWith("sta_"))
          {

            addIssue("Name of field feature must start with fea_ and a state must start with sta_",
                    n, XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);
          }
        }
        checkForMissingDescription(n);
        break;
      default:
        break;
    }
  }

  @Check
  protected void checkCatalogue(Catalogue n)
  {
    final var filename = n.eResource().getURI().trimFileExtension().lastSegment();
    if (!filename.equals(n.getName()))
    {
      addIssue(
              "Name of the " + n.eClass().getName() + " (" + n.getName()
                      + ") must be the same than the file name (" + filename + ")",
              n, XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);
    }
  }

  @Check
  protected void checkModel(Model n)
  {
    final var base = getRootBase(n);
    if (!base.eIsProxy()
            && !tasMdkModelQfn.equals(qualifiedNameProvider.getFullyQualifiedName(base)))
    {
      error("Model " + n.getName() + " must extends 'TasMdk.Model' or one of its sub class.",
              XsmpPackage.Literals.COMPONENT__BASE);
    }
  }

  @Check
  protected void checkService(Service n)
  {

    final var base = getRootBase(n);
    if (!base.eIsProxy()
            && !tasMdkServiceQfn.equals(qualifiedNameProvider.getFullyQualifiedName(base)))
    {
      error("Service " + n.getName() + " must extends 'TasMdk.Service' or one of its sub class.",
              XsmpPackage.Literals.COMPONENT__BASE);
    }
  }

  @Check
  protected void checkEventType(EventType n)
  {
    // check event do not have a type
    if (n.getEventArgs() != null)
    {
      error("In Gram environment, an Event shall only be of type Void",
              XsmpPackage.Literals.EVENT_TYPE__EVENT_ARGS);
    }
  }

  @Check
  protected void checkEventSource(EventSource n)
  {
    // check naming convention of event sources
    if (!n.getName().startsWith("eso_"))
    {

      addIssue("Name of event sources must start with eso_", n,
              XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);
    }
    // check event type of the event source is void
    if (n.getType() instanceof final EventType eventType && eventType.getEventArgs() != null)
    {
      error("An event source must be of type void", XsmpPackage.Literals.EVENT_SOURCE__TYPE);
    }
    checkForMissingDescription(n);
  }

  @Check
  protected void checkEventSink(EventSink n)
  {
    // check naming convention of event sink
    if (!n.getName().startsWith("esi_"))
    {

      addIssue("Name of event sink must start with esi_", n,
              XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);
    }
    checkForMissingDescription(n);
  }

  @Check
  protected void checkEntryPoint(EntryPoint n)
  {
    // check naming convention of entry points
    if (!n.getName().startsWith("ept_"))
    {

      addIssue("Name of entry point must start with ept_", n,
              XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);
    }
    checkForMissingDescription(n);
  }

  @Check
  protected void checkReference(Reference n)
  {
    // check naming convention of references
    if (!n.getName().startsWith("ref_"))
    {
      addIssue("Name of reference must start with ref_", n,
              XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);
    }
  }

  @Check
  protected void checkOperation(Operation n)
  {
    if (n.eContainer() instanceof Component)
    {
      if (n.getRealVisibility() == VisibilityKind.PUBLIC)
      {
        addIssue("An operation cannot be public in Gram environment", n,
                XsmpPackage.Literals.VISIBILITY_ELEMENT__VISIBILITY, INVALID_VISIBILITY_PART);
      }

      // check naming convention of operations
      if (!n.getName().startsWith("ope_"))
      {

        addIssue("Name of operation must start with ope_", n,
                XsmpPackage.Literals.NAMED_ELEMENT__NAME, INVALID_NAMING_CONVENTION_PART);
      }
      checkOperationIsPublicable(n);

      checkForMissingDescription(n);
    }
  }

  @Check
  protected void checkOperationIsPublicable(final Operation op)
  {

    // an operation is publicable if all parameters are publicables
    op.getParameter().forEach(this::checkParameterIsPublicable);
    if (op.getReturnParameter() != null)
    {
      checkParameterIsPublicable(op.getReturnParameter());
    }
  }

  @Check
  protected void checkParameterIsPublicable(final Parameter p)
  {

    final var type = p.getType();
    if (type instanceof SimpleType)
    {
      return;
    }

    error("Parameter of type " + type.eClass().getName() + " is not publicable.", p,
            XsmpPackage.Literals.PARAMETER__TYPE);
  }

  private void checkForMissingDescription(NamedElement e)
  {
    if (e.getDescription() == null || e.getDescription().isEmpty())
    {
      addIssue("The element description is missing.", e,
              XsmpPackage.Literals.NAMED_ELEMENT__DESCRIPTION, MISSING_DESCRIPTION_PART);
    }

  }

  @Check
  protected void checkClass(Class n)
  {
    error("Classes are not allowed in the Gram environment.", null);
  }

  public static Component getRootBase(Component component)
  {
    while (component.getBase() instanceof final Component cmp)
    {
      component = cmp;
    }
    return component;
  }
}
