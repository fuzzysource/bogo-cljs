(ns bogo-clojure.bg-typemode
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-word :refer :all]
            [bogo-clojure.bg-action :refer :all]))

(def SIMPLE-TELEX
  {
   "a" (mark-> "a" :hat ["a" "ă" "â"])
   "w" (mark-> "w"
               :horn ["u" "ư" "uo" "ươ"]
               :breve ["a" "ă" "â"]
               :horn ["o" "ơ" "ô"])
   "o" (mark-> "o" :hat ["o" "ô" "ơ"])
   "e" (mark-> "e" :hat ["e" "ê"])
   "d" (mark-> "d"
               :bar ["d" "đ"])
   "r" (accent-> "r"
                 :hook)
   "x" (accent-> "x"
                 :tilde)
   "j" (accent-> "j"
                 :dot)
   "s" (accent-> "s"
                 :acute)
   "f" (accent-> "f"
                 :grave)
   "z" (accent-> "z"
                 :none)
   })

(def TELEX
  {
   "a" (mark-> "a" :hat ["a" "ă" "â"])
   "w" (fn [word]
         (let [case-a (mark->word word :breve)
               case-ou (mark->word word :horn)]
           (cond
            (not= case-a word) case-a
            (not= case-ou word) case-ou
            (not= "ư" (normalize (str (last word)))) (str word "ư")
            :else (str (rollback word :horn) "w"))))
   "o" (mark-> "o" :hat ["o" "ô" "ơ"])
   "e" (mark-> "e" :hat ["e" "ê"])
   "d" (mark-> "d"
               :bar ["d" "đ"])
   "r" (accent-> "r"
                 :hook)
   "x" (accent-> "x"
                 :tilde)
   "j" (accent-> "j"
                 :dot)
   "s" (accent-> "s"
                 :acute)
   "f" (accent-> "f"
                 :grave)
   "z" (accent-> "z"
                 :none)
   "[" (add-> "[" "ơ")
   "]" (add-> "]" "ư")
   })

