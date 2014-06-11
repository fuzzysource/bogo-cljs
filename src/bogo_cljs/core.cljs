(ns bogo
  (:require [clojure.string :as string]
            [bogo-cljs.bg-word
             :refer [grammar-split-word
                     refine-word]]
            [bogo-cljs.bg-typemode
             :refer [TELEX]]
            bogo-cljs.bg-action))
(defn get-action
  "Action is a function taking in current string, make transform,
  return the new string"
  [key typemode]
  (if (contains? typemode key)
    (get typemode key)
    :addchar))

(defn process-key*
  "Capitalize the last character if the rollback was triggered by an
  upper-case character"
  [key old-string new-string]
  (if (and (= (inc (count old-string)) (count new-string))
           (= (string/lower-case key) (str (last new-string))))
    (str (subs new-string 0 (count old-string)) key)
    new-string))

(defn ^:export process_key
  "Return a new string that is resulted by typing new character into
  the old one."
  ([astring key]
   (process_key astring key TELEX))
  ([astring key typemode]
   (let [[first-word last-word] (grammar-split-word astring)
         strkey (str key)
         lkey (string/lower-case strkey)
         action (get-action lkey typemode)]
     (str first-word
          (refine-word (process-key* strkey
                                             last-word
                                             (if (fn? action)
                                               (action last-word)
                                               (str last-word strkey))))))))

(defn ^:export process_sequence
  "Return the string that is resulted by typing the given sequence
  of keys."
  ([sequence]
   (process_sequence sequence TELEX))
  ([sequence typemode]
   (reduce (fn [word key] (process_key word key typemode))
           ""
           sequence)))
