package org.eclipse.xsmp.tool.python.generator.type

import org.eclipse.xsmp.xcatalogue.Enumeration
import org.eclipse.xsmp.tool.python.generator.AbstractFileGenerator
import org.eclipse.xsmp.tool.python.generator.ExpressionHelper
import com.google.inject.Inject
import org.eclipse.xsmp.util.PrimitiveTypeKind

class PythonEnumerationGenerator extends AbstractFileGenerator<Enumeration> {
	
	@Inject
	ExpressionHelper helper
	
	override protected generate(Enumeration type) {
		'''
			class Type(enum.Enum):
				«FOR l : type.literal»
					«l.name» = «helper.getValue(l.value, PrimitiveTypeKind.INT32)»
				«ENDFOR»
		'''
	}
}