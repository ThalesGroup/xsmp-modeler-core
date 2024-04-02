package org.eclipse.xsmp.tool.adoc.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.Operation
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocOperatorGenerator {

    @Inject extension ADocUtil

    def CharSequence generateContent(Component component, IFileSystemAccess2 fsa) {
        val operations = component.member.filter(Operation)
        '''
            «IF !operations.empty»
                ==== Operations
                The model shall implement the operations defined below.
                 
                .Operations
                |===
                |Name |Parameter |Comment
                
                «FOR operation : operations»
                    «operation.generate»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def protected CharSequence generate(Operation operation) {
        '''
            |«operation.name»
            |«FOR param : operation.parameter SEPARATOR ','»«param.type.fqn.toString("::")» «param.name» («param.direction»)«ENDFOR»
            |«operation.description.formatDescription»
        '''
    }
    
    def CharSequence generateMermaid(Operation operation) {
        '''
            «operation.name»(«FOR param : operation.parameter SEPARATOR ','»«param.direction» «param.type.name»«ENDFOR») «IF operation.returnParameter === null »void«ELSE»«operation.returnParameter.type.name»«ENDIF»
        '''
    }
}
