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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.documentation.TagElement;
import org.eclipse.xsmp.documentation.TextElement;
import org.eclipse.xsmp.xcatalogue.Metadatum;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.RangeBasedDiagnostic;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public abstract class NamedElementImplCustom extends NamedElementImpl
{

  private class XsmpcatdocEList<E> extends EcoreEList.Dynamic<E>
  {
    @Override
    public boolean equals(Object object)
    {
      if (object instanceof XsmpcatdocEList< ? >)
      {
        return ((XsmpcatdocEList< ? >) object).tagName.equals(tagName) && super.equals(object);
      }
      return super.equals(object);
    }

    @Override
    public int hashCode()
    {
      return Objects.hash(tagName, super.hashCode());
    }

    /**
     *
     */
    public static final long serialVersionUID = -7859434301857045784L;

    private final String tagName;

    public XsmpcatdocEList(InternalEObject owner, EStructuralFeature eStructuralFeature)
    {
      super(owner, eStructuralFeature);
      this.tagName = "@" + eStructuralFeature.getName();
    }

    @Override
    protected void didAdd(int index, E newObject)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();

      final var filteredTags = xsmpcatdoc.tags().stream()
              .filter(t -> tagName.equals(t.getTagName())).collect(Collectors.toList());

      // compute the final tag position
      if (filteredTags.size() > index)
      {
        index = xsmpcatdoc.tags().indexOf(filteredTags.get(index));
      }
      else
      {
        index = xsmpcatdoc.tags().size();
      }
      final var tag = new TagElement(-1, tagName);
      tag.fragments().add(new TextElement(-1, serialize(eStructuralFeature, newObject)));
      // add the tag
      xsmpcatdoc.tags().add(index, tag);
      metadatum.setDocumentation(xsmpcatdoc.toString());

    }

    @Override
    protected void didRemove(int index, E oldObject)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();

      final var tag = xsmpcatdoc.tags().stream().filter(t -> tagName.equals(t.getTagName()))
              .skip(index).findFirst().orElse(null);
      xsmpcatdoc.tags().remove(tag);
      metadatum.setDocumentation(xsmpcatdoc.toString());

    }

    @Override
    protected void didClear(int size, Object[] oldObjects)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();

      xsmpcatdoc.tags().removeIf(t -> tagName.equals(t.getTagName()));

      metadatum.setDocumentation(xsmpcatdoc.toString());

    }

    @Override
    protected void didMove(int index, E movedObject, int oldIndex)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();

      final var filteredTags = xsmpcatdoc.tags().stream()
              .filter(t -> tagName.equals(t.getTagName())).collect(Collectors.toList());

      // compute the tag indexes
      oldIndex = xsmpcatdoc.tags().indexOf(filteredTags.get(oldIndex));
      index = xsmpcatdoc.tags().indexOf(filteredTags.get(index));

      // move the tag
      xsmpcatdoc.tags().move(index, oldIndex);

      metadatum.setDocumentation(xsmpcatdoc.toString());

    }

    @Override
    protected void didSet(int index, E newObject, E oldObject)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();

      xsmpcatdoc.tags().stream().filter(t -> tagName.equals(t.getTagName())).skip(index).findFirst()
              .ifPresent(tag -> {
                tag.fragments().clear();
                // set the value
                for (final String v : serialize(eStructuralFeature, newObject)
                        .split(System.lineSeparator()))
                {
                  tag.fragments().add(new TextElement(-1, v));
                }
              });

      metadatum.setDocumentation(xsmpcatdoc.toString());

    }

  }

  /**
   * Map in which to store the value of each feature define inside the DOCUMENTATION of the
   * Metadatum
   */
  protected Map<EStructuralFeature, Object> featureMap;

  protected Diagnostic createIssue(String message, int offset, int length, int error)
  {
    return new RangeBasedDiagnostic(error, message, this, offset, length, CheckType.FAST, null,
            null) {

    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    return featureID == XcataloguePackage.NAMED_ELEMENT__METADATUM || super.eIsSet(featureID);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription()
  {
    return getFeature(XcataloguePackage.Literals.NAMED_ELEMENT__DESCRIPTION, DESCRIPTION_EDEFAULT);
  }

  @SuppressWarnings("unchecked")
  protected <T> EList<T> getFeature(EStructuralFeature feature)
  {

    return (EList<T>) getFeatureMap().computeIfAbsent(feature,
            f -> new XsmpcatdocEList<T>(this, f));

  }

  protected EStructuralFeature getFeature(String name)
  {
    return "deprecated".equals(name) ? XcataloguePackage.Literals.NAMED_ELEMENT__DEPRECATED : null;
  }

  @SuppressWarnings("unchecked")
  protected <T> T getFeature(EStructuralFeature feature, Object defaultValue)
  {
    return (T) getFeatureMap().getOrDefault(feature, defaultValue);
  }

  /**
   * @return the feature map with parsed results
   */
  protected Map<EStructuralFeature, Object> getFeatureMap()
  {

    if (featureMap == null)
    {
      final var deliver = eDeliver();
      eSetDeliver(false);
      featureMap = parse();
      eSetDeliver(deliver);
    }
    return featureMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Metadatum getMetadatum()
  {
    if (metadatum == null)
    {
      final var deliver = eDeliver();
      eSetDeliver(false);
      setMetadatum(XcatalogueFactory.eINSTANCE.createMetadatum());
      eSetDeliver(deliver);
    }

    return super.getMetadatum();
  }

  protected boolean isSetFeature(EAttribute feature)
  {
    return getFeatureMap().containsKey(feature);

  }

  protected String printFragment(List< ? extends TextElement> list)
  {
    return list.stream().map(TextElement::getText)
            .collect(Collectors.joining(System.lineSeparator())).trim();
  }

  @SuppressWarnings({"unchecked", "rawtypes" })
  private Map<EStructuralFeature, Object> parse()
  {
    final Map<EStructuralFeature, Object> values = new HashMap<>();

    final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();
    if (xsmpcatdoc == null)
    {
      return values;
    }

    final var tags = xsmpcatdoc.tags();
    for (final TagElement tag : tags)
    {
      if (tag.getTagName() == null)
      {
        values.put(XcataloguePackage.Literals.NAMED_ELEMENT__DESCRIPTION,
                printFragment(tag.fragments()));
      }
      else
      {
        // find a featured tag
        final var feature = getFeature(tag.getTagName().substring(1));
        if (feature != null)
        {
          try
          {
            final var value = parse(feature, printFragment(tag.fragments()));
            if (feature.isMany())
            {
              ((Collection) values.computeIfAbsent(feature, f -> new XsmpcatdocEList<>(this, f)))
                      .add(value);
            }
            else
            {
              values.putIfAbsent(feature, value);
            }
          }
          catch (final Exception e)
          {
            // ignore
          }
        }
      }
    }
    return values;
  }

  public void check(DiagnosticChain chain)
  {

    final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();
    if (xsmpcatdoc == null)
    {
      return;
    }
    var nodeOffset = 0;
    for (final INode node : NodeModelUtils.findNodesForFeature(getMetadatum(),
            XcataloguePackage.Literals.METADATUM__DOCUMENTATION))
    {
      nodeOffset = node.getTotalOffset();
    }

    final Set<EStructuralFeature> features = new HashSet<>();

    for (final TagElement tag : xsmpcatdoc.tags())
    {
      if (tag.getTagName() == null)
      {
        features.add(XcataloguePackage.Literals.NAMED_ELEMENT__DESCRIPTION);
      }
      else
      {

        // find a featured tag
        final var feature = getFeature(tag.getTagName().substring(1));
        if (feature == null)
        {
          switch (tag.getTagName())
          {
            // rise error for these tags because they are defined in the grammar
            case "@description":
              chain.add(createIssue("Invalid tag: " + tag.getTagName() + ".",
                      nodeOffset + tag.getStartPosition(), tag.getLength(), Diagnostic.ERROR));
              break;
            case "@param":
              final var paramName = tag.fragments().stream().map(TextElement::getText).findFirst()
                      .orElse(null);
              if (this instanceof Operation && ((Operation) this).getParameter().stream()
                      .filter(p -> p.getName().equals(paramName)).findFirst().isEmpty())
              {
                chain.add(createIssue("Parameter " + paramName + " does not exist.",
                        nodeOffset + tag.getStartPosition(), tag.getLength(), Diagnostic.WARNING));
              }
              break;
            case "@return":
              if (this instanceof Operation && ((Operation) this).getReturnParameter() == null)
              {
                chain.add(createIssue("Operation does not returns.",
                        nodeOffset + tag.getStartPosition(), tag.getLength(), Diagnostic.WARNING));
              }
              break;
            default:
              break;
          }
        }
        else
        {
          if (!features.add(feature) && !feature.isMany())
          {
            chain.add(createIssue("Duplicated tag name " + tag.getTagName(),
                    nodeOffset + tag.getStartPosition(), tag.getLength(), Diagnostic.ERROR));
          }

          final var strValue = printFragment(tag.fragments());
          try
          {
            parse(feature, strValue);
          }
          catch (final Exception e)
          {
            chain.add(createIssue(
                    "Unable to convert '" + strValue + "' to " + feature.getEType().getName()
                            + " Type.",
                    nodeOffset + tag.getStartPosition(), tag.getLength(), Diagnostic.ERROR));
          }
        }
      }
    }
  }

  protected Object parse(EStructuralFeature feature, String strValue)
  {
    if (feature instanceof EReference)
    {
      final var eClass = ((EReference) feature).getEReferenceType();
      final var result = EcoreUtil.create(eClass);

      final var values = strValue.split(",");

      for (final String value : values)
      {

        final var entry = value.split("=");
        if (entry.length == 2)
        {
          final var f = eClass.getEStructuralFeature(entry[0].trim());
          result.eSet(f, entry[1].substring(1, entry[1].length() - 1));
        }
      }
      return result;
    }
    final var dataType = ((EAttribute) feature).getEAttributeType();
    // ignore the strValue, the tag is present so value is true, the strValue is a description
    if (dataType.getInstanceClass() == boolean.class
            || dataType.getInstanceClass() == Boolean.class)
    {
      return Boolean.TRUE;
    }

    return EcoreUtil.createFromString(dataType, strValue);
  }

  protected String serialize(EStructuralFeature feature, Object object)
  {

    if (feature instanceof EReference)
    {

      final var eObject = (EObject) object;
      return eObject.eClass().getEAllAttributes().stream().filter(eObject::eIsSet)
              .map(a -> a.getName() + "=\"" + eObject.eGet(a).toString() + "\"")
              .collect(Collectors.joining(", "));
    }
    return EcoreUtil.convertToString(((EAttribute) feature).getEAttributeType(), object);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescription(String newDescription)
  {
    final var oldValue = getFeatureMap().put(XcataloguePackage.Literals.NAMED_ELEMENT__DESCRIPTION,
            newDescription);
    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET,
              XcataloguePackage.Literals.NAMED_ELEMENT__DESCRIPTION.getFeatureID(), oldValue,
              newDescription));

    }
    final var metadatum = getMetadatum();
    final var xsmpcatdoc = metadatum.getXsmpcatdoc();
    var tag = xsmpcatdoc.tags().stream().findFirst().orElse(null);

    if (tag == null || tag.getTagName() != null)
    {
      tag = new TagElement(-1, null);
      xsmpcatdoc.tags().add(0, tag);
    }
    else
    {
      tag.fragments().clear();
    }
    // set the value
    if (newDescription != null)
    {
      for (final String v : newDescription.split(System.lineSeparator()))
      {
        tag.fragments().add(new TextElement(-1, v));
      }
    }
    else
    {
      xsmpcatdoc.tags().remove(tag);
    }
    metadatum.setDocumentation(xsmpcatdoc.toString());
  }

  protected void setFeature(EAttribute feature, Object value)
  {

    final var oldValue = getFeatureMap().put(feature, value);
    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET, feature.getFeatureID(), oldValue,
              value));

    }
    final var metadatum = getMetadatum();
    final var xsmpcatdoc = metadatum.getXsmpcatdoc();

    // find the tag
    final var tagName = "@" + feature.getName();
    var tag = xsmpcatdoc.tags().stream().filter(t -> tagName.equals(t.getTagName())).findFirst()
            .orElse(null);

    final var dataType = feature.getEAttributeType();

    final var strValue = EcoreUtil.convertToString(feature.getEAttributeType(), value);
    if (dataType.getInstanceClass() == boolean.class
            || dataType.getInstanceClass() == Boolean.class)
    {
      if (Boolean.FALSE.equals(value))
      {
        xsmpcatdoc.tags().remove(tag);
      }
      else if (tag == null)
      {
        tag = new TagElement(-1, tagName);
        xsmpcatdoc.tags().add(tag);
      }
      else
      {
        tag.fragments().clear();
      }
    }
    else if (strValue == null || strValue.isEmpty())
    {
      xsmpcatdoc.tags().remove(tag);
    }
    else
    {
      if (tag == null)
      {
        tag = new TagElement(-1, tagName);
        xsmpcatdoc.tags().add(tag);
      }
      else
      {
        tag.fragments().clear();
      }
      // set the value
      for (final String v : strValue.split(System.lineSeparator()))
      {
        tag.fragments().add(new TextElement(-1, v));
      }
    }

    metadatum.setDocumentation(xsmpcatdoc.toString());

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMetadatum(Metadatum newMetadatum)
  {

    if (metadatum != null)
    {
      featureMap = null;
    }
    super.setMetadatum(newMetadatum);
  }

  protected void unSetFeature(EAttribute feature)
  {

    final var oldValue = getFeatureMap().remove(feature);
    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.UNSET, feature.getFeatureID(), oldValue,
              null, oldValue != null));

    }
    final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();

    // remove the tag
    final var tagName = "@" + feature.getName();
    xsmpcatdoc.tags().removeIf(t -> tagName.equals(t.getTagName()));

    metadatum.setDocumentation(xsmpcatdoc.toString());

  }

  @Override
  public boolean isDeprecated()
  {

    return getFeature(XcataloguePackage.Literals.NAMED_ELEMENT__DEPRECATED, DEPRECATED_EDEFAULT);
  }

  @Override
  public void setDeprecated(boolean newDeprecated)
  {

    setFeature(XcataloguePackage.Literals.NAMED_ELEMENT__DEPRECATED, newDeprecated);
  }

} // NamedElementImplCustom
