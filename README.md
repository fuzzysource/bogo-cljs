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
* Cách 2: Bookmark [link][bogo] này lại và vào trang web bất kì. :))


At this moment, the key that triggers transformation must be a lower
character.

## TẠO KIỂU GÕ

Các tạo kiểu gõ được thể hiện trong file `bg_telex.clj`.

## TEST

```
    lein test
```

[bogo]: javascript:(function () {var url="ws://0.0.0.0:8080";var UserTextFields=$("input, textarea");var connectBogoServer=function(e){var t=new WebSocket(e);t.onopen=function(e){this.send("CHAO\nf")};return t};var bogo=connectBogoServer(url);var bgProcessKey=function(e,t){var n=$(e).val();var r=e.oldString;bogo.send(r+"\n"+t);bogo.onmessage=function(t){var i=t.data;var s=n.length-r.length;$(e).val(n.substr(0,s)+i);e.oldString=i}};var isCharacter=function(e){if(e.length>1||e==" ")return false;return true};var isBackspace=function(e){return e==="Backspace"};var isSpecialKey=function(e){return e.altKey||e.ctrlKey||e.metaKey};var hasSelectedText=function(e){var t=$(e).val().slice(e.selectionStart,e.selectionEnd);return t};var startBogo=function(e){e.oldString="";var t=e.oldString;$(document).bind("keypress",function(n){if(hasSelectedText(e)){e.oldString="";return true}if(isSpecialKey(n))return true;if(isCharacter(n.key)){n.preventDefault();bgProcessKey(e,n.key)}else{if(isBackspace(n.key)){e.oldString=t.substr(0,t.length-1)}e.oldString=""}})};var stopBogo=function(e){e.oldString="";$(document).unbind("keypress")};UserTextFields.focus(function(e){startBogo(this)});UserTextFields.blur(function(e){stopBogo(this)})})()
