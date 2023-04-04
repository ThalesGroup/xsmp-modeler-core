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
package org.eclipse.xsmp.tool.smp.tests

import com.google.inject.Inject
import com.google.inject.Provider
import java.io.BufferedReader
import java.io.InputStreamReader
import org.eclipse.emf.common.util.URI
import org.eclipse.xsmp.tool.smp.generator.SmpOutputConfigurationProvider
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xtext.generator.GeneratorContext
import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.xtext.generator.InMemoryFileSystemAccess
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.util.CancelIndicator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

import static org.junit.jupiter.api.Assertions.*

@ExtendWith(InjectionExtension)
@InjectWith(SmpInjectorProvider)
class XsmpcatGeneratorTest {
    @Inject ParseHelper<Catalogue> parsehelper
    @Inject
    Provider<XtextResourceSet> resourceSetProvider;

    @Inject IGenerator2 generator

    @Test
    def test() {

        var rs = resourceSetProvider.get
        parsehelper.parse(getClass().getResource("/org/eclipse/xsmp/lib/ecss.smp.xsmpcat").openStream,
            URI.createURI("ecss.smp.xsmpcat"), null, rs)

        val model = parsehelper.parse(getClass().getResource("Test.xsmpcat").openStream, URI.createURI("Test.xsmpcat"),
            null, rs)

        val fsa = new InMemoryFileSystemAccess()
        var context = new GeneratorContext();
        context.setCancelIndicator(CancelIndicator.NullImpl);
        generator.doGenerate(model.eResource, fsa, context)

        assertEquals(2, fsa.getTextFiles.size)
        assertTrue(fsa.getTextFiles.containsKey(SmpOutputConfigurationProvider::SMDL_GEN + "Test.smpcat"))

        try ( val br_generated = new BufferedReader(new InputStreamReader(fsa.readBinaryFile("Test.smpcat", SmpOutputConfigurationProvider::SMDL_GEN )))) {
            try ( val br_ref = new BufferedReader(new InputStreamReader(getClass().getResource("Test.smpcat").openStream))) {
                assertLinesMatch(br_ref.lines(), br_generated.lines());
            }
        }

        assertTrue(fsa.getTextFiles.containsKey(SmpOutputConfigurationProvider::SMDL_GEN + "Test.smppkg"))
        try ( val br_generated = new BufferedReader(new InputStreamReader(fsa.readBinaryFile("Test.smppkg", SmpOutputConfigurationProvider::SMDL_GEN )))) {
            try ( val br_ref = new BufferedReader(new InputStreamReader(getClass().getResource("Test.smppkg").openStream))) {
                assertLinesMatch(br_ref.lines(), br_generated.lines());
            }
        }

    }
}
