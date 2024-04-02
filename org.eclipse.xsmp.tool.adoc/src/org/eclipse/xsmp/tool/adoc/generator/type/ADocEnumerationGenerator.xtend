package org.eclipse.xsmp.tool.adoc.generator.type

import org.eclipse.xsmp.model.xsmp.Enumeration
import org.eclipse.xsmp.tool.adoc.ADocUtil
import com.google.inject.Inject
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocEnumerationGenerator {
    
    @Inject extension ADocUtil
    
    def CharSequence generate(Enumeration enumeration, IFileSystemAccess2 fsa) {
        '''
            === Enumeration «enumeration.name»
            «enumeration.description»
            
            .«enumeration.name»
            |===
            |Name |Value
            
            «FOR literal : enumeration.literal»
                |«literal.name»
                |«literal.value.defaultValue»
            «ENDFOR»
            |===
        '''
    }
}