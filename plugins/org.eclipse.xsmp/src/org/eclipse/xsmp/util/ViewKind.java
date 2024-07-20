package org.eclipse.xsmp.util;

public enum ViewKind
{
  /** The element is not made visible to the user (this is the default). */
  NONE("None"),
  /**
   * The element is made visible for debugging purposes.
   * The element is not visible to end users. If the simulation infrastructure supports the
   * selection of different user roles, then the element shall be visible to "Debug" users only.
   */
  DEBUG("Debug"),
  /**
   * The element is made visible for expert users.
   * The element is not visible to end users. If the simulation infrastructure supports the
   * selection of different user roles, then the element shall be visible to "Debug" and "Expert"
   * users.
   */
  EXPERT("Expert"),
  /** The element is made visible to all users. */
  ALL("All");

  public final String label;

  ViewKind(String label)
  {
    this.label = label;
  }
}