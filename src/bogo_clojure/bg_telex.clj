(ns bogo-clojure.bg-telex
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-word :refer :all]
            [bogo-clojure.bg-macro :refer :all]))

(def TELEX
  {
   "a" (add-mark "a" :hat ["a" "ă" "â"])
   "w" (add-mark "w"
                 :horn ["u" "ư"]
                 :breve ["a" "ă" "â"]
                 :horn ["o" "ơ" "ô"])
   "o" (add-mark "o" :hat "o" "ô" "ơ")
   "d" (add-mark "d"
                 :bar ["d" "đ"])
   "r" (add-accent "r"
                   :hook)
   "x" (add-accent "x"
                   :tilde)
   "j" (add-accent "j"
                   :dot)
   "s" (add-accent "s"
                   :acute)
   "f" (add-accent "f"
                   :grave)
   "z" (add-accent "z"
                   :none)
   })
