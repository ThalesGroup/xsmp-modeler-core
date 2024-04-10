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

VS Code
------------------

To install XSMP in Visual Studio Code, download the .vsix file from the `release page <https://github.com/ThalesGroup/xsmp-modeler-core/releases>`_ and follow these `instructions <https://code.visualstudio.com/docs/editor/extension-marketplace#_install-from-a-vsix>`_.

Project creation
================

Eclipse
-------

Run the wizard from **File** → **New** → **Project...** → **Xsmp** → **Xsmpcat Project** and follow the instructions.

In case you installed an additional Profile, a dedicated template should be available in the template selection page.

VS Code
-------

Open the command palette [Ctrl + Shift + P], select ``xsmp: Create new project`` and follow the instructions.

Modeling file
==============

The modeling file is located in the ``smdl`` folder of the newly created project.

This file is a textual file that describes the SMP document.

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

Project configuration
=====================

The XSMP project configuration is located at the root of the project. The file is named **xsmp.project**. 

It is essential to define:

* The project name: ``project "[name]"``
* The source directory for modeling files:  ``source "[directory name]"``

Additionally, you can:

* Enable a specific `profile <./profiles.html>`_: ``profile "[profile id]"``
* Add additional `tools <./tools.html>`_: ``tool "[tool id]"`` for each tool
* Define a dependency to another project: ``dependency "[project name]"``. The dependency must be a project located within the same workspace as the current project.
For example, if you have a project A that requires an SMP type publicly defined in the catalogue of a project B, then you have to add in the xsmp.project of the project A a dependency on the project B.

xsmp.project example
--------------------

::

    /** Example Project Configuration */
    project "Example"
    
    // Source folder containing modeling file(s)
    source "smdl"
    
    // uncomment to use XSMP-SDK Profile
    //profile "org.eclipse.xsmp.profile.xsmp-sdk"
    
    // uncomment to use ESA-CDK Profile
    //profile "org.eclipse.xsmp.profile.esa-cdk"
    
    // uncomment to use SMP legacy Tool
    //tool "org.eclipse.xsmp.tool.smp"
    
    // uncomment to use Python Wrapper
    //tool "org.eclipse.xsmp.tool.python"
    
    // uncomment to use AsciiDoc Tool
    //tool "org.eclipse.xsmp.tool.adoc"

    // Project dependencies
    // dependency "Dependency1"
    // dependency "Dependency2"

C++ formatter configuration
===========================

The formatting of generated C++ code relied on `Clang format <https://clang.llvm.org/docs/ClangFormat.html>`_.
Clang format must be installed on your platform.

An initial Clang format configuration file can be generated at the project root with the following command:

::

   clang-format -style=llvm -dump-config > .clang-format

Workflow
========

The XSMP workflow is automatic. When the modeling file is saved, the code generators are automatically triggered.

Eclipse
-------

Verify that **Project** → **Build Automatically** is checked

Command Line Interface
======================

Catalogue validation and generation can be performed from the command line.

* Get the CLI jar file for your tool or profile available in `release assets <https://github.com/ThalesGroup/xsmp-modeler-core/releases>`_.
* Check that Java 17 is available.
* Go to your project directory: ``cd ~/workspace/<project_name>``.
* Run the CLI: ``java -jar /path/to/cli.jar``.

To get a list of all available options, add the ``--help`` or ``-h`` argument: ``java -jar /path/to/cli.jar -h``.
