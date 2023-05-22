package org.eclipse.xsmp.tool.python.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.tool.python.generator.AbstractFileGenerator
import org.eclipse.xsmp.xcatalogue.EventType

class PythonEventTypeGenerator extends AbstractFileGenerator<EventType> {
	
	@Inject
	protected extension GeneratorExtension

	override protected generate(EventType type) {
		'''
			Type = typing.NewType("«type.name»", «IF type.eventArgs !== null»«type.eventArgs.typeName»«ELSE»None«ENDIF»)
		'''
	}
}