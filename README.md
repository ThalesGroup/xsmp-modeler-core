
# Eclipse Xsmp-Core

## Get started

Xsmp-Core is an Eclipse framework for development of SMDL (Simulation Model Definition Language) as defined in [ECSS SMP standard](https://ecss.nl/standard/ecss-e-st-40-07c-simulation-modelling-platform-2-march-2020/). 
Xsmp-Core is built on the [Xtext](https://www.eclipse.org/Xtext/) framework.

Xsmp-Core comes with an integrated Text Editor with coloring, auto-completion, formatting, hover information, outline, quick fix and validation.
Xsmp-Core provides the skeleton of a customizable C++ code generator to convert the SMDL Catalogue to C++ source files.

Xsmp-Core can be customized for a specific environment / MDK via an extension mechanism.

This initial release does not provides ECSS SMP catalogue import/export (smpcat, smppkg) due to license issue. This functionality will be provided as soon as the ECSS SMP Standard will be released with a public license.


### How To Build

Check out and run `mvn install`.

## Documentation

Documentation is available [here](/doc/).

## Contributing

If you are interested in contributing to the Xsmp-Core project, start by reading the [Contributing guide](/CONTRIBUTING.md).

## License

Xsmp-Core is released under the EPL 2.0, see [LICENSE](/LICENSE) file at the root of the repository

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=ThalesGroup_xsmp-modeler-core)