This page explains common tasks related to working with Xsmp-Core's source code.

## Report an Issue

Please create any new issue in the [Issue tracker on GitHub](https://github.com/ThalesGroup/xsmp-modeler-core/issues)

### Issue Labels

Issue labels can serve several purposes:

* Indicate the issue type: `bug`, `enhancement`, `new_feature`, `question`
* Communicate the current status of an issue: `confirmed`, `help_wanted`
* Communicate why an issue has not been fixed: `duplicate`, `invalid`, `wontfix`
* Categorize issues for better overview, e.g. to assign an issue to a specific part of the software. Committers may add categorization labels as required. However, in most cases the `[category]` prefixing in the issue title should be sufficient.

In general we want to keep the number of labels as low as possible in order to avoid contributors to be overwhelmed and to make sure they are actually used. A label is not useful if it's assigned only to a fraction of the issues it belongs to, so it's important for all contributors to use the existing labels consistently.

### Workflow

All committers should feel responsible to read incoming issues. When you read a new issue, please think about an appropriate reaction:

* If possible, assign a type and a status.
* If appropriate, close the issue and explain why it won't be fixed.
* If the report seems reasonable, a comment with some feedback to the reporter would be nice (e.g. describe wich action is required next, such as to confirm the issue or find a solution for it), especially if the reporter is not a committer.
* If you are not familiar enough with the topic of the issue, you might find someone who is and delegate the action.

## Contribute via Fork
You need a [GitHub](https://github.com/join) account.

 1. Make sure there is a GitHub issue for what you want to work on.
 2. Announce in the comments section that you want to work on the issue. Also describe the solution you want to implement. To improve the chances for your contribution to be accepted, you'll want to wait for the feedback of the committers.
 3. Implement your change. Take care to follow the [quality guidelines](QUALITY_GUIDELINES.md) to improve the chances of your contribution being accepted by a committer.
 4. Run the Maven build locally to see if everything compiles and all tests pass.
 5. Push your changes to your forked repository. It is recommended to create a separate branch, this will make it easier to include the feedback from committers and update your changes.
 6. Create a [pull request](https://help.github.com/articles/using-pull-requests/).

## Contributing for Committers
You're a committer if you have write-access to the Xsmp-Core git-repository.

 1. Make sure there is a GitHub issue for what you want to work on
 2. Assign the issue to yourself.
 3. Create a local git branch.
 4. Implement your change. Take care to follow the [quality guidelines](QUALITY_GUIDELINES.md).
 5. Push the branch into the repository.
 6. Run the CI
 7. If, and only if, all tests have passed, create a pull request and ask somebody who is familiar with the code you modified to review it.
 8. If the reviewer approves, merge.


