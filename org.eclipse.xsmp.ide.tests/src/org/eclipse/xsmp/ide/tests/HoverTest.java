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

import org.eclipse.xtext.testing.HoverTestConfiguration;
import org.junit.jupiter.api.Test;

class HoverTest extends AbstractXsmpcatLanguageServerTest
{
  @Test
  void testHover_1()
  {
    testHover((HoverTestConfiguration it) -> {
      final var model = """
              catalogue Catalogue1

              namespace Namespace1 { }
              """;
      it.setModel(model);
      it.setLine(2);

      final var expectedHover = """
              [[2, 0] .. [2, 9]]
              kind: markdown
              value: `namespace <name> { }`

              A **Namespace** is a primary ordering mechanism.

              A **namespace** may contain other namespaces (nested namespaces), and does typically contain types.
              In SMDL, namespaces are contained within a **Catalogue** (either directly, or within another namespace in a catalogue).
              All sub-elements of a namespace (namespaces and types) must have unique names.
              """;
      it.setExpectedHover(expectedHover);
    });
  }

  @Test
  void testHover_2()
  {
    testHover((HoverTestConfiguration it) -> {
      // Include ECSS-SMP library
      TestUtils.loadEcssSmpLibrary(it);

      final var model = """
              catalogue TestCatalogue

              namespace TestNamespace
              {
                  /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                  model TestModel
                  {
                      field Bool fea_bool
                  }
              }""";

      it.setModel(model);
      it.setLine(7);
      it.setColumn(15);

      final var expectedHover = """
              [[7, 14] .. [7, 18]]
              kind: markdown
              value: boolean with true or false<dl><dt>@id</dt><dd>Smp.Bool</dd></dl>
                            """;
      it.setExpectedHover(expectedHover);
    });
  }

  @Test
  void testHover_3()
  {
    testHover((HoverTestConfiguration it) -> {
      final var model = """
              catalogue TestCatalogue

              namespace TestNamespace
              {
                  /**
                   * Description of my integer
                   *
                   * @uuid 6ca8c3ec-d081-49d1-9ea7-7cc791afa2c1
                   */
                  integer TestInteger

                  /** @uuid 563ca54c-6d21-46d2-83a3-029eb18f66ed */
                  model TestModel
                  {
                      field TestInteger fea_int
                  }
              }""";

      it.setModel(model);
      it.setLine(14);
      it.setColumn(16);

      final var expectedHover = """
              [[14, 14] .. [14, 25]]
              kind: markdown
              value: Description of my integer<dl></dl>
              """;
      it.setExpectedHover(expectedHover);
    });
  }

}
