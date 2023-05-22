package org.eclipse.xsmp.tool.python.generator.member

import org.eclipse.xsmp.xcatalogue.EntryPoint

class PythonEntryPointGenerator {
	
	def CharSequence generate(EntryPoint entrypoint) {
		'''
			«entrypoint.name»: Smp.EntryPointObject = Smp.EntryPoint()
		'''
	}
}