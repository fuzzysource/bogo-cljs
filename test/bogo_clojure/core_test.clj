(ns bogo-clojure.core-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.core :refer :all]))

(deftest test-do-stack-transform
  (testing "With :undo-to-none"
    ;; Only one accent transform in the stack
    (let [option :undo-to-none]
      (do
        (is (= '(:acute) (do-stack-transform '() :acute option)))
        (is (= '() (do-stack-transform '(:acute) :acute option)))
        (is (= '(:grave) (do-stack-transform '(:acute) :grave option)))
        (is (= '(:hat) (do-stack-transform '(:hat :acute) :acute option)))
        (is (= '(:grave) (do-stack-transform '(:hat :acute) :grave option)))
        ;; Remove all accent if :none is received
        (is (= '(:hat) (do-stack-transform '(:hat :acute) :none option))))
      ))
  '(testing "With :undo-to-previous"
     ;; Fallback to nearest accent.
     (let [option :undo-to-previous]
       (do
         (is (= (:grave) (do-stack-transform () :grave option)))
         (is (= () (do-stack-transform (:grave) :grave option)))
         (is (= (:acute) (do-stack-transform (:grave :acute) :grave option)))
         (is (= (:hat) (do-stack-transform (:hat :grave) :grave option)))
         ;; Only keep 2 nearest transforms
         (is (= (:hook :hat :acute) (do-stack-transform (:hat :acute :grave))))
         ;; Remove duplicate transforms
         (is (= (:grave :hat) (do-stack-transform (:hat :acute :grave)
                                                   :grave option)))
         ;; Remove all accent if :none is received
         (is (= (:hat) (do-stack-transform (:hat :acute :grave) :none option))))
       ))
  )
