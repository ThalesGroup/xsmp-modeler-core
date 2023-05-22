package org.eclipse.xsmp.tool.python.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.Float
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.tool.python.generator.AbstractFileGenerator

class PythonFloatGenerator extends AbstractFileGenerator<Float> {
	
	@Inject
	protected extension GeneratorExtension

	override protected generate(Float type) {
		'''
			Type = typing.NewType("«type.name»", «IF type.primitiveType !== null »«type.primitiveType.typeName»«ELSE»Smp.Float32«ENDIF»)
		'''
	}
}