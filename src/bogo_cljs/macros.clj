(ns bogo-cljs.macros)

(defmacro mark->
  "This macro create a mark-adding function associating with the enter key.
  Pairs describes the effected parts of the word and the
  their accompanying transformations"
  [key & pairs]
  `(fn [~'word]
     ~(concat '(cond)
              (loop [mark-set pairs
                     actions  '()]
                (if (nil? mark-set)
                  actions
                  (recur (next (next mark-set))
                         (concat actions
                                 `((bogo-cljs.bg-action/has-char? ~'word ~(second mark-set))
                                   (bogo-cljs.bg-action/process-mark ~'word ~(first mark-set) ~key))))))
              `(:else (str ~'word ~key)))))

(defmacro accent->
  "This macro create a accent-adding function associating with the enter key.
  Pairs describes the effected parts of the word and the
  their accompanying transformations"
  [key accent]
  `(fn [word#]
     (bogo-cljs.bg-action/process-accent word# ~accent ~key)))

(defmacro add->
  "Create a function that add new chars at the end of the original word."
  [key chars]
  `(fn [~'word]
     (bogo-cljs.bg-action/process-fast-typing ~'word ~chars ~key)))
