(ns bogo-clojure.bg-word-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.bg-word :refer :all]))

(deftest test-split-word
  (is (= ["m" "o" "ng"] (split-word "mong")))
  (is (= ["m" "eo" ""] (split-word "meo")))
  (is (= ["nfèogh" "oo" ""] (split-word "nfèoghoo")))
  (is (= ["ng" "èo" ""] (split-word "ngèo")))
  (is (= ["qu" "a" "n"]  (split-word "quan")))
  (is (= ["gi" "a" "n"]  (split-word "gian")))
  (is (= ["g" "i" "n"]  (split-word "gin"))))

(deftest test-normalize
  (is (= "meo" (normalize "mèo")))
  (is (= "meoconrahôca" (normalize "mÈocoNrahỒcá")))
  )

(deftest test-valid-word?
  (let [test-cases {
                    "meo" true
                    "me" true
                    "qua" true
                    "mèo" true
                    "ôin" false
                    "ôN" true
                    "chuyên" true
                    "chuyêm" true
                    "chuyêch" false
                    "quôx" false
                    "quên" true
                    "quuên" false
                    }]
    (dorun (map #(is  (let [correct? (= %2 (valid-word? %1))]
                        (when (not correct?) (println %1 "false"))
                        correct?))
                (keys test-cases)
                (vals test-cases)))))
