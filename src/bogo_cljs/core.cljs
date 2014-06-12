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
    "addchar"))

(defn process-key*
  "Capitalize the last character if the rollback was triggered by an
  upper-case character"
  [key old-string new-string]
  (let [old-length (.-length old-string)
        new-length (.-length new-string)]
    (if (and (= (dec old-length) new-length)
             (= (.toLowerCase key) (aget new-string (dec new-length))))
      (+ (.substring new-string 0 (.-length old-string)) key)
      new-string)))

(defn ^:export process_key
  "Return new string composed by the current string and the key pressed."
  ([astring key]
   (process_key astring key TELEX))
  ([astring key typemode]
   (let [[first-word last-word] (grammar-split-word astring)
         lkey (.toLowerCase key)
         action (get-action lkey typemode)]
     (+ first-word
        (refine-word (process-key* key
                                   last-word
                                   (if (fn? action)
                                     (action last-word)
                                     (+ last-word key))))))))

(defn ^:export process_sequence
  "Return the new string composed by typing a sequence of keys."
  ([sequence]
   (process_sequence sequence TELEX))
  ([sequence typemode]
   (reduce (fn [word key] (process_key word key typemode))
           ""
           sequence)))
