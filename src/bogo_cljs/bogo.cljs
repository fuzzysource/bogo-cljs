(ns bogo
  (:require [bogo-cljs.core :refer [process_key]]))

(defn editareas
  "Select all editable areas in the web page"
  []
  (.querySelectorAll js/document "input, textarea, *[contenteditable=\"true\"]"))

(defn selection
  "Get the dom that has the caret in"
  []
  (.. js/document getSelection))

(defn process-key
  "Process the key event"
  [selection key]
  (if (no-text-selection selection)
    (process-key-at-caret selection key)
    (replace-selection-with-key selection key)))


