# Hệ thống quản lý vắc xin và tiêm chủng Long Châu

## Giới thiệu

Phần mềm Hệ thống quản lý vắc xin và tiêm chủng Long Châu là một ứng dụng desktop được phát triển bằng Java Swing, giúp quản lý thông tin về vắc xin, lịch tiêm chủng và theo dõi tình trạng tiêm chủng của người dân.

## Tính năng chính

1. **Quản lý Người dùng**

   - Đăng nhập với 3 vai trò: Admin, Bác sĩ, và Khách hàng
   - Quản lý thông tin cá nhân
   - Phân quyền chức năng theo vai trò

2. **Quản lý Vắc xin và Nhà sản xuất**

   - Thêm, sửa, xóa thông tin vắc xin
   - Theo dõi số lượng tồn kho theo từng lô vắc xin
   - Tự động cập nhật số lượng khi:
     - Thêm mới chỉ định tiêm: Giảm số lượng vắc xin
     - Xóa chỉ định tiêm: Hoàn trả số lượng vắc xin
     - Chỉnh sửa chỉ định (thay đổi vắc xin): Cập nhật số lượng tương ứng
   - Quản lý thông tin nhà sản xuất:
     - Thêm, sửa, xóa thông tin nhà sản xuất

3. **Quản lý Bệnh và Phòng bệnh**

   - Quản lý danh mục bệnh:
     - Thêm, sửa, xóa thông tin bệnh
     - Mô tả chi tiết về bệnh
   - Quản lý mối liên hệ giữa bệnh và vắc xin:
     - Thiết lập vắc xin phòng ngừa cho từng bệnh

4. **Quản lý Tiêm chủng**

   - Theo dõi lịch sử tiêm chủng
   - Quản lý trạng thái tiêm chủng
   - Ghi chú và theo dõi phản ứng sau tiêm
   - Tự động cập nhật số lượng vắc xin khi thay đổi chỉ định

## Công nghệ sử dụng

- **Ngôn ngữ**: Java
- **GUI Framework**: Java Swing
- **Cơ sở dữ liệu**: MySQL + phpMyAdmin
- **Build tool**: Maven
- **IDE**: NetBeans


## Cấu trúc dự án

```
src/
├── main/
│   ├── java/
│   │   └── com/uit/vaccinemanagement/
│   │       ├── controller/    # Xử lý logic nghiệp vụ
│   │       ├── dao/          # Tương tác với CSDL
│   │       ├── model/        # Các lớp đối tượng
│   │       ├── util/         # Tiện ích
│   │       ├── view/         # Giao diện người dùng
│   │       └── VaccineManagement.java  # Class chính
│   └── resources/
└── test/
    └── java/   # Unit tests
```

## Cài đặt và Chạy

1. **Yêu cầu hệ thống**

   - Java Development Kit (JDK) 17 trở lên
   - Maven
   - Visual Studio Code với các extension Java

2. **Cài đặt**

   ```bash
   # Clone repository
   git clone https://github.com/Minhtam270293/uit_vaccine_management.git

   # Di chuyển vào thư mục dự án
   cd VaccineManagement

   # Build project
   mvn clean install
   ```

3. **Chạy ứng dụng**
   ```bash
   mvn exec:java
   ```

## Cấu hình Cơ sở dữ liệu

- **MySQL + phpMyAdmin**: Được cấu hình trong `DBConnection.java` với các thông số kết nối đến MySQL và quản lý qua phpMyAdmin. Cụ thể:

  - **URL**: `jdbc:mysql://sql12.freesqldatabase.com:3306/sql12796261?useSSL=false&serverTimezone=UTC`
  - **User**: `sql12796261`
  - **Password**: `w3gbfV71bu`

  Đảm bảo rằng MySQL server đang chạy và phpMyAdmin được cấu hình để quản lý cơ sở dữ liệu. Để bật MySQL server và phpMyAdmin:

  1. Mở **XAMPP Control Panel**.
  2. Nhấn **Start** cho cả **Apache** (để chạy phpMyAdmin) và **MySQL** (để chạy cơ sở dữ liệu).
  3. Truy cập phpMyAdmin qua trình duyệt tại địa chỉ: `http://localhost/phpmyadmin`.

  Các thông tin cấu hình trên có thể được thay đổi tùy theo môi trường triển khai.

  - **Sử dụng file cơ sở dữ liệu local**:
  Nếu muốn sử dụng file `.sql` để khởi tạo cơ sở dữ liệu, thực hiện các bước sau:
  
  1. Truy cập **phpMyAdmin** tại `http://localhost/phpmyadmin`.
  2. Tạo một cơ sở dữ liệu mới (ví dụ: `uit_vaccine_management`).
  3. Chọn cơ sở dữ liệu vừa tạo, sau đó vào tab **Import**.
  4. Nhấn **Choose File** và chọn file `sql12796261.sql` trong thư mục `sql`.
  5. Nhấn **Go** để import dữ liệu.

  Sau khi hoàn tất, cơ sở dữ liệu sẽ được khởi tạo với các bảng và dữ liệu mẫu.