AsciiDoc Tool
=============

This tool generates AsciiDoc documentation from modeling files.

To use this tool, add the following line to your `project configuration <./user-guide.html#project-configuration>`_:
::

    tool "org.eclipse.xsmp.tool.adoc"

In a ``doc`` folder, the tool will generate an auto-generated file representing the modeled file, in AsciiDoc format.

You can customize the final AsciiDoc output by adding a user-code file, for example:
::
    :doctype: book
    :toc:
    :pdf-themesdir: {docdir}

    = Catalogue document

    include::YourDocument-gen.adoc[]

It is possible to add a theme (YML type) to customize the PDF. For example **default.yml**:
::
    extends: default
    page:
      margin: [1.5in, 0.75in]
      numbering:
        start_at: title
    base: 
      font-family: Helvetica
    running_content:
      start_at: title
    header:
      height: 1.25in
      border-width: 0.25
      border-color: #DDDDDD
      recto:
        right:
          content: |
            *Date:* {localdatetime} +
            *Page:* {page-number}/{page-count}
      verso:
        right:
          content: $header-recto-right-content
    footer:
      height: 0.85in
      font-size: 9
      recto:
        left:
          content: ''
        center:
          content: |
            *XSMP* +
            This document has been automatically generated using the Adoc tool from XSMP Modeler. +
            © {localyear}, All Rights Reserved
        right:
          content: ''
      verso:
        left:
          content: $footer-recto-left-content
        center:
          content: $footer-recto-center-content
        right:
          content: $footer-recto-right-content

Finally, to generate the PDF from the theme and AsciiDoc files, type the following command:
::

    asciidoctor-pdf -a pdf-theme=your_theme.yml -r asciidoctor-diagram YourDocument.adoc

Be sure to download the following elements beforehand (using Gem or Homebrew):

* asciidoctor
* asciidoctor-pdf
* asciidoctor-diagram

And also Mermaid using NPM:
::

    npm install -g @mermaid-js/mermaid-cli

Python Wrapper
==============

This tool generates Python helpers from modeling files. It is used by XSMP SDK unit tests framework.

To use this tool, add the following line to your `project configuration <./user-guide.html#project-configuration>`_:

::

    tool "org.eclipse.xsmp.tool.python"

SMP legacy tool
===============

This tool generates SMP legacy modeling files from XSMP.

To use this tool, add the following line to your `project configuration <./user-guide.html#project-configuration>`_:

::

    tool "org.eclipse.xsmp.tool.smp"