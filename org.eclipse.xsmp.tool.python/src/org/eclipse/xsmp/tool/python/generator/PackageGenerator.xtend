package org.eclipse.xsmp.tool.python.generator

import org.eclipse.xsmp.xcatalogue.Catalogue
import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.Namespace
import org.eclipse.xsmp.xcatalogue.Model
import org.eclipse.emf.ecore.EObject

class PackageGenerator {

    @Inject
    PythonCopyrightNoticeProvider copyright

    def protected CharSequence generate(Catalogue cat) {
        '''
            «copyright.generate(cat.name, cat, true)»
            
            import ecss_smp_smp
            import types
            
            
            «FOR member : cat.member»
                «member.doGenerate»
            «ENDFOR»
        '''
    }

    def dispatch protected CharSequence doGenerate(EObject ns) {
        // ignore other elements
    }

    def dispatch protected CharSequence doGenerate(Namespace ns) {
        '''
            class «ns.name»(types.ModuleType):
                «IF !ns.description.empty»
                    «"'''"»
                    «ns.description»
                    «"'''"»
                    
                «ENDIF»
                «FOR member : ns.member»
                    «member.doGenerate»
                «ENDFOR»
                
                pass
            
        '''
    }

    def dispatch protected CharSequence doGenerate(Model model) {
        '''
            class «model.name»(ecss_smp_smp.Smp.IModel):
                «IF !model.description.empty»
                    «"'''"»
                    «model.description»
                    «"'''"»
                    
                «ENDIF»
                uuid:ecss_smp_smp.Smp.Uuid = ecss_smp_smp.Smp.Uuid("«model.uuid»")
                package_name:str = __name__
            
        '''
    }
}
