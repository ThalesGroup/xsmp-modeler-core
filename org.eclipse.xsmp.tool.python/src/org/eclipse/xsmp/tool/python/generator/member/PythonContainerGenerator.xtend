package org.eclipse.xsmp.tool.python.generator.member

import org.eclipse.xsmp.xcatalogue.Container

class PythonContainerGenerator {
	
	def CharSequence generate(Container container) {
		'''
			«container.name»: Smp.ContainerObject = Smp.Container()
		'''
	}
}
