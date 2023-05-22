package org.eclipse.xsmp.tool.python.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.ExpressionHelper
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.xcatalogue.Constant

class PythonConstantGenerator {
	
	@Inject
	ExpressionHelper helper
	
	@Inject
	protected extension GeneratorExtension

	def CharSequence generate(Constant constant) {
		'''
			«constant.name»: «constant.type.typeName» = Smp.Constant(«helper.getValue(constant.value, constant.type)»)
		'''
	}
}