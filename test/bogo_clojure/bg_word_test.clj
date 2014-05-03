(ns bogo-clojure.bg-word-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.bg-word :refer :all]))

(deftest test-split-word
  (is (= ["m" "o" "ng"] (split-word "mong")))
  (is (= ["m" "eo" ""] (split-word "meo")))
  (is (= ["nfèogh" "oo" ""] (split-word "nfèoghoo")))
  (is (= ["ng" "èo" ""] (split-word "ngèo")))
  (is (= ["qu" "a" "n"]  (split-word "quan")))
  )
