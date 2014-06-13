(ns bogo
  (:require bogo-cljs.core))

(defn editing-areas
  "Select all editable areas in the web page"
  []
  (.. js/Array
      -prototype
      -slice
      (call (.querySelectorAll js/document "input, textarea, *[contenteditable=\"true\"]"))))

(defn key-from-key-event
  [key-event]
  (.fromCharCode js/String (.-keyCode key-event)))

(defn create-html-range
  "Create a HTML range within a node with given start and end position."
  [node startOffset endOffset]
  (let
    [r (.createRange js/document)]
    (.setStart r node startOffset)
    (.setEnd r node endOffset)
    r))

(defn process-key-at-caret
  "When user do not select any text, the selection object .toString()
  return an empty string. The new string combined from the text before
  the caret and the entered key"
  [selection key]
  (let [current-text-node (.-anchorNode selection)
        old-text (.-textContent current-text-node)
        new-text (bogo-cljs.core/process-key old-text key)]
    (do
      (set! (.-textContent current-text-node)
            new-text)
      (.collapse selection current-text-node (.-length new-text)))))

(defn editing-areas
  []
  (.querySelectorAll js/document "input,textarea,div[contenteditable=true]"))

(defn special-key-pressed?
  [key-event]
  (or (.-altKey key-event)
      (.-ctrlKey key-event)
      (.-metaKey key-event)))

(defn backspace-press?
  [key-event]
  (= "Backspace" (key-from-key-event key-event)))


(defn process-key-event!
  "Process the key event"
  [selection key-event]
  (if (= "" (.toString selection))
    (do
      (process-key-at-caret selection (key-from-key-event key-event))
      (.preventDefault key-event))
    false))

(defn binding-key-event-handler!
  [node]
  (aset node "onkeypress"
        (fn [key-event]
          (if (or (special-key-pressed? key-test)
                  (backspace-press? key-event))
            false ;; Leave the key event handled by default
            ;; if an character is pressed
            (process-key-event! (.getSelection js/document)
                                key-event)))))
(.forEach (editing-areas)
          (fn [e]
            (binding-key-event-handler! e)))


