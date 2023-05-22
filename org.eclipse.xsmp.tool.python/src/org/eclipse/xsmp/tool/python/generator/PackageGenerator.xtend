package org.eclipse.xsmp.tool.python.generator

import org.eclipse.xsmp.xcatalogue.Catalogue
import com.google.inject.Inject

class PackageGenerator {
	
	@Inject
	PythonCopyrightNoticeProvider copyright
	
	def protected CharSequence generate(Catalogue cat) {
		'''
			«copyright.generate("_Package", cat, false)»
			
			def get_library_name():
				return "lib«cat.name.toLowerCase».so"
		'''
	}
}