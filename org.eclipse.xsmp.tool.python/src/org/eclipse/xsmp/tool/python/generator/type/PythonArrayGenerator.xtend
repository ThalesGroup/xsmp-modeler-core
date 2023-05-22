package org.eclipse.xsmp.tool.python.generator.type

import org.eclipse.xsmp.tool.python.generator.AbstractFileGenerator
import org.eclipse.xsmp.xcatalogue.Array
import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.tool.python.generator.ExpressionHelper
import org.eclipse.xsmp.util.PrimitiveTypeKind

class PythonArrayGenerator extends AbstractFileGenerator<Array> {
	
	@Inject
	protected extension GeneratorExtension
	
	@Inject
	ExpressionHelper helper

	override protected generate(Array type) {
		'''
			class Type(Smp.Array):
			
				def __init__(self, parent, name, default_value=None):
					super().__init__(parent, name, «type.itemType.typeName», «helper.getValue(type.size, PrimitiveTypeKind.INT32)», default_value)
					
				def __getitem__(self, index: int) -> «type.itemType.typeName»:
				        return super().__getitem__(index)
		'''
	}
}