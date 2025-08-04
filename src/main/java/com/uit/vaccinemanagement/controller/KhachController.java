package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.model.*;
import java.util.*;

public class KhachController {

    private List<ThongTinMua> lichSuMua = new ArrayList<>();
    private List<TiemChung> lichTiem = new ArrayList<>();

    public void dangKy(NguoiDung nguoiDung) {
        System.out.println("Đăng ký thành công cho: " + nguoiDung.getHoTen());
    }

    public void dangNhap(String tenDangNhap, String matKhau) {
        System.out.println("Đăng nhập thành công cho: " + tenDangNhap);
    }

    public void muaVaccine(ThongTinMua mua) {
        lichSuMua.add(mua);
        System.out.println("Mua vaccine thành công, mã giao dịch: " + mua.getMaGiaoDich());
    }

    public void xemLichTiem() {
        System.out.println("--- Lịch tiêm ---");
        for (TiemChung t : lichTiem) {
            System.out.println(t.getMaTiemChung());
        }
    }

    public void xemLichSuMua() {
        System.out.println("--- Lịch sử mua ---");
        for (ThongTinMua m : lichSuMua) {
            System.out.println(m.getMaGiaoDich());
        }
    }
}
