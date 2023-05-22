package org.eclipse.xsmp.tool.python.generator.member

import org.eclipse.xsmp.xcatalogue.Reference

class PythonReferenceGenerator {

	def CharSequence generate(Reference reference) {
		'''
			«reference.name»: Smp.ReferenceObject = Smp.Reference()
		'''
	}
}
