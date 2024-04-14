/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.model.xsmp.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.documentation.Documentation;
import org.eclipse.xsmp.documentation.TagElement;
import org.eclipse.xsmp.documentation.TextElement;
import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xsmp.model.xsmp.Operation;
import org.eclipse.xsmp.model.xsmp.XsmpFactory;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
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

    public static final long serialVersionUID = -7859434301857045784L;

    public XsmpcatdocEList(InternalEObject owner, EAttribute eStructuralFeature)
    {
      super(owner, eStructuralFeature);
    }

    @Override
    protected void didAdd(int index, E newObject)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      updateDocumentation((EAttribute) eStructuralFeature, this);
    }

    @Override
    protected void didRemove(int index, E oldObject)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      updateDocumentation((EAttribute) eStructuralFeature, this);
    }

    @Override
    protected void didClear(int size, Object[] oldObjects)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      updateDocumentation((EAttribute) eStructuralFeature, this);
    }

    @Override
    protected void didMove(int index, E movedObject, int oldIndex)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      updateDocumentation((EAttribute) eStructuralFeature, this);
    }

    @Override
    protected void didSet(int index, E newObject, E oldObject)
    {
      if (!owner.eDeliver())
      {
        return;
      }
      updateDocumentation((EAttribute) eStructuralFeature, this);
    }
  }

  /**
   * Map in which to store the value of each feature define inside the DOCUMENTATION of the
   * Metadatum
   */
  private Map<EAttribute, Object> featureMap;

  private Diagnostic createIssue(String message, int offset, int length, int error)
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
    return featureID == XsmpPackage.NAMED_ELEMENT__METADATUM || super.eIsSet(featureID);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription()
  {
    return getFeature(XsmpPackage.Literals.NAMED_ELEMENT__DESCRIPTION, DESCRIPTION_EDEFAULT);
  }

  @SuppressWarnings("unchecked")
  protected <T> EList<T> getFeature(EAttribute feature)
  {
    return (EList<T>) getFeatureMap().computeIfAbsent(feature, f -> new XsmpcatdocEList<>(this, f));
  }

  protected EAttribute getFeature(String name)
  {
    return "deprecated".equals(name) ? XsmpPackage.Literals.NAMED_ELEMENT__DEPRECATED : null;
  }

  @SuppressWarnings("unchecked")
  protected <T> T getFeature(EStructuralFeature feature, Object defaultValue)
  {
    return (T) getFeatureMap().getOrDefault(feature, defaultValue);
  }

  /**
   * @return the feature map with parsed results
   */
  protected Map<EAttribute, Object> getFeatureMap()
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
      setMetadatum(XsmpFactory.eINSTANCE.createMetadatum());
      eSetDeliver(deliver);
    }

    return super.getMetadatum();
  }

  protected String printFragment(Documentation doc, List< ? extends TextElement> list)
  {
    return list.stream().map(e -> e.getText(doc))
            .collect(Collectors.joining(System.lineSeparator())).trim();
  }

  @SuppressWarnings({"unchecked" })
  private Map<EAttribute, Object> parse()
  {
    final Map<EAttribute, Object> values = new LinkedHashMap<>();

    final var xsmpcatdoc = getMetadatum().getXsmpcatdoc();
    if (xsmpcatdoc == null)
    {
      return values;
    }

    final var tags = xsmpcatdoc.tags();
    for (final TagElement tag : tags)
    {
      if (tag.getTagName(xsmpcatdoc) == null)
      {
        values.put(XsmpPackage.Literals.NAMED_ELEMENT__DESCRIPTION,
                printFragment(xsmpcatdoc, tag.fragments()));
      }
      else
      {
        // find a featured tag
        final var feature = getFeature(tag.getTagName(xsmpcatdoc).substring(1));
        if (feature != null)
        {
          try
          {
            final var value = parse(feature, printFragment(xsmpcatdoc, tag.fragments()));
            if (feature.isMany())
            {
              ((Collection<Object>) values.computeIfAbsent(feature,
                      f -> new XsmpcatdocEList<>(this, f))).add(value);
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
            XsmpPackage.Literals.METADATUM__DOCUMENTATION))
    {
      nodeOffset = node.getTotalOffset();
    }

    final Set<EStructuralFeature> features = new HashSet<>();

    for (final TagElement tag : xsmpcatdoc.tags())
    {
      final var tagName = tag.getTagName(xsmpcatdoc);
      if (tagName == null)
      {
        features.add(XsmpPackage.Literals.NAMED_ELEMENT__DESCRIPTION);
      }
      else
      {

        // find a featured tag
        final var feature = getFeature(tagName.substring(1));
        if (feature == null)
        {
          switch (tagName)
          {
            // rise error for these tags because they are defined in the grammar
            case "@description":
              chain.add(createIssue("Invalid tag: " + tag.getTagName(xsmpcatdoc) + ".",
                      nodeOffset + tag.getStartPosition(), tag.getTotalLength(), Diagnostic.ERROR));
              break;
            case "@param":
              final var paramName = tag.fragments().stream().map(e -> e.getText(xsmpcatdoc))
                      .findFirst().orElse(null);
              if (this instanceof final Operation op && op.getParameter().stream()
                      .filter(p -> p.getName().equals(paramName)).findFirst().isEmpty())
              {
                chain.add(createIssue("Parameter " + paramName + " does not exist.",
                        nodeOffset + tag.getStartPosition(), tag.getTotalLength(),
                        Diagnostic.WARNING));
              }
              break;
            case "@return":
              if (this instanceof final Operation op && op.getReturnParameter() == null)
              {
                chain.add(createIssue("Operation does not returns.",
                        nodeOffset + tag.getStartPosition(), tag.getTotalLength(),
                        Diagnostic.WARNING));
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
            chain.add(createIssue("Duplicated tag name " + tag.getTagName(xsmpcatdoc),
                    nodeOffset + tag.getStartPosition(), tag.getTotalLength(), Diagnostic.ERROR));
          }

          final var strValue = printFragment(xsmpcatdoc, tag.fragments());
          try
          {
            parse(feature, strValue);
          }
          catch (final Exception e)
          {
            chain.add(createIssue(
                    "Unable to convert '" + strValue + "' to " + feature.getEType().getName()
                            + " Type.",
                    nodeOffset + tag.getStartPosition(), tag.getTotalLength(), Diagnostic.ERROR));
          }
        }
      }
    }
  }

  protected Object parse(EStructuralFeature feature, String strValue)
  {
    if (feature instanceof final EReference ref)
    {
      final var eClass = ref.getEReferenceType();
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

  @Override
  public void setMetadatum(Metadatum newMetadatum)
  {
    if (metadatum != null)
    {
      featureMap = null;
    }
    super.setMetadatum(newMetadatum);
  }

  @Override
  public boolean isDeprecated()
  {
    return getFeature(XsmpPackage.Literals.NAMED_ELEMENT__DEPRECATED, DEPRECATED_EDEFAULT);
  }

  @Override
  public void setDeprecated(boolean newDeprecated)
  {
    setFeature(XsmpPackage.Literals.NAMED_ELEMENT__DEPRECATED, newDeprecated);
  }

  protected void setFeature(EAttribute feature, Object value)
  {
    final var oldValue = getFeatureMap().put(feature, value);

    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET, feature.getFeatureID(), oldValue,
              value));
    }
    // update the documentation
    updateDocumentation(feature, value);

  }

  private void updateDocumentation(EAttribute feature, Object value)
  {
    final var doc = metadatum.getXsmpcatdoc();
    final var sb = new StringBuilder();
    sb.append("/**\n");
    var tagUpdated = false;
    for (final var tag : doc.tags())
    {
      if (tag.getTagLength() == 0)
      {
        if (feature == XsmpPackage.Literals.NAMED_ELEMENT__DESCRIPTION)
        {
          sb.append(value).append("\n");
          tagUpdated = true;
        }
        else
        {
          sb.append(tag.getText(doc)).append("\n");
        }
      }
      else
      {
        final var tagName = tag.getTagName(doc);
        if (("@" + feature.getName()).equals(tagName))
        {
          if (!tagUpdated)
          {
            append(feature, value, sb);
            tagUpdated = true;
          }
        }
        else
        {
          sb.append(tag.getText(doc)).append("\n");
        }
      }
    }
    if (!tagUpdated)
    {
      append(feature, value, sb);
    }

    sb.append("*/");
    metadatum.setDocumentation(new Documentation(sb.toString()).toString());
  }

  private void append(EAttribute feature, Object value, StringBuilder sb)
  {
    if (!eIsSet(feature))
    {
      return;
    }
    final var dataType = feature.getEAttributeType();
    final var tagName = "@" + feature.getName();
    if (dataType.getInstanceClass() == boolean.class
            || dataType.getInstanceClass() == Boolean.class)
    {
      if (Boolean.TRUE.equals(value))
      {
        sb.append(tagName).append("\n");
      }
    }
    else if (feature.isMany())
    {
      for (final var v : (Collection< ? >) value)
      {
        sb.append(tagName).append(" ").append(EcoreUtil.convertToString(dataType, v)).append("\n");
      }
    }
    else
    {
      sb.append(tagName).append(" ").append(EcoreUtil.convertToString(dataType, value))
              .append("\n");
    }
  }

  @Override
  public void setDescription(String newDescription)
  {
    setFeature(XsmpPackage.Literals.NAMED_ELEMENT__DESCRIPTION, newDescription);
  }

} // NamedElementImplCustom
