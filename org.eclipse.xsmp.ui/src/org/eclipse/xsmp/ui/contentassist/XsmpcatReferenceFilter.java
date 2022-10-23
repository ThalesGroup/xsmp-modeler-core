package org.eclipse.xsmp.ui.contentassist;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.util.TypeReferenceConverter;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.util.XsmpUtil.PrimitiveTypeKind;
import org.eclipse.xsmp.validation.DefaultValidator;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpcatReferenceFilter implements IReferenceFilter
{

  private final Map<EReference, Function<EObject, Predicate<IEObjectDescription>>> builders = ImmutableMap
          .<EReference, Function<EObject, Predicate<IEObjectDescription>>> builder()
          // add the entries
          .put(XcataloguePackage.Literals.CLASS__BASE,
                  model -> p -> isValidTypeReference(model, XcataloguePackage.Literals.CLASS__BASE,
                          p)
                          && !XsmpUtil.isRecursive((org.eclipse.xsmp.xcatalogue.Class) model,
                                  p.getEObjectOrProxy()))
          .put(XcataloguePackage.Literals.FLOAT__PRIMITIVE_TYPE,
                  model -> p -> floatingPointPrimitiveSet.contains(XsmpUtil.getPrimitiveType(p)))
          .put(XcataloguePackage.Literals.INTEGER__PRIMITIVE_TYPE,
                  model -> p -> integerPrimitiveSet.contains(XsmpUtil.getPrimitiveType(p)))
          .put(XcataloguePackage.Literals.INTERFACE__BASE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.INTERFACE__BASE, p)
                          && !XsmpUtil.isRecursive((Interface) model, p.getEObjectOrProxy()))
          .put(XcataloguePackage.Literals.EVENT_TYPE__EVENT_ARGS,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.EVENT_TYPE__EVENT_ARGS, p))
          .put(XcataloguePackage.Literals.EVENT_SINK__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.EVENT_SINK__TYPE, p))
          .put(XcataloguePackage.Literals.ARRAY__ITEM_TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.ARRAY__ITEM_TYPE, p))
          .put(XcataloguePackage.Literals.EVENT_SOURCE__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.EVENT_SOURCE__TYPE, p))
          .put(XcataloguePackage.Literals.VALUE_REFERENCE__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.VALUE_REFERENCE__TYPE, p))
          .put(XcataloguePackage.Literals.FIELD__TYPE,
                  model -> p -> PrimitiveTypeKind.STRING8 != XsmpUtil.getPrimitiveType(p)
                          && isValidTypeReference(model, XcataloguePackage.Literals.FIELD__TYPE, p))
          .put(XcataloguePackage.Literals.ENTRY_POINT__INPUT,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.ENTRY_POINT__INPUT, p)
                          && ((Field) p.getEObjectOrProxy()).isInput())
          .put(XcataloguePackage.Literals.ENTRY_POINT__OUTPUT,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.ENTRY_POINT__OUTPUT, p)
                          && ((Field) p.getEObjectOrProxy()).isOutput())
          .put(XcataloguePackage.Literals.ATTRIBUTE_TYPE__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.ATTRIBUTE_TYPE__TYPE, p))
          .put(XcataloguePackage.Literals.CONSTANT__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.CONSTANT__TYPE, p))
          .put(XcataloguePackage.Literals.ASSOCIATION__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.ASSOCIATION__TYPE, p))
          .put(XcataloguePackage.Literals.CONTAINER__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.CONTAINER__TYPE, p))
          .put(XcataloguePackage.Literals.CONTAINER__DEFAULT_COMPONENT,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.CONTAINER__DEFAULT_COMPONENT, p))
          .put(XcataloguePackage.Literals.OPERATION__RAISED_EXCEPTION,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.OPERATION__RAISED_EXCEPTION, p))
          .put(XcataloguePackage.Literals.PARAMETER__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.PARAMETER__TYPE, p))
          .put(XcataloguePackage.Literals.PROPERTY__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.PROPERTY__TYPE, p))
          .put(XcataloguePackage.Literals.PROPERTY__ATTACHED_FIELD,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.PROPERTY__ATTACHED_FIELD, p))
          .put(XcataloguePackage.Literals.PROPERTY__GET_RAISES,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.PROPERTY__GET_RAISES, p))
          .put(XcataloguePackage.Literals.PROPERTY__SET_RAISES,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.PROPERTY__SET_RAISES, p))
          .put(XcataloguePackage.Literals.REFERENCE__INTERFACE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.REFERENCE__INTERFACE, p))
          .put(XcataloguePackage.Literals.COMPONENT__INTERFACE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.COMPONENT__INTERFACE, p))
          .put(XcataloguePackage.Literals.COMPONENT__BASE,
                  model -> p -> isValidTypeReference(model,
                          XcataloguePackage.Literals.COMPONENT__BASE, p)
                          && !XsmpUtil.isRecursive((Component) model, p.getEObjectOrProxy()))
          .put(XcataloguePackage.Literals.ATTRIBUTE__TYPE, model -> p -> {
            final EObject elem = EcoreUtil2.getContainerOfType(model, NamedElement.class);
            if (elem != null)
            {
              final List<String> elemUsages = Stream
                      .concat(Stream.of(elem.eClass().getName()),
                              elem.eClass().getEAllSuperTypes().stream().map(EClass::getName))
                      .collect(Collectors.toList());
              return XsmpUtil.isVisibleFrom(p, model)
                      && Arrays.stream(getUsages(p)).anyMatch(elemUsages::contains);
            }
            return false;
          })

          // build the map
          .build();

  private static final Set<PrimitiveTypeKind> floatingPointPrimitiveSet = ImmutableSet
          .<PrimitiveTypeKind> builder().add(PrimitiveTypeKind.FLOAT32, PrimitiveTypeKind.FLOAT64)
          .build();

  private static final Set<PrimitiveTypeKind> integerPrimitiveSet = ImmutableSet
          .<PrimitiveTypeKind> builder()
          .add(PrimitiveTypeKind.INT8, PrimitiveTypeKind.INT16, PrimitiveTypeKind.INT32,
                  PrimitiveTypeKind.INT64, PrimitiveTypeKind.UINT8, PrimitiveTypeKind.UINT16,
                  PrimitiveTypeKind.UINT32, PrimitiveTypeKind.UINT64)
          .build();

  @Inject
  private TypeReferenceConverter typeReferenceConverter;

  String[] getUsages(IEObjectDescription d)
  {
    final var obj = d.getEObjectOrProxy();

    if (obj instanceof AttributeType)
    {
      if (obj.eIsProxy())
      {
        return d.getUserData("usage").split(" ");
      }
      return ((AttributeType) obj).getUsage()
              .toArray(new String[((AttributeType) obj).getUsage().size()]);
    }
    return new String[0];

  }

  private boolean isValidTypeReference(EObject eObject, EReference reference, IEObjectDescription p)
  {
    if (!typeReferenceConverter.convert(reference).isInstance(p.getEObjectOrProxy())
            || !XsmpUtil.isVisibleFrom(p, eObject))
    {
      return false;
    }
    final var fqn = p.getQualifiedName();
    if (fqn.equals(DefaultValidator.fieldUpdateKind) || fqn.equals(DefaultValidator.operatorKind))
    {
      return false;
    }

    if (reference.isMany())
    {
      final var values = (List< ? >) eObject.eGet(reference);
      if (values.contains(p.getEObjectOrProxy()))
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public Predicate<IEObjectDescription> getFilter(EObject model, EReference reference)
  {

    final var filterBuilder = builders.get(reference);
    if (filterBuilder != null)
    {
      return filterBuilder.apply(model);
    }
    return Predicates.alwaysTrue();
  }
}
