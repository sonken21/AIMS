# Git Flow

## Bắt đầu cài đặt dự án
- Clone dự án về
```sh
git clone https://github.com/TanLP2003/TKXDPM.20241.19.git
cd TKXDPM.20241.19
```
- Tải các dependency cần thiết
```shell
./mvnw.cmd clean install (hãy chạy lại nhiều lần nếu thất bại)
```
- Bắt đầu code
```shell
git fetch origin <nhánh của mình> (có thể xem trên github nếu không rõ)
git checkout <nhánh của mình>
```

## Quy trình phát triển
- Pull thay đổi từ nhánh main về local và merge với nhánh của mình để làm việc ở mỗi ngày mới
```sh
git checkout main  // chuyển sang nhánh main, nhớ phải commit ở local trước khi chuyển
git pull origin main
git checkout <nhánh của mình>
git merge main
```
- Commit và đẩy code của mình lên sau mỗi ngày làm việc
```shell
git commit -m <message>
git push origin <nhánh của mình>
```
- Sau khi hoàn thành xong một chức năng, kéo code mới từ nhánh main về để kiểm tra cập nhật và merge vào nhánh của mình, xử lý conflict (nếu có) rồi mới push lên và tạo pull request

### Tham khảo
- Git Command: [https://fullstack.edu.vn/blog/bo-tui-21-lenh-git-co-ban-cach-nho-giup-newdev-lam-chu-git-quan-ly-tot-ma-ngu.html](https://fullstack.edu.vn/blog/bo-tui-21-lenh-git-co-ban-cach-nho-giup-newdev-lam-chu-git-quan-ly-tot-ma-ngu.html)
- Git Flow: [https://youtu.be/vQgcl8VouLU?si=xqRLPIFA4GdMqrM-](https://youtu.be/vQgcl8VouLU?si=xqRLPIFA4GdMqrM-)