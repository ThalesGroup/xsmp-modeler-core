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
import org.eclipse.xsmp.model.xsmp.XsmpPackage
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor

class XsmpcatIdeContentProposalProvider extends XsmpIdeContentProposalProvider {

	int snippetPriority = 400;
	
	def protected complete_association(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			association ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.ASSOCIATION__TYPE)»|} ${2:name}
		''', "association", context), snippetPriority);
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
		''', "catalogue", context), snippetPriority);
	}

	def protected complete_class(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			class ${1:name}
			{
			    $0
			}
		''', "class", context), snippetPriority);

	}

	def protected complete_constant(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			constant ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.CONSTANT__TYPE)»|} ${2:name} = $0
		''', "constant", context), snippetPriority);
	}

	def protected complete_container(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			container ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.CONTAINER__TYPE)»|}[*] ${2:name}
		''', "container", context), snippetPriority);
	}

	def protected complete_def(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			def void ${1:name} ($0)
		''', "def", context), snippetPriority);
	}

	def protected complete_entrypoint(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			entrypoint ${1:name}
		''', "entrypoint", context), snippetPriority);
	}

	def protected complete_enum(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			enum ${1:name}
			{
			    $0 = 0
			}
		''', "enum", context), snippetPriority);
	}

	def protected complete_event(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			event ${1:name}
		''', "event", context), snippetPriority);
	}

	def protected complete_eventsink(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			eventsink ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.EVENT_SINK__TYPE)»|} ${2:name}
		''', "eventsink", context), snippetPriority);
	}

	def protected complete_eventsource(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			eventsource ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.EVENT_SOURCE__TYPE)»|} ${2:name}
		''', "eventsource", context), snippetPriority);
	}

	def protected complete_exception(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			exception ${1:name} 
			{
			    $0
			}
		''', "exception", context), snippetPriority);
	}

	def protected complete_field(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			field ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.FIELD__TYPE)»|} ${2:name}
		''', "field", context), snippetPriority);
	}

	def protected complete_integer(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			integer ${1:name}
		''', "integer", context), snippetPriority);
	}

	def protected complete_float(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			float ${1:name}
		''', "float", context), snippetPriority);
	}

	def protected complete_interface(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			interface ${1:name} 
			{
			    $0
			}
		''', "interface", context), snippetPriority);
	}

	def protected complete_model(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			model ${1:name}
			{
			    $0
			}
		''', "model", context), snippetPriority);
	}

	def protected complete_namespace(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			namespace ${1:name}
			{
			    $0
			} // namespace ${1:name}
		''', "namespace", context), snippetPriority);
	}

	def protected complete_property(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			property ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.PROPERTY__TYPE)»|} ${2:name}
		''', "property", context), snippetPriority);
	}

	def protected complete_reference(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			reference ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.REFERENCE__INTERFACE)»|}[*] ${2:name}
		''', "reference", context), snippetPriority);
	}

	def protected complete_service(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			service ${1:name}
			{
			    $0
			}
		''', "service", context), snippetPriority);
	}

	def protected complete_string(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			string ${1:name}[$0]
		''', "string", context), snippetPriority);
	}

	def protected complete_structure(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			struct ${1:name}
			{
			    $0
			}
		''', "struct", context), snippetPriority);
	}

	def protected complete_valueReference(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			using ${1:name} = ${2|«getCrossReferences(context.currentModel, XsmpPackage.Literals.VALUE_REFERENCE__TYPE)»|}
		''', "using", context), snippetPriority);
	}

	def protected complete_array(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** @uuid «generateUuid» */
			array ${1:name} = ${2|«getCrossReferences(context.currentModel, XsmpPackage.Literals.ARRAY__ITEM_TYPE)»|}[$0]
		''', "array", context), snippetPriority);
	}
	def protected complete_nativeType(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** 
			 * @type native_type
			 * @location native_location
			 * @namespace native_namespace
			 * @uuid «generateUuid» 
			 */
			native ${1:name}
		''', "native", context), snippetPriority);
	}
		def protected complete_attributeType(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptor.accept(getProposalCreator().createSnippet('''
			/** 
			 * // @allowMultiple
			 * @usage ...
			 * @uuid «generateUuid» 
			 */
			attribute ${1|«getCrossReferences(context.currentModel, XsmpPackage.Literals.ATTRIBUTE_TYPE__TYPE)»|} ${2:name} = $0
		''', "attribute", context), snippetPriority);
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
