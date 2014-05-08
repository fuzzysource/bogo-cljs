# bogo-clojure

## THỬ NGHIỆM

### VỚI REPL

* Clone và  `lein repl`
* Thử
```clojure
    (ns bogo-clojure.core) ; Bước đầu tiên.
    (process-key "meo" "f") ; => mèo
    (process-key "mèo" "f") ; => meof
    (process-sequence "meof") ; => mèo
    (process-sequence "muwo") ; => mươ
```

### VỚI TRÌNH DUYỆT

Phiên bản này chỉ mang tính thử nghiệm và "cho vui", sử dụng web
socket để trao đổi data giữa trình duyệt và engine.

* Chạy `lein run`.

Tới đây có 2 cách để tiếp tục:

* Cách 1: Mở file index.html trong thư mục `bogo-web`. Tắt hết các bộ gõ đang
  khác hoạt động.
* Cách 2: Tạo một bookmark với location là nội dung dưới tại [link này][bogo]. này lại
  và vào trang web sử dụng textarea. vnexpress.net, bbc.co.uk. :))


At this moment, the key that triggers transformation must be a lower
character.

## TẠO KIỂU GÕ

Các tạo kiểu gõ được thể hiện trong file `bg_telex.clj`.

## TEST

```
    lein test
```

[bogo]: https://raw.githubusercontent.com/fuzzysource/bogo-clojure/master/bogo-web/bookmark
