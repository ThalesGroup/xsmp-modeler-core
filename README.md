# XSMP Modeler

[![Java Build](https://github.com/ThalesGroup/xsmp-modeler-core/actions/workflows/build.yml/badge.svg)](https://github.com/ThalesGroup/xsmp-modeler-core/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ThalesGroup_xsmp-modeler-core&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ThalesGroup_xsmp-modeler-core)
[![Documentation Status](https://readthedocs.org/projects/xsmp-modeler/badge/?version=latest)](https://xsmp-modeler.readthedocs.io/en/latest/?badge=latest)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-curved)](https://github.com/ThalesGroup/xsmp-modeler-core/labels/help%20wanted)
[![Open questions](https://img.shields.io/badge/Open-questions-blue.svg?style=flat-curved)](https://github.com/ThalesGroup/xsmp-modeler-core/discussions)
[![Open bugs](https://img.shields.io/badge/Open-bugs-red.svg?style=flat-curved)](https://github.com/ThalesGroup/xsmp-modeler-core/labels/bug)

<p align="center">
    <picture>
      <source media="(prefers-color-scheme: dark)" width="200" srcset="https://github.com/ThalesGroup/xsmp-modeler-core/raw/main/docs/images/xsmp_logo_dark.svg">
      <source media="(prefers-color-scheme: light)" width="200" srcset="https://github.com/ThalesGroup/xsmp-modeler-core/raw/main/docs/images/xsmp_logo_light.svg">
      <img alt="XSMP logo" width="200" src="https://github.com/ThalesGroup/xsmp-modeler-core/raw/main/docs/images/xsmp_logo_light.svg">
    </picture>
</p>


XSMP Modeler is a framework for the development of SMDL (Simulation Model Definition Language) as defined in the [ECSS SMP standard](https://ecss.nl/standard/ecss-e-st-40-07c-simulation-modelling-platform-2-march-2020/).

Supported IDEs:
- Eclipse
- Visual Studio Code

It includes:
- An integrated text editor with syntax highlighting, error checking, auto-completion, formatting, hover information, outline, quick fixes, and more.
- Specific profiles for each framework.
- Additional tools for extended capabilities.

## Profiles

XSMP Modeler offers specific profiles to enhance its functionality:

- **XSMP SDK Profile**: Seamlessly integrates with the [XSMP SDK](https://github.com/ThalesGroup/xsmp-sdk) framework to facilitate the development and testing of SMP components.
- **ESA-CDK Profile**: Specific profile designed for use with the ESA Component Development Kit (ESA-CDK).

## Tools

XSMP Modeler provides additional tools to extend its functionality:

- **SMP Legacy Tool**: Generates SMP legacy modeling files from XSMP textual modeling files.
- **AsciiDoc Tool**: Generates AsciiDoc documentation from XSMP modeling files.

## System Requirements

- Eclipse 2024-03 or newer
- Java SE 17

## How To Install

Refer to the instructions provided on the [releases page](https://github.com/ThalesGroup/xsmp-modeler-core/releases) or in the [ReadTheDocs documentation](https://xsmp-modeler.readthedocs.io).

### Debug and Build

This project uses both Apache Maven and Gradle as its build systems.

- Maven is used to build Eclipse plugins.
- Gradle is used to build the CLI and VS Code extension.

From a command line, run `mvn package` and `./gradlew build vscodeExtension` in the root directory of the project source files.

## Contributing

If you are interested in contributing, start by reading the [Contributing guide](/CONTRIBUTING.md).

## License

This project is licensed under the [Eclipse Public License 2.0](http://www.eclipse.org/legal/epl-2.0/).
