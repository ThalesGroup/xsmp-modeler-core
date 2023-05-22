package org.eclipse.xsmp.tool.python.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.xcatalogue.EventSource

class PythonEventSourceGenerator {
	
	@Inject
	protected extension GeneratorExtension

	def CharSequence generate(EventSource eventsource) {
		'''
			«eventsource.name»: Smp.EventSourceObject = Smp.EventSource(«eventsource.type.typeName»)
		'''
	}
}