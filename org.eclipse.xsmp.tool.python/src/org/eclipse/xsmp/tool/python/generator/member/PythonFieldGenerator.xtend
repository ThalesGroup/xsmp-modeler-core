package org.eclipse.xsmp.tool.python.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.tool.python.generator.ExpressionHelper

class PythonFieldGenerator {
	
	@Inject
	ExpressionHelper helper

	@Inject
	protected extension GeneratorExtension

	def CharSequence generate(Field field) {
		'''
			«field.name»: «field.type.typeName» = Smp.Field(«field.type.typeName»«IF field.^default !== null», «helper.getValue(field.^default, field.type)»«ENDIF»)
		'''
	}
}