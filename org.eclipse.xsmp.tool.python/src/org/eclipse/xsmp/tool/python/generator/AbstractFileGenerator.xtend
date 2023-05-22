package org.eclipse.xsmp.tool.python.generator

import org.eclipse.xsmp.xcatalogue.NamedElement
import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.Catalogue

abstract class AbstractFileGenerator<T extends NamedElement> {
	@Inject
	PythonCopyrightNoticeProvider copyright

	@Inject
	ImportGenerator importGenerator

	def protected abstract CharSequence generate(T type)

	def protected CharSequence generateUserCode(T type) {
		''
	}

	def protected CharSequence generateFile(T type, Catalogue cat) {
		'''
			«copyright.generate(type, cat, true)»
			
			«importGenerator.generate(type)»
			
			
			«type.generate»
		'''
	}

	def protected CharSequence generateUserCodeFile(T type, Catalogue cat) {
		'''
			«copyright.generate(type, cat, false)»
			
			«importGenerator.generateUserCode(type)»
			
			
			«type.generateUserCode»
		'''
	}
}
