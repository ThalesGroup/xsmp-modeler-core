package org.eclipse.xsmp.tool.adoc.generator.member

import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.Container

class ADocContainerGenerator {
    
    def CharSequence generateMermaid(Component component) {
        val containers = component.member.filter(Container)
        '''
            «FOR container : containers»
                «component.name» "«container.lower»..«container.upper»" *-- «container.type.name» : «container.name»
            «ENDFOR»
        '''
    }
}