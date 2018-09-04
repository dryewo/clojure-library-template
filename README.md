# clojure-library-template

[![Build Status](https://travis-ci.org/dryewo/clojure-library-template.svg?branch=master)](https://travis-ci.org/dryewo/clojure-library-template)
[![Clojars Project](https://img.shields.io/clojars/v/library/lein-template.svg)](https://clojars.org/library/lein-template)


Leiningen template for libraries with better release cycle.

## Usage

    lein new library com.example/<lib_name>

Among other improvements, the generated library uses an alternative release cycle:

1. Version in _project.clj_ is the latest released version. It's not decorated with `-SNAPSHOT`.
2. When you release it, you decide which version increment you are making and you run `lein release <segment>`,
   where `<segment>` is one of `:major`, `:minor`, `:patch`.
3. During `lein release` (after incrementing the version in _project.clj_) all mentions of the version in _README.md_ are updated to the current one,
   as well as `Unreleased` section in _CHANGELOG.md_ is renamed to the current version.

Example:
- Right now you have version `"1.2.3"` in your _project.clj_.
- That means that version `"1.2.3"` was released a while ago, and in the repository there are some new changes since that last release.
- _CHANGELOG.md_ already contains all the descriptions of significant changes that you have made since then
  in the `Unreleased` section, because you were updating it regularly.
- You look at _CHANGELOG.md_ and realize that there's a new feature there and you want to release it now,
  which means minor version release, from `"1.2.3"` to `"1.3.0"`.
- You run `lein release :minor`, and it does for you:
  - Checks that there are no uncommitted changes.
  - Bumps the version in _project.clj_ from `"1.2.3"` to `"1.3.0"`.
  - Updates _CHANGELOG.md_: moves contents from `Unreleased` section to the new `"1.3.0"` section.
  - Replaces mentions of `"1.2.3"` in _README.md_ with `"1.3.0"`.
  - Commits the changes.
  - Creates a tag `"1.3.0"`.
  - Uploads the JAR to Clojars.
  - Does `git push`.

## License

Copyright © 2018 Dmitrii Balakhonskii

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
