/*******************************************************************************
* Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.profile.esa_cdk;

import org.eclipse.xsmp.XsmpcatRuntimeModule;
import org.eclipse.xsmp.generator.cpp.CatalogueGenerator;
import org.eclipse.xsmp.generator.cpp.CppCopyrightNoticeProvider;
import org.eclipse.xsmp.generator.cpp.GeneratorUtil;
import org.eclipse.xsmp.generator.cpp.member.ConstantGenerator;
import org.eclipse.xsmp.generator.cpp.member.ContainerGenerator;
import org.eclipse.xsmp.generator.cpp.member.EntryPointGenerator;
import org.eclipse.xsmp.generator.cpp.member.EventSinkGenerator;
import org.eclipse.xsmp.generator.cpp.member.EventSourceGenerator;
import org.eclipse.xsmp.generator.cpp.member.ReferenceGenerator;
import org.eclipse.xsmp.generator.cpp.type.ArrayGenerator;
import org.eclipse.xsmp.generator.cpp.type.ComponentGenerator;
import org.eclipse.xsmp.generator.cpp.type.ExceptionGenerator;
import org.eclipse.xsmp.generator.cpp.type.StringGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.EsaCdkGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.EsaCdkOutputConfigurationProvider;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.EsaCdkCatalogueGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.EsaCdkCopyrightProvider;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.EsaCdkGeneratorExtension;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.member.EsaCdkConstantGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.member.EsaCdkContainerGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.member.EsaCdkEntryPointGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.member.EsaCdkEventSinkGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.member.EsaCdkEventSourceGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.member.EsaCdkReferenceGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.type.EsaCdkArrayGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.type.EsaCdkComponentGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.type.EsaCdkExceptionGenerator;
import org.eclipse.xsmp.profile.esa_cdk.generator.cpp.type.EsaCdkStringGenerator;
import org.eclipse.xsmp.profile.esa_cdk.validation.EsaCdkValidator;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.service.SingletonBinding;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class EsaCdkRuntimeModule extends XsmpcatRuntimeModule {

	@Override
	public Class<? extends IGenerator2> bindIGenerator2() {
		return EsaCdkGenerator.class;
	}

	@SingletonBinding(eager = true)
	public Class<? extends EsaCdkValidator> bindEsaCdkValidator() {
		return EsaCdkValidator.class;
	}

	@Override
	public Class<? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider() {
		return EsaCdkOutputConfigurationProvider.class;
	}

	public Class<? extends EntryPointGenerator> bindEntryPointGenerator() {
		return EsaCdkEntryPointGenerator.class;
	}

	public Class<? extends EventSinkGenerator> bindEventSinkGenerator() {
		return EsaCdkEventSinkGenerator.class;
	}

	public Class<? extends EventSourceGenerator> bindEventSourceGenerator() {
		return EsaCdkEventSourceGenerator.class;
	}

	public Class<? extends ReferenceGenerator> bindReferenceGenerator() {
		return EsaCdkReferenceGenerator.class;
	}

	public Class<? extends ContainerGenerator> bindConatinerGenerator() {
		return EsaCdkContainerGenerator.class;
	}

	public Class<? extends ComponentGenerator> bindComponentGenerator() {
		return EsaCdkComponentGenerator.class;
	}

	public Class<? extends CatalogueGenerator> bindCatalogueGenerator() {
		return EsaCdkCatalogueGenerator.class;
	}

	public Class<? extends ArrayGenerator> bindArrayGenerator() {
		return EsaCdkArrayGenerator.class;
	}

	public Class<? extends StringGenerator> bindStringGenerator() {
		return EsaCdkStringGenerator.class;
	}

	public Class<? extends ExceptionGenerator> bindExceptionGenerator() {
		return EsaCdkExceptionGenerator.class;
	}

	public Class<? extends ConstantGenerator> bindConstantGenerator() {
		return EsaCdkConstantGenerator.class;
	}

	public Class<? extends GeneratorUtil> bindGeneratorExtension() {
		return EsaCdkGeneratorExtension.class;
	}

	public Class<? extends CppCopyrightNoticeProvider> bindCopyrightProvider() {
		return EsaCdkCopyrightProvider.class;
	}

}
