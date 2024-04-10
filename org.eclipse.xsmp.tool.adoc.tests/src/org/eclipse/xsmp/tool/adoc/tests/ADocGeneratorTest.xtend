/*******************************************************************************
 * Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.tool.adoc.tests

import com.google.inject.Inject
import com.google.inject.Provider
import java.io.BufferedReader
import java.io.InputStreamReader
import org.eclipse.emf.common.util.URI
import org.eclipse.xsmp.tool.adoc.generator.ADocOutputConfigurationProvider
import org.eclipse.xsmp.model.xsmp.Catalogue
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
@InjectWith(ADocInjectorProvider)
class ADocGeneratorTest {
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

        assertEquals(1, fsa.getTextFiles.size)
        assertTrue(fsa.getTextFiles.containsKey(ADocOutputConfigurationProvider::DOC + "Test-gen.adoc"))

        try ( val br_generated = new BufferedReader(new InputStreamReader(fsa.readBinaryFile("Test-gen.adoc", ADocOutputConfigurationProvider::DOC )))) {
            try ( val br_ref = new BufferedReader(new InputStreamReader(getClass().getResource("Test-gen.adoc").openStream))) {
                assertLinesMatch(br_ref.lines(), br_generated.lines());
            }
        }

    }
}
