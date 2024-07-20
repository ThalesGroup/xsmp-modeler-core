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
package org.eclipse.xsmp.ide.tests;

import java.util.List;

import org.eclipse.lsp4j.Diagnostic;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

class ValidatorTest extends AbstractXsmpcatLanguageServerTest
{
  @Test
  void testMissingUuid()
  {
    final var model = """
            catalogue TestCatalogue

            namespace TestNamespace
            {
              model TestModel
              {
                def void ope_test ()
              }
            }
            """;

    writeFile("test1.xsmpcat", model);
    initialize();

    final var problems = problems();
    Assert.assertEquals(1, problems.size());

    final var problem = Iterables.getFirst(problems, null);
    assertEquals("Missing Type UUID.", problem.getMessage());
  }

  @Test
  void testWrongKeyword()
  {
    final var model = """
            catalogue TestCatalogue

            namespace TestNamespace
            {
              integger TestWrongInteger
            }
            """;

    writeFile("test2.xsmpcat", model);
    initialize();

    final var problems = problems();
    Assert.assertEquals(1, problems.size());

    final var problem = Iterables.getFirst(problems, null);
    assertEquals("mismatched input 'integger' expecting '}'", problem.getMessage());
  }

  @Test
  void testVisibilityProblem()
  {
    final var model = """
            catalogue TestCatalogue

            namespace TestNamespace1
            {
                /** @uuid ab7de841-e453-4e44-ad89-b111125af9cd */
                model MyFirstModel
                {

                }

            }

            namespace TestNamespace2
            {
                /** @uuid 89c3e6f7-448c-43ab-90bb-e774a49e8df3 */
                model MySndModel extends TestNamespace1.MyFirstModel
                {

                }

            }
            """;

    writeFile("test3.xsmpcat", model);
    initialize();

    final var problems = problems();
    Assert.assertEquals(1, problems.size());

    final var problem = Iterables.getFirst(problems, null);
    assertEquals("The Model TestNamespace1.MyFirstModel is not visible.", problem.getMessage());
  }

  private List<Diagnostic> problems()
  {
    return Iterables.getFirst(getDiagnostics().entrySet(), null).getValue();
  }
}
