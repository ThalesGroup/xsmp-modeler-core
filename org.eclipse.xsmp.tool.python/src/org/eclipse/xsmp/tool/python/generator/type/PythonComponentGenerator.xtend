package org.eclipse.xsmp.tool.python.generator.type

import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.AbstractFileGenerator
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.tool.python.generator.member.PythonContainerGenerator
import org.eclipse.xsmp.tool.python.generator.member.PythonEntryPointGenerator
import org.eclipse.xsmp.tool.python.generator.member.PythonEventSinkGenerator
import org.eclipse.xsmp.tool.python.generator.member.PythonEventSourceGenerator
import org.eclipse.xsmp.tool.python.generator.member.PythonFieldGenerator
import org.eclipse.xsmp.tool.python.generator.member.PythonReferenceGenerator
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.Container
import org.eclipse.xsmp.xcatalogue.Document
import org.eclipse.xsmp.xcatalogue.EntryPoint
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.EventSource
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.Reference
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xsmp.tool.python.generator.member.PythonOperationGenerator
import org.eclipse.xsmp.tool.python.generator.member.PythonConstantGenerator

class PythonComponentGenerator extends AbstractFileGenerator<Component> {

	@Inject
	PythonOperationGenerator opGenerator

	@Inject
	PythonFieldGenerator fieldGenerator;

	@Inject
	PythonContainerGenerator containerGenerator

	@Inject
	PythonReferenceGenerator referenceGenerator

	@Inject
	PythonEntryPointGenerator entryPointGenetor

	@Inject
	PythonEventSinkGenerator eventSinkGenerator

	@Inject
	PythonEventSourceGenerator eventSourceGenerator

	@Inject
	PythonConstantGenerator constantGenerator;

	@Inject
	protected extension GeneratorExtension

	override protected CharSequence generate(Component component) {
		'''		
			class Type(«IF component.base === null»Smp.Component«ELSE»«component.base.typeName»«ENDIF»):
				«component.generateAttributes»
			
				def __init__(self, container, name, description=""):
					super().__init__(container, name, description)
				
				«component.generateOperations»
				«component.generateClassMethods»
		'''
	}

	def private Iterable<Operation> getOperations(Component component) {
		return component.member.filter(typeof(Operation))
	}

	def private CharSequence generateAttributes(Component component) {
		'''
			«FOR field : component.member.filter(typeof(Field))»«fieldGenerator.generate(field)»«ENDFOR»
			«FOR cst : component.member.filter(typeof(Constant)) AFTER '\n'»«constantGenerator.generate(cst)»«ENDFOR»
			«FOR container : component.member.filter(typeof(Container))»«containerGenerator.generate(container)»«ENDFOR»
			«FOR ref : component.member.filter(typeof(Reference)) AFTER '\n'»«referenceGenerator.generate(ref)»«ENDFOR»
			«FOR ept : component.member.filter(typeof(EntryPoint))»«entryPointGenetor.generate(ept)»«ENDFOR»
			«FOR esi : component.member.filter(typeof(EventSink))»«eventSinkGenerator.generate(esi)»«ENDFOR»
			«FOR eso : component.member.filter(typeof(EventSource))»«eventSourceGenerator.generate(eso)»«ENDFOR»
		'''
	}

	def private CharSequence generateOperations(Component component) {
		var operations = getOperations(component);

		'''
			«IF !operations.empty»
				# ------------ Operations ------------
				
				«FOR operation : operations SEPARATOR '\n' AFTER '\n'»
					«opGenerator.generate(operation)»
				«ENDFOR»				
			«ENDIF»
		'''
	}

	def private CharSequence generateClassMethods(Component component) {
		'''
			# ---------- Class methods ----------
						
			@classmethod
			def create(cls, container, name, description=""):
				return cls(container, name, description)
							
			@classmethod
			def get_uuid(cls):
				return "«component.uuid»"
							
			@classmethod
			def get_package(cls):
				return «EcoreUtil2.getContainerOfType(component, Document).name»._Package
		'''
	}

	override protected CharSequence generateUserCode(Component type) {
		'''
			class Type(«type.fqn.toString(".")»Gen.Type):
						
				def __init__(self, container, name, description=""):	
					super().__init__(container, name, description)
		'''
	}
}
