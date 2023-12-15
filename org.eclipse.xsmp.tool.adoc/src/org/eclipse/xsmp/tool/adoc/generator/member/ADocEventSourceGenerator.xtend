package org.eclipse.xsmp.tool.adoc.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.EventSource
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocEventSourceGenerator {

    @Inject extension ADocUtil

    def CharSequence generateContent(Component component, IFileSystemAccess2 fsa) {
        val eventSources = component.member.filter(EventSource)
        '''
            «IF !eventSources.empty»
                === Event Source
                The model shall implement the events defined below.
                 
                .Model Event Source
                |===
                |Name |Comment
                
                «FOR eventSource : eventSources»
                    «eventSource.generate»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def protected CharSequence generate(EventSource eventSource) {
        '''
            |«eventSource.name»
            |«eventSource.description.formatDescription»
            |«eventSource.type.fqn.toString("::")»
        '''
    }
}
