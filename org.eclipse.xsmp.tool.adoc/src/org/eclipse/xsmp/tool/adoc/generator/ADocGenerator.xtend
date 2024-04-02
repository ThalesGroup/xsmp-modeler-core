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
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.model.xsmp.Catalogue
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.Namespace
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.model.xsmp.Type
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xsmp.tool.adoc.generator.type.ADocArrayGenerator
import org.eclipse.xsmp.tool.adoc.generator.type.ADocComponentGenerator
import org.eclipse.xsmp.tool.adoc.generator.type.ADocStructureGenerator
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import org.eclipse.xsmp.tool.adoc.generator.type.ADocEnumerationGenerator
import org.eclipse.xsmp.model.xsmp.Enumeration

class ADocGenerator extends AbstractGenerator {

    @Inject
    ADocComponentGenerator componentGenerator;

    @Inject
    ADocStructureGenerator structureGenerator;

    @Inject
    ADocArrayGenerator arrayGenerator;
    
    @Inject
    ADocEnumerationGenerator enumGenerator;

    @Inject extension ADocUtil

    override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
        generateCatalogue(input.getContents().get(0) as Catalogue, fsa);
    }

    def void generateCatalogue(Catalogue catalogue, IFileSystemAccess2 fsa) {
        // Catalogue file
        val fileName = catalogue.name + "-catalogue.adoc"
        fsa.generateFile(fileName, ADocOutputConfigurationProvider.DOC, '''
            = Catalogue «catalogue.name»
            «catalogue.description»
            
            «FOR namespace : catalogue.member.filter(Namespace)»
                «namespace.generateNamespace(hasTypeMembers(namespace))»
            «ENDFOR»
        ''')

    // User-code file
    // ...
    }

    def CharSequence generateNamespace(Namespace namespace, boolean print) {
        '''
            «IF print»
                == Namespace «namespace.fqn.toString("::")»
                «namespace.description»
                «namespace.generateMermaidNamespace»
            «ENDIF»
            
            «FOR type : namespace.member.filter(Type)»«type.generateType»«ENDFOR»
            
            «FOR neastedNs : namespace.member.filter(Namespace)»
                «neastedNs.generateNamespace(hasTypeMembers(neastedNs))»
            «ENDFOR»
        '''
    }

    def CharSequence generateType(Type type) {
        if (type instanceof Component) {
            return componentGenerator.generate(type, null);
        } else if (type instanceof Structure) {
            return structureGenerator.generate(type, null);
        } else if (type instanceof Array) {
            return arrayGenerator.generate(type, null);
        } else if (type instanceof Enumeration) {
            return enumGenerator.generate(type, null);
            //return '';
        }

        return '';
    }

    def CharSequence generateMermaidNamespace(Namespace namespace) {
        val types = namespace.member.filter(Type)
        '''
            [.center]
            [mermaid]
            ....
            classDiagram
            
            direction LR
            
            namespace «namespace.name» {
                «FOR type : types»
                    class «type.name» {
                        <<«type.eClass.name»>>
                    }
                «ENDFOR»
            }
            ....
        '''
    }
    
    private def hasTypeMembers(NamedElementWithMembers elem) {
    	return !elem.member.filter[it | !(it instanceof Namespace)].empty
    }
}
