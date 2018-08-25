(ns leiningen.new.library
  (:require [leiningen.new.templates :refer [renderer year date project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment sanitize]]
            [leiningen.core.main :as main]
            [clojure.string :as str]))


(defn prepare-data [name]
  (let [namespace    (project-name name)
        escaped-name (-> name
                         (str/replace #"\." "\\\\.")
                         (str/replace #"/" "\\\\\\\\/"))]
    {:raw-name      name
     :name          (project-name name)
     :namespace     namespace
     :package       (sanitize namespace)
     :nested-dirs   (name-to-path namespace)
     :repo-path     "REPO_OWNER/REPO_NAME"
     :version-regex (pr-str (format "s/\\\\[%s \"[0-9.]*\"\\\\]/[%s \"${:version}\"]/"  escaped-name escaped-name))
     :debug         (System/getenv "DEBUG")}))

;(println (prepare-data "org.example/foo1"))
;
;(println (-> "org.example/foo1"
;             (str/replace #"\." "\\\\.")
;             (str/replace #"/" "\\\\\\\\/")))

(defn prepare-files
  "Generates arguments for ->files. Extracted for testing."
  [name]
  (let [data   (prepare-data name)
        render (renderer "library")]
    (main/debug "Template data:" data)
    (main/info "Generating a library called" name "based on the 'library' template.")
    (concat
      [data
       ["project.clj" (render "project.clj" data)]
       ["README.md" (render "README.md" data)]
       ["LICENSE" (render "LICENSE" data)]
       ["CHANGELOG.md" (render "CHANGELOG.md" data)]
       [".gitignore" (render "_gitignore" data)]
       [".travis.yml" (render ".travis.yml" data)]
       ["src/{{nested-dirs}}/core.clj" (render "src/_namespace_/core.clj" data)]
       ["test/{{nested-dirs}}/core_test.clj" (render "test/_namespace_/core_test.clj" data)]])))


(defn library [name]
  (main/info "Generating fresh 'lein new' library project.")
  (apply ->files (prepare-files name))
  (main/info "\n\nReplace REPO_OWNER/REPO_NAME in README.md and CHANGELOG.md with the real GitHub coordinates of the repo you'll be keeping this project in.\n"))
