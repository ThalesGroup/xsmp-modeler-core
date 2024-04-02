package org.eclipse.xsmp.tool.adoc.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocInputGenerator {

    @Inject extension ADocUtil

    def CharSequence generateContent(NamedElementWithMembers elem, IFileSystemAccess2 fsa) {
        val fields = elem.member.filter(Field).filter[it|it.isInput]
        '''
            «IF !fields.empty»
                === Inputs
                The model shall implement the inputs parameters defined below.
                 
                .Model inputs
                |===
                |Name |Comment |Type |Unit
                
                «FOR field : fields»
                    «field.generate»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def CharSequence generate(Field field) {
        '''
            |«field.name»
            |«field.description.formatDescription»
            |«field.type.fqn.toString("::")»
            |N.A.
        '''
    }

    def CharSequence generateMermaid(NamedElementWithMembers elem) {
        val fields = elem.member.filter(Field).filter[it|it.isInput]
        '''
            «FOR field : fields»
                input «field.name»: «field.type.name»
            «ENDFOR»
        '''
    }
}
