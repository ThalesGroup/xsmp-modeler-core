package org.eclipse.xsmp.tool.python.generator

import com.google.inject.Inject
import java.util.Set
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.XcataloguePackage
import java.util.HashSet
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.Enumeration
import org.eclipse.xsmp.xcatalogue.Integer
import org.eclipse.xsmp.xcatalogue.Float
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.EventType
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.EventSource
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xsmp.xcatalogue.Document

class ImportGenerator {
	
	@Inject
	protected extension GeneratorExtension

	def protected CharSequence generate(NamedElement element) {
		'''
			«FOR imp : element.collectImports»
				import «imp»
			«ENDFOR»
		'''
	}

	def protected CharSequence generateUserCode(NamedElement element) {
		'''
			import «element.fqn.toString(".")»Gen
		'''
	}

	def dispatch protected Set<String> collectImports(NamedElement element) {
		var imports = new HashSet<String>

		imports.add("Smp")

		return imports
	}

	def dispatch protected Set<String> collectImports(NamedElementWithMembers element) {
		var imports = new HashSet<String>

		var fields = element.member.filter(typeof(Field))
		var eventSinks = element.member.filter(typeof(EventSink))
		var eventSources = element.member.filter(typeof(EventSource))

		imports.add("Smp")

		for (field : fields) {
			var type = field.type.eClass().getClassifierID()

			if (type != XcataloguePackage.PRIMITIVE_TYPE)
				imports.add(field.type.fqn.toString("."))
		}

		for (esi : eventSinks) {
			imports.add(esi.type.fqn.toString("."))
		}

		for (eso : eventSources) {
			imports.add(eso.type.fqn.toString("."))
		}

		return imports
	}

	def dispatch protected Set<String> collectImports(Component element) {
		var imports = _collectImports(element as NamedElementWithMembers)

		if (element.base !== null)
			imports.add(element.base.fqn.toString("."))
			
		var cat = EcoreUtil2.getContainerOfType(element, Document)
		imports.add(cat.name + "._Package")

		return imports
	}

	def dispatch protected Set<String> collectImports(Array element) {
		var imports = new HashSet<String>
		imports.add("Smp")

		var itemType = element.getItemType.eClass().getClassifierID()

		if (itemType != XcataloguePackage.PRIMITIVE_TYPE)
			imports.add(element.itemType.fqn.toString("."))

		return imports
	}

	def dispatch protected Set<String> collectImports(Enumeration element) {
		var imports = new HashSet<String>

		imports.add("Smp")
		imports.add("enum")

		return imports
	}
	
	def dispatch protected Set<String> collectImports(org.eclipse.xsmp.xcatalogue.String element) {
		var imports = new HashSet<String>

		imports.add("Smp")

		return imports
	}

	def dispatch protected Set<String> collectImports(Integer element) {
		var imports = new HashSet<String>

		imports.add("Smp")
		imports.add("typing")

		return imports
	}
	
	def dispatch protected Set<String> collectImports(Float element) {
		var imports = new HashSet<String>

		imports.add("Smp")
		imports.add("typing")

		return imports
	}

	def dispatch protected Set<String> collectImports(EventType element) {
		var imports = new HashSet<String>
		
		imports.add("typing")

		if (element.eventArgs !== null) {
			var eventArgsType = element.eventArgs.eClass().getClassifierID()
			
			if (eventArgsType == XcataloguePackage.PRIMITIVE_TYPE)
				imports.add("Smp")
			else
				imports.add(element.eventArgs.fqn.toString("."))
		}

		return imports
	}
}