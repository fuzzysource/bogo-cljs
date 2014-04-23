(defproject bogo-clojure "0.1.0-SNAPSHOT"
  :description "Vietnamese typing engine"
  :url "http://github.com/fuzzysource/bogo-clojure"
  :license {:name "General Public License"
            :url "https://www.gnu.org/copyleft/gpl.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot bogo-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
