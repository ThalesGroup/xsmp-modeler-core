Installation
============

Eclipse
-------

Configure the update site
^^^^^^^^^^^^^^^^^^^^^^^^^

1. From Github: Copy the link of the **org.eclipse.xsmp.repository** archive file from the releases `page <https://github.com/ThalesGroup/xsmp-modeler-core/releases>`_. In **Window** → **Preferences** → **Install/Update** → **Available software** → **Add** create a new site called XSMP with location: ``jar:<URL>!/`` where ``<URL>`` is the archive link.

   e.g: ``jar:https://github.com/ThalesGroup/xsmp-modeler-core/releases/download/xxx/org.eclipse.xsmp.repository-xxx.zip!/``

2. From P2 repository: P2 repository is not yet available.

Install the plugins
^^^^^^^^^^^^^^^^^^^

In **Help** → **Install new software...** select the XSMP update site. You can then select and install the XSMP plugins you need.


Project creation
================

Run the wizard from **File** → **New** → **Project...** → **Xsmp** → **Xsmpcat Project** and follow the instructions.

In case you installed an additional Profile, a dedicated template should be available in the template selection page.


Catalogue file
==============

The Catalogue file is located in the ``smdl`` folder of the newly created project. Its file extension is ``xsmpcat``.

This file is a textual file that describes the SMP Catalogue. Its content must match the Xsmpcat grammar.

The Xsmp modeler provides a specialized editor with:

* Syntax/Semantic Coloring
* Error Checking
* Auto-Completion (including templates)
* Formatting
* Hover Information
* Mark Occurrences
* Go To Declaration
* Rename Refactoring
* Toggle Comments
* Outline / Structure View
* Quick Fix Proposals
* Find References
* Folding


Workflow
========

The XSMP workflow is automatic (verify that **Project** → **Build Automatically** is checked). This means that on each change/save of a catalogue file, the catalogue is validated and if it succeeded the code generator(s) are triggered (C++ code generator for example).


Project dependencies
====================

Project dependencies are handled by the META-INF/MANIFEST.MF file in which you can specify the list of required plug-ins. 

For example, if you have a project A that requires an SMP type publicly defined in the catalogue of a project B, then you have to add in the MANIFEST.MF of the project A a dependency on the project B.


Command Line Interface
======================

Catalogue validation and generation can be performed from the command line.

* Get the CLI jar file for your tool or profile.
* Check that Java 11 is available.
* Go to your project directory: ``cd ~/eclipse_workspace/<project_name>``.
* Run the CLI: ``java -jar /path/to/cli.jar``.

To get a list of all available options, add the ``--help`` or ``-h`` argument: ``java -jar /path/to/cli.jar -h``.
