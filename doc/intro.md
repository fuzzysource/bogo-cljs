# Giới thiệu về bogo-clojure

## Ý tưởng triển khai

Thay vì quan tâm đến việc xử lý từng trường hợp phím bấm vào như thông
thường, phương án này tập trung vào việc định nghĩa các phép biến
đổi. Dựa trên các việc định nghĩa các phép biến đổi, ta có thể định
nghĩa các kiểu gõ (telex, vni,...).

Khi một phím (`key`) được nhập vào từ bàn phím, việc xử lý tiếng Việt
xảy ra theo 1 trong 3 khả năng:

* Thêm thanh (hỏi, ngã, huyền, nặng)
* Thêm dấu (dấu móc của ư, ơ, đấu mũ của â, ă, ô, dấu ngang của đ)
* Thêm kí tự mới vào cuối câu

Hành động biến đổi từ được gọi là `transformation`. 2 trường hợp đầu
về bản chất là như nhau. Mỗi `transformation` được định nghĩa bởi 3
thành phần:

1. key: key nhập từ bàn phím.
2. part: Thành phần bị tác động
3. effect: Kết quả của tác động gồm nguồn (input) và kết quả (result)

### Thành phần bị tác động (part)

Mỗi từ được chia thành 3 phần:

1. Phụ âm đầu (first-consonant)
2. Nguyên âm giữa (vowel)
3. Phụ âm cuối (last-consonant)

Phần bị tác động của 1 phép biến đổi là một trong 3 thành phần của của
từ.

### Ví dụ

Dưới đây đề xuất một hình mẫu cho việc định nghĩa phép biến đổi

    w vowel u->ư
    w vowel uo->ươ
    w last-consonant ->ư: Thêm kí tự ư vào cuối.
    d firt-consonant d->đ: Chuyển d thành đ
    o vowel ư->ươ
    f vowel e->è


## Sơ lược về giải thuật

Khi một key được nhập, thực hiện các bước sau:

* Tìm `transformation` tương ứng. Với mỗi `key` nhận được chỉ thực
  hiện một phép biến đổi. Do đó, việc định `part` vả `effect` cần được
  thực hiện tỉ mỉ.
* Tìm thành phần tác động
* Nếu phần tác động là nguồn của phép biến đổi, thực hiện phép biến
  đổi.
* Nếu phần tác động là kết quả của phép biến đổi, hoán đổi lại phép biến
  đổi, thêm `key` vào cuối của từ.
* Nếu không thuộc trường hợp nào, thêm key vào cuối từ.
