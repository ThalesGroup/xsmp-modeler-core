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

import org.eclipse.xtext.testing.FormattingConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class FormattingTest extends AbstractXsmpcatLanguageServerTest
{
  @Test
  void testFormatting_1()
  {
    testFormatting((FormattingConfiguration it) -> {
      it.setModel(
              "catalogue TestCatalogue namespace TestNamespace { /** @uuid 87b404c9-2a35-4446-a675-64af8b6a1f68 */ model TestModel { field Bool fea_mybool } }");

      final var expected = """

              catalogue TestCatalogue


              namespace TestNamespace
              {
                  /** @uuid 87b404c9-2a35-4446-a675-64af8b6a1f68 */
                  model TestModel
                  {
                      field Bool fea_mybool
                  }
              }

              """;
      it.setExpectedText(expected);
    });
  }

  @Test
  @Disabled("Remove useless space between description and namespace in Xsmpcat formatting")
  void testFormatting_2()
  {
    testFormatting((FormattingConfiguration it) -> {
      final var model = """
              catalogue TestCatalogue

              namespace TestNamespace {

                  /** Description of my event

                  @uuid 4ce8488e-bf6c-4eda-8f08-85e0ac7ce126 */
                event TestEvent
              }
              """;
      it.setModel(model);

      final var expected = """

              catalogue TestCatalogue


              namespace TestNamespace
              {
                  /**
                   * Description of my event
                   *
                   * @uuid 4ce8488e-bf6c-4eda-8f08-85e0ac7ce126
                   */
                  event TestEvent
              }

              """;
      it.setExpectedText(expected);
    });
  }
}
