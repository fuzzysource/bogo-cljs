(defproject bogo-cljs "0.1.0-SNAPSHOT"
  :description "Vietnamese typing engine"
  :url "http://github.com/fuzzysource/bogo-clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2227"]]
  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {:builds
               [{
                 :source-paths ["src"]

                 ;; Google Closure Compiler options
                 :compiler {;; the name of emitted JS script file
                           :output-to "build/bogo.js"
                           :optimizations :whitespace
                           :pretty-print true}}]})
