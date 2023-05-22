package org.eclipse.xsmp.tool.python.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.xcatalogue.EventSink

class PythonEventSinkGenerator {
	
	@Inject
	protected extension GeneratorExtension

	def CharSequence generate(EventSink eventsink) {
		'''
			«eventsink.name»: Smp.EventSinkObject = Smp.EventSink(«eventsink.type.typeName»)
		'''
	}
}