Introduction
============


Import
======


Command Line Interface
======================

To convert an smpcat file to xsmpcat using the CLI:

#. Get the CLI .jar in `org.eclipse.xsmp.tool.smp.cli` > `target` > **...-SNAPSHOT-cli.jar**
#. Go to the root of your project: ``cd /workspace/your-project``
#. Launch the CLI with the -i option: ``java -jar path/to/cli.jar -i``

The xsmpcat file will be generated in the ``smdl`` folder.

If your project has dependencies to other projects, please add the path to these other projects with the -c option: ``-c [project path 1] [project path 2] ...``

To get a list of all available options, add the --help or -h argument.
