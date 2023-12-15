package org.eclipse.xsmp.tool.adoc.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.EntryPoint
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocEntryPointGenerator {

    @Inject extension ADocUtil

    def CharSequence generateContent(Component component, IFileSystemAccess2 fsa) {
        val entryPoints = component.member.filter(EntryPoint)
        '''
            «IF !entryPoints.empty»
                === Entry Points
                The model shall implement the entry points defined below.
                 
                .Entry Points
                |===
                |Name |Comment
                
                «FOR entryPoint : entryPoints»
                    «entryPoint.generate»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def protected CharSequence generate(EntryPoint entryPoint) {
        '''
            |«entryPoint.name»
            |«entryPoint.description.formatDescription»
        '''
    }
}
