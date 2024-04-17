package com.thalesaleniaspace.compilechain;

import org.eclipse.cdt.core.formatter.DefaultCodeFormatterOptions;
import org.eclipse.cdt.internal.formatter.CCodeFormatter;

@SuppressWarnings("restriction")
public class CodeFormatter extends CCodeFormatter
{
  public CodeFormatter()
  {
    super(formatterOptions());
  }

  private static DefaultCodeFormatterOptions formatterOptions()
  {
    final var options = DefaultCodeFormatterOptions.getKandRSettings();
    options.tab_char = DefaultCodeFormatterOptions.SPACE;
    return options;
  }
}
