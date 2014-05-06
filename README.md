# bogo-clojure

## Thử nghiệm

* Clone và  `lein repl`
* Try
```clojure
    (process-key "meo" "f") ; => mèo
    (process-key "mèo" "f") ; => meof
    (process-sequence "meof") ; => mèo
```

At this moment, the key that triggers transformation must be a lower
character.

## Tạo kiểu gõ

Các tạo kiểu gõ được thể hiện trong file `bg_telex.clj`.

## Test

```
    lein test
```

