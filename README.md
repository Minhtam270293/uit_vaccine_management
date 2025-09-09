# Đồ án Quản lý Tiêm chủng Vaccine

## Giới thiệu

Phần mềm Quản lý Tiêm chủng Vaccine là một ứng dụng desktop được phát triển bằng Java Swing, giúp quản lý thông tin về vaccine, lịch tiêm chủng và theo dõi tình trạng tiêm chủng của người dân.

## Tính năng chính

1. **Quản lý người dùng**

   - Đăng nhập với 3 vai trò: Admin, Bác sĩ, và Khách hàng
   - Quản lý thông tin cá nhân
   - Phân quyền chức năng theo vai trò

2. **Quản lý Vaccine và Nhà sản xuất**

   - Thêm, sửa, xóa thông tin vaccine
   - Theo dõi số lượng tồn kho theo từng lô vaccine
   - Tự động cập nhật số lượng khi:
     - Thêm mới chỉ định tiêm: Giảm số lượng vaccine
     - Xóa chỉ định tiêm: Hoàn trả số lượng vaccine
     - Chỉnh sửa chỉ định (thay đổi vaccine): Cập nhật số lượng tương ứng
   - Quản lý thông tin nhà sản xuất:
     - Thêm, sửa, xóa thông tin nhà sản xuất

3. **Quản lý Bệnh và Phòng bệnh**

   - Quản lý danh mục bệnh:
     - Thêm, sửa, xóa thông tin bệnh
     - Mô tả chi tiết về bệnh
   - Quản lý mối liên hệ giữa bệnh và vaccine:
     - Thiết lập vaccine phòng ngừa cho từng bệnh

4. **Quản lý Tiêm chủng**

   - Theo dõi lịch sử tiêm chủng
   - Quản lý trạng thái tiêm chủng
   - Ghi chú và theo dõi phản ứng sau tiêm
   - Tự động cập nhật số lượng vaccine khi thay đổi chỉ định

## Công nghệ sử dụng

- **Ngôn ngữ**: Java
- **GUI Framework**: Java Swing
- **Cơ sở dữ liệu**: MySQL (cloud)
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

- **MySQL Cloud**: Được cấu hình trong `DBConnection.java` với các thông số kết nối đến MySQL cloud
