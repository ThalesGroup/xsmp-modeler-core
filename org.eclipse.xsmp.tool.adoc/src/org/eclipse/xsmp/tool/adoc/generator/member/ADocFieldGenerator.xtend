package org.eclipse.xsmp.tool.adoc.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocFieldGenerator {

    @Inject extension ADocUtil

    def CharSequence generateContent(NamedElementWithMembers elem, IFileSystemAccess2 fsa) {
        val fields = elem.member.filter(Field).filter[it|!it.isOutput && !it.isInput]
        '''
            «IF !fields.empty»
                === Fields
                The model shall implement the fields defined below.
                 
                .Fields
                |===
                |Name |Comment |Type |Unit |Initial Value
                
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
            |«field.^default.defaultValue»
        '''
    }
}
