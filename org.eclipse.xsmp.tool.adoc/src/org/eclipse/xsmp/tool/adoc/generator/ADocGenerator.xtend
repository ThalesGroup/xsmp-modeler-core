/*******************************************************************************
 * Copyright (C) 2023-2024 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.tool.adoc.generator

import com.google.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xsmp.model.xsmp.Catalogue
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.tool.adoc.generator.type.ADocArrayGenerator
import org.eclipse.xsmp.tool.adoc.generator.type.ADocComponentGenerator
import org.eclipse.xsmp.tool.adoc.generator.type.ADocStructureGenerator
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext

class ADocGenerator extends AbstractGenerator {
    
    @Inject
    ADocComponentGenerator componentGenerator;
    
    @Inject
    ADocStructureGenerator structureGenerator;
    
    @Inject
    ADocArrayGenerator arrayGenerator;

    override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
        generate(input.getContents().get(0) as Catalogue, fsa);
    }

    def void generate(Catalogue catalogue, IFileSystemAccess2 fsa) {
        val fileName = catalogue.name + "-catalogue.adoc"
        fsa.generateFile(fileName, ADocOutputConfigurationProvider.DOC, '''
            = Catalogue «catalogue.name»
            
            «FOR component : catalogue.eAllContents.filter(Component).toList»
                «componentGenerator.generate(component, fsa)»
            «ENDFOR»
        ''')

    }
}
