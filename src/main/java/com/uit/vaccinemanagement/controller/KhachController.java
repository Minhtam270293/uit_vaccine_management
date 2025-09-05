package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.model.*;
import java.util.*;

public class KhachController {

    private List<TiemChung> lichTiem = new ArrayList<>();

    public void dangKy(NguoiDung nguoiDung) {
        System.out.println("Đăng ký thành công cho: " + nguoiDung.getHoTen());
    }

    public void dangNhap(String tenDangNhap, String matKhau) {
        System.out.println("Đăng nhập thành công cho: " + tenDangNhap);
    }

    public void xemLichTiem() {
        System.out.println("--- Lịch tiêm ---");
        for (TiemChung t : lichTiem) {
            System.out.println(t.getMaTiemChung());
        }
    }

}
