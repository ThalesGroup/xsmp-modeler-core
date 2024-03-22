package org.eclipse.xsmp.tool.adoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.xsmp.model.xsmp.CollectionLiteral;
import org.eclipse.xsmp.model.xsmp.DesignatedInitializer;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.util.XsmpUtil;

public class ADocUtil extends XsmpUtil
{
  private static final Set<String> ALLOWED_TAGS = new HashSet<>(
          Arrays.asList("br", "img", "a", "strong", "em", "code", "font", "span", "button", "kbd",
                  "sup", "sub", "mark", "menu", "del"));

  public String getDefaultValue(Expression e)
  {
    if (e instanceof CollectionLiteral)
    {
      final var defaultValues = getDefaultValuesArray((CollectionLiteral) e);
      return removeLeadingEscape(defaultValues);
    }
    final var value = getValue(e);
    if (value != null)
    {
      return value.toString();
    }
    return null;
  }

  public String getDefaultValuesArray(CollectionLiteral c)
  {
    final List<String> results = new ArrayList<>();

    for (final Expression expression : c.getElements())
    {
      if (expression instanceof CollectionLiteral)
      {
        results.add(getDefaultValuesArray((CollectionLiteral) expression));
      }
      else if (expression instanceof DesignatedInitializer)
      {
        results.add(getDefaultValue(((DesignatedInitializer) expression).getExpr()));
      }
      else
      {
        results.add(super.getValue(expression).toString());
      }
    }

    final var str = results.toString();
    if (str.startsWith("[["))
    {
      return "\\" + str;
    }

    return str;
  }

  public boolean isHTML(String description)
  {
    return description.startsWith("<") && description.endsWith(">");
  }

  public String formatDescription(String description)
  {
    if (isHTML(description))
    {
    }
    return description;
  }

  private String removeLeadingEscape(String str)
  {
    if (str.startsWith("\\"))
    {
      return str.substring(1);
    }
    return str;
  }
}
