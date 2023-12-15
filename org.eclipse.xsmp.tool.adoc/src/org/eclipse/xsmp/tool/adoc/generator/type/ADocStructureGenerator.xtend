package org.eclipse.xsmp.tool.adoc.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.Namespace
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xsmp.tool.adoc.generator.member.ADocFieldGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocStructureGenerator {
    
    @Inject extension ADocUtil
    
    @Inject
    ADocFieldGenerator fieldGenerator

    def CharSequence generateContent(Namespace namespace, IFileSystemAccess2 fsa) {
        val structures = namespace.member.filter(Structure)
        '''
            == Structures
            «IF structures.empty»None.«ELSE»
            
            «FOR structure : structures SEPARATOR '\n'»
                «structure.generate(fsa)»
            «ENDFOR»
            
            «ENDIF»
        '''
    }
    
    def protected CharSequence generate(Structure structure, IFileSystemAccess2 fsa) {
        var fields = structure.member.filter(Field)
        '''
            .«structure.fqn.toString("::")»
            |===
            |Name |Comment |Type |Unit |Initial Value
            
            «FOR field : fields»
                «fieldGenerator.generate(field)»
            «ENDFOR»
            |===
        '''
    }
}
