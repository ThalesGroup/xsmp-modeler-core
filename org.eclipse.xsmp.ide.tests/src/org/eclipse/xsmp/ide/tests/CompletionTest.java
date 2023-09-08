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

import org.eclipse.xtext.testing.TestCompletionConfiguration;
import org.junit.jupiter.api.Test;

class CompletionTest extends AbstractXsmpcatLanguageServerTest
{
  @Test
  void testCompletion_01()
  {
    testCompletion((TestCompletionConfiguration it) -> {
      it.setModel("""
              catalogue TestCatalogue


              """);
      it.setLine(2);

      final var expected = """
              Create a Namespace -> namespace ${1:name}
              {
                  $0
              } // namespace ${1:name}
               [[2, 0] .. [2, 0]]
              documentation (ML_DOCUMENTATION) -> documentation [[2, 0] .. [2, 0]]
              import -> import [[2, 0] .. [2, 0]]
                            """;

      it.setExpectedCompletionItems(expected);
    });
  }

  @Test
  void testCompletion_02()
  {
    testCompletion((TestCompletionConfiguration it) -> {
      it.setModel("""
              catalogue TestCatalogue

              namespace TestNamespace
              {
                /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                model TestModel
                {

                }
              }
              """);
      it.setLine(7);

      final var expected = """
              Create a Constant -> constant ${1|None|} ${2:name} = $0
               [[7, 0] .. [7, 0]]
              Create a Container -> container ${1|TestModel,TestNamespace.TestModel|}[*] ${2:name}
               [[7, 0] .. [7, 0]]
              Create a Field -> field ${1|None|} ${2:name}
               [[7, 0] .. [7, 0]]
              Create a Property -> property ${1|None|} ${2:name}
               [[7, 0] .. [7, 0]]
              Create a Reference -> reference ${1|None|}[*] ${2:name}
               [[7, 0] .. [7, 0]]
              Create an Association -> association ${1|TestModel,TestNamespace.TestModel|} ${2:name}
               [[7, 0] .. [7, 0]]
              Create an EntryPoint -> entrypoint ${1:name}
               [[7, 0] .. [7, 0]]
              Create an Event Sink -> eventsink ${1|None|} ${2:name}
               [[7, 0] .. [7, 0]]
              Create an Event Source -> eventsource ${1|None|} ${2:name}
               [[7, 0] .. [7, 0]]
              Create an Operation -> def void ${1:name} ($0)
               [[7, 0] .. [7, 0]]
              documentation (ML_DOCUMENTATION) -> documentation [[7, 0] .. [7, 0]]
              } -> } [[7, 0] .. [7, 0]]
                            """;

      it.setExpectedCompletionItems(expected);
    });
  }

  @Test
  void testCompletion_03()
  {
    testCompletion((TestCompletionConfiguration it) -> {
      TestUtils.loadEcssSmpLibrary(it);

      it.setModel("""
              catalogue TestCatalogue

              namespace TestNamespace
              {
                /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                model TestModel
                {
                  def B
                }
              }
              """);
      it.setLine(7);
      it.setColumn(9);

      final var expected = """
              Bool (PrimitiveType) -> Bool [[7, 8] .. [7, 9]]
              Smp.Bool (PrimitiveType) -> Smp.Bool [[7, 8] .. [7, 9]]
              """;

      it.setExpectedCompletionItems(expected);
    });
  }

  @Test
  void testCompletion_04()
  {
    testCompletion((TestCompletionConfiguration it) -> {
      TestUtils.loadEcssSmpLibrary(it);

      it.setModel("""
              catalogue TestCatalogue

              namespace TestNamespace
              {
                /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                model TestModel
                {
                  field B
                }
              }
              """);
      it.setLine(7);
      it.setColumn(11);

      final var expected = """
              Bool (PrimitiveType) -> Bool [[7, 10] .. [7, 11]]
              Smp.Bool (PrimitiveType) -> Smp.Bool [[7, 10] .. [7, 11]]
                            """;

      it.setExpectedCompletionItems(expected);
    });
  }

  @Test
  void testCompletion_05()
  {
    testCompletion((TestCompletionConfiguration it) -> {
      TestUtils.loadEcssSmpLibrary(it);

      it.setModel("""
              catalogue TestCatalogue

              namespace TestNamespace
              {
                /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                struct TestModel
                {
                  field B
                }
              }
              """);
      it.setLine(7);
      it.setColumn(11);

      final var expected = """
              Bool (PrimitiveType) -> Bool [[7, 10] .. [7, 11]]
              Smp.Bool (PrimitiveType) -> Smp.Bool [[7, 10] .. [7, 11]]
                            """;

      it.setExpectedCompletionItems(expected);
    });
  }
}
