package org.eclipse.xsmp.tool.adoc.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.tool.adoc.generator.member.ADocFieldGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocStructureGenerator {
    
    @Inject
    ADocFieldGenerator fieldGenerator
    
    def CharSequence generate(Structure structure, IFileSystemAccess2 fsa) {
        var fields = structure.member.filter(Field)
        '''
            === Structure «structure.name»
            «structure.description»
            
            .«structure.name»
            |===
            |Name |Comment |Type |Unit |Initial Value
            
            «FOR field : fields»
                «fieldGenerator.generate(field)»
            «ENDFOR»
            |===
        '''
    }
}
