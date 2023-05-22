package org.eclipse.xsmp.tool.python.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.member.PythonFieldGenerator
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.tool.python.generator.AbstractFileGenerator
import org.eclipse.xsmp.tool.python.generator.member.PythonConstantGenerator

class PythonStructureGenerator extends AbstractFileGenerator<Structure> {

	@Inject
	PythonFieldGenerator fieldGenerator;

	@Inject
	PythonConstantGenerator constantGenerator;

	override protected generate(Structure type) {
		'''
			class Type(Smp.Structure):
				«FOR field : type.member.filter(typeof(Field))»«fieldGenerator.generate(field)»«ENDFOR»
				«FOR cst : type.member.filter(typeof(Constant))»«constantGenerator.generate(cst)»«ENDFOR»
			
				def __init__(self, parent, name, default_value=None):
					super().__init__(parent, name)
		'''
	}

}
