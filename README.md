# XSMP Modeler

[![Gitpod - Code Now](https://img.shields.io/badge/Gitpod-code%20now-blue.svg?longCache=true)](https://gitpod.io#https://github.com/ThalesGroup/xsmp-modeler-core)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-curved)](https://github.com/ThalesGroup/xsmp-modeler-core/labels/help%20wanted)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ThalesGroup_xsmp-modeler-core&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ThalesGroup_xsmp-modeler-core)
[![Open questions](https://img.shields.io/badge/Open-questions-blue.svg?style=flat-curved)](https://github.com/ThalesGroup/xsmp-modeler-core/discussions)
[![Open bugs](https://img.shields.io/badge/Open-bugs-red.svg?style=flat-curved)](https://github.com/ThalesGroup/xsmp-modeler-core/labels/bug)
[![Documentation Status](https://readthedocs.org/projects/xsmp-modeler/badge/?version=latest)](https://xsmp-modeler.readthedocs.io/en/latest/?badge=latest)

<p align="center">
    <picture>
      <source media="(prefers-color-scheme: dark)" width="200" srcset="https://github.com/ThalesGroup/xsmp-modeler-core/raw/main/docs/images/xsmp_logo_dark.svg">
      <source media="(prefers-color-scheme: light)" width="200" srcset="https://github.com/ThalesGroup/xsmp-modeler-core/raw/main/docs/images/xsmp_logo_light.svg">
      <img alt="XSMP logo" width="200" src="https://github.com/ThalesGroup/xsmp-modeler-core/raw/main/docs/images/xsmp_logo_light.svg">
    </picture>
</p>

## Get started

XSMP Modeler is a framework for development of SMDL (Simulation Model Definition Language) as defined in the [ECSS SMP standard](https://ecss.nl/standard/ecss-e-st-40-07c-simulation-modelling-platform-2-march-2020/). 

Supported IDES:
- Eclipse
- Visual Studio Code

It comes with:
 - an integrated Text Editor with coloring, error checking, auto-completion, formatting, hover information, outline, quick fix, ...
 - a customizable C++ code generator.

### System Requirements

 - Eclipse 2024-03 or newer.
 - JavaSE-17

### How To Install

Refers to instructions provided in the [releases page](https://github.com/ThalesGroup/xsmp-modeler-core/releases) or in the [ReadTheDoc documentation](https://xsmp-modeler.readthedocs.io).

### Debug and Build

This project uses both Apache Maven and Gradle as its build system.

Maven is used to build Eclipse plugins.

Gradle is used to build CLI and VS Code extension.

From a command-line, run `mvn package` and `./gradlew build vscodeExtension` in the root directory of the project source files.

## Contributing

If you are interested in contributing, start by reading the [Contributing guide](/CONTRIBUTING.md).

## License

- [Eclipse Public License 2.0](http://www.eclipse.org/legal/epl-2.0/)
