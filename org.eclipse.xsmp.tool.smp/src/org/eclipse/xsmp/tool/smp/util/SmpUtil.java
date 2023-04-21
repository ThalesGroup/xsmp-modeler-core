package org.eclipse.xsmp.tool.smp.util;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.xsmp.tool.smp.core.elements.NamedElement;
import org.eclipse.xsmp.tool.smp.core.types.Attribute;
import org.eclipse.xsmp.tool.smp.core.types.BoolValue;
import org.eclipse.xsmp.tool.smp.core.types.Value;
import org.eclipse.xsmp.tool.smp.core.types.VisibilityElement;
import org.eclipse.xsmp.tool.smp.core.types.VisibilityKind;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SmpUtil
{
  @Inject
  SmpcatQualifiedNameProvider qualifiedNameProvider;

  public QualifiedName fqn(NamedElement t)
  {
    if (t != null)
    {
      return qualifiedNameProvider.getFullyQualifiedName(t);
    }
    return QualifiedName.EMPTY;
  }

  public Attribute attribute(NamedElement o, QualifiedName id)
  {
    return o.getMetadata().stream().filter(Attribute.class::isInstance).map(Attribute.class::cast)
            .filter(it -> it.getType() != null
                    && id.equals(qualifiedNameProvider.getFullyQualifiedName(it.getType())))
            .findFirst().orElse(null);
  }

  protected Value attributeValue(NamedElement o, QualifiedName id)
  {
    final var attribute = attribute(o, id);
    if (attribute == null)
    {
      return null;
    }
    if (attribute.getValue() == null)
    {
      return attribute.getType().getRef().getDefault();
    }
    return attribute.getValue();
  }

  protected Optional<Boolean> attributeBoolValue(NamedElement o, QualifiedName id)
  {
    final var value = attributeValue(o, id);
    if (value instanceof BoolValue)
    {
      return Optional.of(((BoolValue) value).isValue());
    }

    return Optional.empty();
  }

  protected boolean attributeBoolValue(NamedElement o, QualifiedName id, boolean defaultValue)
  {
    return attributeBoolValue(o, id).orElse(defaultValue);
  }

  protected boolean attributeBoolValue(NamedElement o, QualifiedName id,
          Supplier<Boolean> defaultValue)
  {
    return attributeBoolValue(o, id).orElseGet(defaultValue);
  }

  public boolean isStatic(NamedElement o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Static, false);
  }

  public VisibilityKind getRealVisibility(VisibilityElement o)
  {

    if (o.isSetVisibility())
    {
      return o.getVisibility();
    }

    final var container = o.eContainer();
    if (container != null)
    {
      switch (container.eClass().getClassifierID())
      {
        case XcataloguePackage.INTERFACE:
        case XcataloguePackage.STRUCTURE:
          return VisibilityKind.PUBLIC;

        default:
          return VisibilityKind.PRIVATE;
      }
    }

    return VisibilityKind.PRIVATE;
  }
}
