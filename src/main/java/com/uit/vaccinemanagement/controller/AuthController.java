package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.model.NguoiDung;

public class AuthController {

    public void dangKy(NguoiDung nguoiDung) {
        System.out.println("Đăng ký thành công cho: " + nguoiDung.getHoTen());
    }

    public void dangNhap(String tenDangNhap, String matKhau) {
        System.out.println("Đăng nhập thành công cho: " + tenDangNhap);
    }
}
