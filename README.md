# clojure-library-template

[![Build Status](https://travis-ci.org/dryewo/clojure-library-template.svg?branch=master)](https://travis-ci.org/dryewo/clojure-library-template)
[![Clojars Project](https://img.shields.io/clojars/v/library/lein-template.svg)](https://clojars.org/library/lein-template)


Leiningen template for libraries with better release cycle.

## Usage

    lein new library com.example/<lib_name>

Then, replace `REPO_OWNER/REPO_NAME` in _README.md_ with the real GitHub coordinates of the repo you'll be keeping this project in.

Among other improvements, the generated library uses an alternative release cycle:

1. Version in _project.clj_ is the latest release version. It's not decorated with `-SNAPSHOT`.
2. When you release it, you decide which version you are releasing and run `lein release <segment>`, where `<segment>` is one of `:major`, `:minor`, `:patch`.
3. During `lein release` mentions of the version in _README.md_ are updated to the current one.
  
Example:
- right now you have version "1.2.3" in your _project.clj_.
- That means that version "1.2.3" was released a while ago, and in the repository there are some new changes since that last release.
- You recall that you added a new nice feature that you want to release now, which means you'd like to release the current state of the codebase as "1.3.0".
- You probably edit _CHANGELOG.md_ and describe the feature there, and commit it.
- You run `lein release :minor`, and it does for you:
  - Checks that there are no uncommitted changes
  - Bumps the version in _project.clj_ from "1.2.3" to "1.3.0"
  - Replaces mentions of "1.2.3" in _README.md_ with "1.3.0"
  - Commits the changes
  - Creates a tag "1.3.0"
  - Uploads the JAR to Clojars
  - `git push`

## License

Copyright © 2018 Dmitrii Balakhonskii

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
