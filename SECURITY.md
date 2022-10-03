Describe here all the security policies in place on this repository to help your contributors to handle security issues efficiently.

## Goods practices to follow

:warning:**You must never store credentials information into source code or config file in a GitHub repository** 
- Block sensitive data being pushed to GitHub by git-secrets or its likes as a git pre-commit hook
- Audit for slipped secrets with dedicated tools
- Use environment variables for secrets in CI/CD (e.g. GitHub Secrets) and secret managers in production

# Security Policy

## Supported Versions

Use this section to tell people about which versions of your project are currently being supported with security updates.

| Version | Supported          |
| ------- | ------------------ |
| 1.1.x   | :white_check_mark: |

## Reporting a Vulnerability

Vulnerabilities can be reported through the github [issues](https://github.com/ThalesGroup/xsmp-modeler-core/issues) tracker.
Once accepted, the vulnerability will be treated as soon as possible.


## Disclosure policy



## Security Update policy


## Security related configuration.

Settings users should consider that would impact the security posture of deploying this project, such as HTTPS, authorization and many others.

## Known security gaps & future enhancements.


