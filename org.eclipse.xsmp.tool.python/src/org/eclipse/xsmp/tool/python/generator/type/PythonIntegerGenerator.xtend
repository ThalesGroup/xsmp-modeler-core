package org.eclipse.xsmp.tool.python.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.Integer
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.tool.python.generator.AbstractFileGenerator

class PythonIntegerGenerator extends AbstractFileGenerator<Integer> {

	@Inject
	protected extension GeneratorExtension

	override protected generate(Integer type) {
		'''
			Type = typing.NewType("«type.name»", «IF type.primitiveType !== null »«type.primitiveType.typeName»«ELSE»Smp.Int32«ENDIF»)
		'''
	}
}
