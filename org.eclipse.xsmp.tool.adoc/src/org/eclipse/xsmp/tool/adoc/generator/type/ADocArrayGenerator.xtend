package org.eclipse.xsmp.tool.adoc.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.tool.adoc.ADocUtil

class ADocArrayGenerator {
    
    @Inject extension ADocUtil
/*
    def CharSequence generateContent(Namespace namespace, IFileSystemAccess2 fsa) {
        val arrays = namespace.member.filter(Array)
        '''
            == Arrays
            «IF arrays.empty»None.«ELSE»
            
            .Arrays
            |===
            |Name |Type |Size
            
            «FOR array : arrays»
                «array.generate»
            «ENDFOR»
            |===
            
            «ENDIF»
        '''
    }*/
    
    def protected CharSequence generate(Array array) {
        '''
            
            |«array.name»
            |«array.itemType.fqn.toString("::")»
            |«array.size.value»
        '''
    }
}
