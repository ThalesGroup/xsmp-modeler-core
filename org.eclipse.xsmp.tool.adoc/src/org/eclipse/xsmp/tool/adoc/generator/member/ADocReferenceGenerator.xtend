package org.eclipse.xsmp.tool.adoc.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.Reference
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocReferenceGenerator {

    @Inject extension ADocUtil

    def CharSequence generateContent(Component component, IFileSystemAccess2 fsa) {
        val references = component.member.filter(Reference)
        '''
            «IF !references.empty»
                ==== References
                The model shall implement the reference parameters defined below.
                 
                .Model Reference
                |===
                |Name |Comment |Type
                
                «FOR reference : references»
                    «reference.generate»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def protected CharSequence generate(Reference reference) {
        '''
            |«reference.name»
            |«reference.description.formatDescription»
            |«reference.interface.fqn.toString("::")»
        '''
    }
    
    def CharSequence generateMermaid(Component component) {
        val references = component.member.filter(Reference)
        '''
            «FOR ref : references»
                «component.name» "«ref.lower»..«ref.upper»" o-- «ref.interface.name» : «ref.name»
            «ENDFOR»
        '''
    }
}
