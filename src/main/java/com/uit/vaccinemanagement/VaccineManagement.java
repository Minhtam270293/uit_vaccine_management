package com.uit.vaccinemanagement;

import com.uit.vaccinemanagement.controller.*;
import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.model.*;
import com.uit.vaccinemanagement.util.PasswordUtils;
import com.uit.vaccinemanagement.util.Role;
import com.uit.vaccinemanagement.view.*;
import java.sql.Date;

import javax.swing.JOptionPane;

public class VaccineManagement {

    public static void main(String[] args) {
        showLogin();
    }

    public static void showLogin() {
        AuthView authView = new AuthView();
        authView.showAuthUI(new AuthView.AuthCallback() {
            @Override
            public void onLogin(String email, String password) {
                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
                for (NguoiDung nd : nguoiDungDAO.getAllNguoiDung()) {
                    if (nd.getEmail().equals(email)
                            && nd.getMatKhau().equals(PasswordUtils.hashPassword(password))) {
                        switch (nd.getVaiTro()) {
                            case ADMIN:
                                new AdminView(nd).showAdminUI();
                                break;
                            case BACSI:
                                new BacSiView(nd).showBacSiUI();
                                break;
                            case KHACH:
                                new KhachView(nd).showKhachUI();
                                break;
                        }
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Sai email hoặc mật khẩu!");
                showLogin();
            }

            @Override
            public void onSignUpClicked() {
                showSignUp();
            }
        });
    }

    private static void showSignUp() {
        SignUpView signUpView = new SignUpView();
        signUpView.showSignUpUI(new SignUpView.SignUpCallback() {
            @Override
            public void onRegister(String username, String hoTen, String email, String password, String dob, String gender) {
                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
                NguoiDung newUser = new NguoiDung();
                newUser.setHoTen(hoTen);
                newUser.setTenDangNhap(username);
                newUser.setEmail(email);
                newUser.setMatKhau(com.uit.vaccinemanagement.util.PasswordUtils.hashPassword(password));
                newUser.setVaiTro(com.uit.vaccinemanagement.util.Role.KHACH); // Default role
                try {
                    newUser.setNgaySinh(java.sql.Date.valueOf(dob));
                } catch (Exception e) {
                    newUser.setNgaySinh(null);
                }
                newUser.setGioiTinh(gender);

                if (nguoiDungDAO.addNguoiDung(newUser)) {
                    JOptionPane.showMessageDialog(null, "Đăng ký thành công! Vui lòng đăng nhập.");
                    showLogin();
                } else {
                    JOptionPane.showMessageDialog(null, "Đăng ký thất bại! Email hoặc tên đăng nhập đã tồn tại.");
                    showSignUp();
                }
            }

            @Override
            public void onBack() {
                showLogin();
            }
        });
    }
}
