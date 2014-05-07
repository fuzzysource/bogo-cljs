(ns bogo-clojure.core-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.core :refer :all]))

(deftest test-process-sequence
  (let [test-cases {
                    "thaor" "thảo"
                    "meof" "mèo"
                    "meoF" "mèo"
                    "chuyeenj" "chuyện"
                    "chuyejen" "chuyện"
                    "quanr" "quản"
                    "gAusa" "gẤu"
                    }]
    (dorun (map #(is  (let [correct? (= %2 (process-sequence %1))]
                        (when (not correct?) (println %1 %2))
                        correct?))
                (keys test-cases)
                (vals test-cases)))))
