#!/usr/bin/env bash
set -euo pipefail
IFS=$'\t\n'
set -x

# Build the template itself
lein do clean, test

cd target

run-test() {
    DEBUG=1 lein new library "$@"
    local project_dir=${1##*/}
    pushd "$project_dir"
        lein test
        # Bump a version and check that it's replaced in README.md (part of release procedure)
        lein change version leiningen.release/bump-version :patch
        lein change version leiningen.release/bump-version release :patch
        lein update-readme-version
        grep "0.0.1" README.md >/dev/null
    popd
}

# Generate a project based on the template and run tests in it
run-test org.example/foo1

# Just in case we want to try it outside of target/
lein install
echo ""
echo "Use:   lein new library org.example.footeam/bar-lib"
echo ""
