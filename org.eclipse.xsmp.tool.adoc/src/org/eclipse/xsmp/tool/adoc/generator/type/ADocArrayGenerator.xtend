package org.eclipse.xsmp.tool.adoc.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xtext.generator.IFileSystemAccess2

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
    
    def protected CharSequence generate2(Array array) {
        '''
            
            |«array.name»
            |«array.itemType.fqn.toString("::")»
            |«array.size.value»
        '''
    }
    
    def CharSequence generate(Array array, IFileSystemAccess2 fsa) {
        '''
            === Array «array.name»
            «array.description»
            
            .«array.name»
            |===
            |Type |Size
            |«array.itemType.fqn.toString("::")»
            |«array.size.value»
            |===
        '''
    }
}
