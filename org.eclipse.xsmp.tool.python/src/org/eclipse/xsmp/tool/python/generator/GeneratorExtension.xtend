package org.eclipse.xsmp.tool.python.generator

import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xsmp.xcatalogue.NamedElement
import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.XcataloguePackage
import org.eclipse.xsmp.xcatalogue.Type

class GeneratorExtension {

	@Inject
	protected PythonQualifiedNameProvider qualifiedNameProvider

	final String generatedFilePattern = "%Gen"

	def String getGenName(NamedElement elem) {
		return generatedFilePattern.replaceFirst("%", elem.getName())
	}

	def QualifiedName fqn(NamedElement type) {
		return qualifiedNameProvider.getFullyQualifiedName(type)
	}

	def QualifiedName fqn(NamedElement type, boolean useGenPattern) {
		var qfn = type.fqn
		if (useGenPattern)
			qfn = qfn.skipLast(1).append(type.genName)
		return qfn
	}

	def CharSequence getTypeName(Type type) {
		var typeId = type.eClass().getClassifierID()

		switch (typeId) {
			case XcataloguePackage.PRIMITIVE_TYPE:
				return '''Smp.«type.name»'''
			default:
				return '''«type.fqn.toString(".")».Type'''
		}
	}
}
