package org.eclipse.xsmp.tool.adoc.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.EntryPoint
import org.eclipse.xsmp.model.xsmp.EventSink
import org.eclipse.xsmp.model.xsmp.EventSource
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.Operation
import org.eclipse.xsmp.tool.adoc.ADocUtil
import org.eclipse.xsmp.tool.adoc.generator.member.ADocContainerGenerator
import org.eclipse.xsmp.tool.adoc.generator.member.ADocEntryPointGenerator
import org.eclipse.xsmp.tool.adoc.generator.member.ADocEventSinkGenerator
import org.eclipse.xsmp.tool.adoc.generator.member.ADocEventSourceGenerator
import org.eclipse.xsmp.tool.adoc.generator.member.ADocFieldGenerator
import org.eclipse.xsmp.tool.adoc.generator.member.ADocOperatorGenerator
import org.eclipse.xsmp.tool.adoc.generator.member.ADocReferenceGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2

class ADocComponentGenerator {

    @Inject extension ADocUtil

    @Inject
    ADocFieldGenerator fieldGenerator;

    @Inject
    ADocEventSourceGenerator eventSourceGenerator;

    @Inject
    ADocEventSinkGenerator eventSinkGenerator;

    @Inject
    ADocEntryPointGenerator entryPointGenerator;

    @Inject
    ADocReferenceGenerator referenceGenerator;

    @Inject
    ADocContainerGenerator containerGenerator;

    @Inject
    ADocOperatorGenerator operatorGenerator;

    def CharSequence generate(Component component, IFileSystemAccess2 fsa) {
        '''
            === Component «component.fqn.toString("::")»
            «component.description»
            
            «component.generateMermaid»
            
            «fieldGenerator.generateContent(component, fsa)»
            
            «eventSourceGenerator.generateContent(component, fsa)»
            
            «eventSinkGenerator.generateContent(component, fsa)»
            
            «entryPointGenerator.generateContent(component, fsa)»
            
            «referenceGenerator.generateContent(component, fsa)»
            
            «operatorGenerator.generateContent(component, fsa)»
        '''
    }

    def CharSequence generateMermaid(Component component) {
        val interfaces = component.interface
        val base = component.base
        '''
            ==== Diagram
            
            [.center]
            [mermaid]
            ....
            classDiagram
                «IF base !== null»
                    class «base.name» {
                    }
                «ENDIF»
                
                «FOR interf : interfaces»
                    class «interf.name» {
                        <<interface>>
                    }
                «ENDFOR»
                
                class «component.name» {
                    «component.mermaidClassAttributes»
                }
                
                «IF base !== null»«base.name» <|-- «component.name»«ENDIF»
                «FOR interf : interfaces»
                    «interf.name» <|.. «component.name»
                «ENDFOR»
                «referenceGenerator.generateMermaid(component)»
                «containerGenerator.generateMermaid(component)»
            ....
        '''
    }

    private def CharSequence mermaidClassAttributes(Component component) {
        val sb = new StringBuilder
        val nbAttributes = component.member.size
        val members = component.member.subList(0, Math.min(nbAttributes, 10))

        for (elem : members) {
            switch elem {
                Field: sb.append(fieldGenerator.generateMermaid(elem))
                EventSource: sb.append(eventSourceGenerator.generateMermaid(elem))
                EventSink: sb.append(eventSinkGenerator.generateMermaid(elem))
                EntryPoint: sb.append(entryPointGenerator.generateMermaid(elem))
                Operation: sb.append(operatorGenerator.generateMermaid(elem))
            }
        }

        if (nbAttributes > 10) {
            sb.append("...")
        }

        return sb.toString
    }
}
