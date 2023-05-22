package org.eclipse.xsmp.tool.python.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.tool.python.generator.AbstractFileGenerator

class PythonStringGenerator extends AbstractFileGenerator<org.eclipse.xsmp.xcatalogue.String> {
	
	@Inject
	protected extension GeneratorExtension

	override protected generate(org.eclipse.xsmp.xcatalogue.String type) {
		'''
			Type = Smp.String8
		'''
	}
}