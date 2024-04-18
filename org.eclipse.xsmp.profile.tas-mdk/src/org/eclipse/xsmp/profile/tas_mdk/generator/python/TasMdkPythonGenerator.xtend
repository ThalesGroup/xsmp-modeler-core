package org.eclipse.xsmp.profile.tas_mdk.generator.python

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import com.google.common.collect.Iterators
import com.google.inject.Inject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.Document
import org.eclipse.xsmp.documentation.CopyrightNoticeProvider

class TasMdkPythonGenerator extends AbstractGenerator {
    
	@Inject extension XsmpUtil
	
	@Inject
	CopyrightNoticeProvider provider

	override void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		Iterators::filter(input.getAllContents(), typeof(Component)).forEachRemaining([it|generate(it, fsa)])
	}

	/** 
	 * Generate Python helper files 
	 */
	def void generate(Component type, IFileSystemAccess2 fsa) {
		val path = type.fqn.toString("/")

		generateFile(fsa, '''builder/«path»/generated_info.py'''.toString, "HELPERS",
			generateInfoHelperFile((type as Component)))

		if (!fsa.isFile('''builder/«path»/user_code.py'''.toString, "HELPERS")) {
			generateFile(fsa, '''builder/«path»/user_code.py'''.toString, "HELPERS",
				generateUserCodeHelperFile((type as Component)))
		}

		if (!fsa.isFile('''builder/«path»/__init__.py'''.toString, "HELPERS")) {
			generateFile(fsa, '''builder/«path»/__init__.py'''.toString, "HELPERS", "")
		}

		if (!fsa.isFile("test_utils/__init__.py", "HELPERS")) {
			generateFile(fsa, "test_utils/__init__.py", "HELPERS", "")
		}
	}

	def CharSequence generateInfoHelperFile(Component c) {
		val Document d = EcoreUtil2.getContainerOfType(c, Document)
		'''
			#!/usr/bin/env python
			# -*- coding: utf-8 -*-
			«provider.getCopyrightNotice(c.eResource, "# ")»
			
			# Import user-defined methods for model integration
			from .user_code import configureInstance, extendInstance
			
			# Model specific data
			MODEL_UUID = "«c.uuid»"
			MODEL_LIB_NAME = "lib«d.name.toLowerCase».so"
			
		'''
	}

	def CharSequence generateUserCodeHelperFile(Component c) {
		val Document d = EcoreUtil2.getContainerOfType(c, Document)
		'''
			#!/usr/bin/env python
			# -*- coding: utf-8 -*-
			# Copyright (C) «year(d)» THALES ALENIA SPACE FRANCE. All rights reserved
			
			def configureInstance(jsim, instance_data):
			    pass
			
			def extendInstance(jsim, instance_data):
			    pass
		'''
	}

	def generateFile(IFileSystemAccess2 fsa, String fileName, String outputConfigurationName,
		CharSequence contents) {
		if (contents !== null) {
			fsa.generateFile(fileName, outputConfigurationName, contents);
		}
	}
}
