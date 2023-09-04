/*******************************************************************************
 * Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.tool.python.generator

import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xsmp.xcatalogue.Namespace
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IGeneratorContext

class PythonGenerator extends AbstractGenerator {

    @Inject
    PythonCopyrightNoticeProvider copyright
    @Inject
    PythonQualifiedNameProvider qualifiedNameProvider

    static final String INIT_PY_FILE = "/__init__.py"

    override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
        generate(input.getContents().get(0) as Catalogue, fsa);
    }

    def void generate(Catalogue catalogue, IFileSystemAccess2 fsa) {
        val fileName = catalogue.name + INIT_PY_FILE
        fsa.generateFile(fileName, PythonOutputConfigurationProvider.PYTHON, '''
            «copyright.generate(fileName, catalogue, true)»
            
            «FOR member : catalogue.member.filter(Namespace)»
                from . import «member.name»
            «ENDFOR»
        ''')
        catalogue.member.filter(Namespace).forEach[it.generate(fsa)]

    }

    def protected void generate(Namespace namespace, IFileSystemAccess2 fsa) {
        val fileName = qualifiedNameProvider.getFullyQualifiedName(namespace).toString("/") + INIT_PY_FILE
        fsa.generateFile(fileName, PythonOutputConfigurationProvider.PYTHON, '''
            «copyright.generate(fileName, namespace, true)»
            
            «FOR member : namespace.member.filter(Namespace)»
                from . import «member.name»
            «ENDFOR»
            «FOR component : namespace.member.filter(Component) BEFORE "import ecss_smp\n\n" AFTER "\ndel ecss_smp\n"»
                class «component.name»:
                    uuid: ecss_smp.Smp.Uuid = ecss_smp.Smp.Uuid("«component.uuid»")
            «ENDFOR»
        ''')

        namespace.member.filter(Namespace).forEach[it.generate(fsa)]
    }



}
