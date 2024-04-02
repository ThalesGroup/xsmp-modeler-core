package org.eclipse.xsmp.tool.adoc.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.EventSink
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocEventSinkGenerator {

    @Inject extension ADocUtil

    def CharSequence generateContent(Component component, IFileSystemAccess2 fsa) {
        val eventSinks = component.member.filter(EventSink)
        '''
            «IF !eventSinks.empty»
                ==== Event Sinks
                The model shall implement the events defined below.
                 
                .Model Event Sink
                |===
                |Name |Comment
                
                «FOR eventSink : eventSinks»
                    «eventSink.generate»
                «ENDFOR»
                |===
            «ENDIF»
        '''
    }

    def protected CharSequence generate(EventSink eventSink) {
        '''
            |«eventSink.name»
            |«eventSink.description.formatDescription»
            |«eventSink.type.fqn.toString("::")»
        '''
    }
    
    def CharSequence generateMermaid(EventSink eventSink) {
        '''
            eventsink «eventSink.name»: «eventSink.type.name»
        '''
    }
}
