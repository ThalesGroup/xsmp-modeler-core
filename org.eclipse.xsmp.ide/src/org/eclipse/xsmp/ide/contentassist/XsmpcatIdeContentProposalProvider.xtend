/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.contentassist

import com.google.common.base.Joiner
import com.google.common.collect.Iterables
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xsmp.xcatalogue.XcataloguePackage
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor

class XsmpcatIdeContentProposalProvider extends AbstractIdeContentProposalProvider {

	int snippetPriority = 400;
	
	def protected complete_association(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			association ${1|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.ASSOCIATION__TYPE)»|} ${2:name}
		''', "Create an Association", context), snippetPriority);
	}

	def protected complete_catalogue(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/**
			* Specifies the SMP Component Model as Catalogue.
			*
			* @date «Instant.now.truncatedTo(ChronoUnit.SECONDS).toString»
			* @author «System.getProperty("user.name")»
			* @title Catalogue
			* @version 1.0
			*/
			catalogue ${1:name}
		''', "Create a Catalogue", context), snippetPriority);
	}

	def protected complete_class(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			class ${1:name}
			{
			    $0
			}
		''', "Create a Class", context), snippetPriority);

	}

	def protected complete_constant(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			constant ${1|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.CONSTANT__TYPE)»|} ${2:name} = $0
		''', "Create a Constant", context), snippetPriority);
	}

	def protected complete_container(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			container ${1|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.CONTAINER__TYPE)»|}[*] ${2:name}
		''', "Create a Container", context), snippetPriority);
	}

	def protected complete_def(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			def void ${1:name} ($0)
		''', "Create an Operation", context), snippetPriority);
	}

	def protected complete_entrypoint(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			entrypoint ${1:name}
		''', "Create an EntryPoint", context), snippetPriority);
	}

	def protected complete_enum(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			enum ${1:name}
			{
			    $0 = 0
			}
		''', "Create an Enumeration", context), snippetPriority);
	}

	def protected complete_event(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			event ${1:name}
		''', "Create an Event Type", context), snippetPriority);
	}

	def protected complete_eventsink(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			eventsink ${1|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.EVENT_SINK__TYPE)»|} ${2:name}
		''', "Create an Event Sink", context), snippetPriority);
	}

	def protected complete_eventsource(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			eventsource ${1|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.EVENT_SOURCE__TYPE)»|} ${2:name}
		''', "Create an Event Source", context), snippetPriority);
	}

	def protected complete_exception(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			exception ${1:name} 
			{
			    $0
			}
		''', "Create an Exception", context), snippetPriority);
	}

	def protected complete_field(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			field ${1|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.FIELD__TYPE)»|} ${2:name}
		''', "Create a Field", context), snippetPriority);
	}

	def protected complete_integer(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			integer ${1:name}
		''', "Create an Integer type", context), snippetPriority);
	}

	def protected complete_float(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			float ${1:name}
		''', "Create a Float", context), snippetPriority);
	}

	def protected complete_interface(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			interface ${1:name} 
			{
			    $0
			}
		''', "Create an Interface", context), snippetPriority);
	}

	def protected complete_model(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			model ${1:name}
			{
			    $0
			}
		''', "Create a Model", context), snippetPriority);
	}

	def protected complete_namespace(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			namespace ${1:name}
			{
			    $0
			} // namespace ${1:name}
		''', "Create a Namespace", context), snippetPriority);
	}

	def protected complete_property(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			property ${1|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.PROPERTY__TYPE)»|} ${2:name}
		''', "Create a Property", context), snippetPriority);
	}

	def protected complete_reference(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			reference ${1|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.REFERENCE__INTERFACE)»|}[*] ${2:name}
		''', "Create a Reference", context), snippetPriority);
	}

	def protected complete_service(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			service ${1:name}
			{
			    $0
			}
		''', "Create a Service", context), snippetPriority);
	}

	def protected complete_string(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			string ${1:name}[$0]
		''', "Create a String Type", context), snippetPriority);
	}

	def protected complete_structure(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			struct ${1:name}
			{
			    $0
			}
		''', "Create a Structure", context), snippetPriority);
	}

	def protected complete_valueReference(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			using ${1:name} = ${2|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.VALUE_REFERENCE__TYPE)»|}
		''', "Create a Value Reference", context), snippetPriority);
	}

	def protected complete_array(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			array ${1:name} = ${2|«getCrossReferences(context.currentModel, XcataloguePackage.Literals.ARRAY__ITEM_TYPE)»|}[$0]
		''', "Create an Array Type", context), snippetPriority);
	}

	def private String getCrossReferences(EObject eObject, EReference eReference) {
		val scope = scopeProvider.getScope(eObject, eReference)
		val filteredCandidates = Iterables.filter(scope.allElements, filter.getFilter(eObject, eReference))
		
		if (filteredCandidates.empty) {
			return "None"
		}

		return Joiner.on(",").join(filteredCandidates.map[it|it.name])
	}

	def private String generateUuid() {
		UUID.randomUUID.toString
	}
}
