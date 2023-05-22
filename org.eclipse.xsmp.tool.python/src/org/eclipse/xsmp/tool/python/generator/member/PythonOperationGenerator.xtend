package org.eclipse.xsmp.tool.python.generator.member

import com.google.inject.Inject
import org.eclipse.xsmp.tool.python.generator.GeneratorExtension
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.ParameterDirectionKind
import org.eclipse.xsmp.xcatalogue.Parameter
import java.util.ArrayList

class PythonOperationGenerator {

	@Inject
	protected extension GeneratorExtension

	def CharSequence generate(Operation operation) {
		'''
			def «operation.name»(self«getParameters(operation)»):
				«IF !operation.description.isNullOrEmpty»"""«operation.description»"""«ENDIF»
				«IF operation.isVoid»«operation.generateVoidOperation»«ELSE»«operation.generateNonVoidOperation»«ENDIF»
		'''
	}

	def CharSequence generateNonVoidOperation(Operation operation) {
		'''
			«operation.generateResultClass»
				
			results = «operation.generateVoidOperation»
			return _Results(«FOR param : operation.resultsParams SEPARATOR ', '»results["«param.name»"]«ENDFOR»)
		'''
	}

	def CharSequence generateVoidOperation(Operation operation) {
		'''
			Smp.Operation(self, "«operation.name»")({
				«FOR param : operation.inputParams SEPARATOR ','»
					"«param.name»": «param.name»
				«ENDFOR»
			})
		'''
	}

	def CharSequence generateResultClass(Operation operation) {
		var params = operation.resultsParams
		'''
			
			class _Results:
				def __init__(self, «FOR param : params SEPARATOR ', '»«param.parameterName»«ENDFOR»):
					«FOR param : params SEPARATOR '\n'»self.«param.parameterName» = «param.parameterName»«ENDFOR»
		'''
	}

	def private CharSequence getParameters(Operation operation) {
		'''«FOR param : operation.inputParams BEFORE ', ' SEPARATOR ', '»«param.name»: «param.type.typeName»«ENDFOR»'''
	}

	def private Iterable<Parameter> getInputParams(Operation operation) {
		operation.parameter.filter[direction == ParameterDirectionKind.IN || direction == ParameterDirectionKind.INOUT]
	}

	def private Iterable<Parameter> getOutputParams(Operation operation) {
		operation.parameter.filter[direction == ParameterDirectionKind.INOUT || direction == ParameterDirectionKind.OUT]
	}

	def private Iterable<Parameter> getResultsParams(Operation operation) {
		var params = new ArrayList<Parameter>()

		if (operation.returnParameter !== null) {
			params.add(operation.returnParameter)
		}
		
		params.addAll(operation.outputParams)

		return params
	}

	def private String getParameterName(Parameter parameter) {
		parameter.name.equals("return") ? "_return" : parameter.name
	}

	def private boolean isVoid(Operation operation) {
		return operation.outputParams.empty && operation.returnParameter === null
	}
}
