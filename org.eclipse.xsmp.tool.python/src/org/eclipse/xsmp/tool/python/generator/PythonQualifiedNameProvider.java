package org.eclipse.xsmp.tool.python.generator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The qualified name converter for Xsmp elements
 *
 * @author daveluy
 */
@Singleton
public class PythonQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

	@Inject
	private IResourceScopeCache cache;

	@Inject
	private IQualifiedNameConverter converter;

	/**
	 * Computes the fully qualified name for the given object, if any.
	 */
	protected QualifiedName computeFullyQualifiedName(EObject obj) {
		final var name = obj instanceof NamedElement ? ((NamedElement) obj).getName() : null;
		if (Strings.isEmpty(name)) {
			return null;
		}
		final var qualifiedNameFromConverter = converter.toQualifiedName(name);
		while ((obj = obj.eContainer()) != null) {
			QualifiedName parentsQualifiedName;
			if ((parentsQualifiedName = getFullyQualifiedName(obj)) != null) {
				return parentsQualifiedName.append(qualifiedNameFromConverter);
			}
		}
		return qualifiedNameFromConverter;
	}

	/**
	 * Tries to obtain the FQN of the given object from the {@link #cache}. If it is
	 * absent, it computes a new name.
	 */
	@Override
	public QualifiedName getFullyQualifiedName(final EObject obj) {
		if (obj == null) {
			return QualifiedName.EMPTY;
		}
		return cache.get(Tuples.pair(obj, "fqn_python"), obj.eResource(), () -> computeFullyQualifiedName(obj));
	}

}