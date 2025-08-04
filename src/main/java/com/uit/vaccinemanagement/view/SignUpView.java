package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.*;

public class SignUpView {

    public interface SignUpCallback {

        void onRegister(String username, String hoTen, String email, String password, String dob, String gender);
    }

    public void showSignUpUI(SignUpCallback callback) {
        JFrame frame = new JFrame("Đăng ký tài khoản mới");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 5, 5));

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        JTextField txtUser = new JTextField();
        JLabel lblHoTen = new JLabel("Họ tên:");
        JTextField txtHoTen = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();

        JLabel lblPass = new JLabel("Mật khẩu:");
        JPasswordField txtPass = new JPasswordField();
        JLabel lblDob = new JLabel("Ngày sinh (yyyy-mm-dd):");
        JTextField txtDob = new JTextField();
        JLabel lblGender = new JLabel("Giới tính:");
        String[] genders = {"Nam", "Nu", "Khac"};
        JComboBox<String> cbGender = new JComboBox<>(genders);

        JButton btnRegister = new JButton("Đăng ký");

        panel.add(lblUser);
        panel.add(txtUser);
        panel.add(lblHoTen);
        panel.add(txtHoTen);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(lblDob);
        panel.add(txtDob);
        panel.add(lblGender);
        panel.add(cbGender);
        panel.add(btnRegister);
        panel.add(new JLabel());

        btnRegister.addActionListener(e -> {
            String username = txtUser.getText();
            String hoTen = txtHoTen.getText();

            String email = txtEmail.getText();
            String password = new String(txtPass.getPassword());
            String dob = txtDob.getText();
            String gender = (String) cbGender.getSelectedItem();
            frame.dispose();
            callback.onRegister(username, hoTen, email, password, dob, gender);
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
