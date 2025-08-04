package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.model.*;
import java.util.*;

public class AdminController {

    private List<Vaccine> vaccines = new ArrayList<>();
    private List<Benh> benhs = new ArrayList<>();
    private List<NhaSanXuat> nhaSanXuats = new ArrayList<>();
    private List<TiemChung> tiemChungs = new ArrayList<>();
    private List<ThongTinMua> giaoDichMuas = new ArrayList<>();

    // Vaccine management
    public void addVaccine(Vaccine v) {
        vaccines.add(v);
        System.out.println("Vaccine added: " + v.getTenVaccine());
    }

    public void editVaccine(int index, Vaccine v) {
        if (index >= 0 && index < vaccines.size()) {
            vaccines.set(index, v);
            System.out.println("Vaccine updated at index " + index);
        }
    }

    public void deleteVaccine(int index) {
        if (index >= 0 && index < vaccines.size()) {
            vaccines.remove(index);
            System.out.println("Vaccine deleted at index " + index);
        }
    }

    public void listVaccines() {
        System.out.println("--- Vaccine List ---");
        for (Vaccine v : vaccines) {
            System.out.println(v.getTenVaccine());
        }
    }

    // Benh management
    public void addBenh(Benh b) {
        benhs.add(b);
        System.out.println("Benh added: " + b.getTenBenh());
    }

    public void editBenh(int index, Benh b) {
        if (index >= 0 && index < benhs.size()) {
            benhs.set(index, b);
            System.out.println("Benh updated at index " + index);
        }
    }

    public void deleteBenh(int index) {
        if (index >= 0 && index < benhs.size()) {
            benhs.remove(index);
            System.out.println("Benh deleted at index " + index);
        }
    }

    public void listBenhs() {
        System.out.println("--- Benh List ---");
        for (Benh b : benhs) {
            System.out.println(b.getTenBenh());
        }
    }

    // Nha San Xuat management
    public void addNhaSanXuat(NhaSanXuat n) {
        nhaSanXuats.add(n);
        System.out.println("Nha San Xuat added: " + n.getTenNhaSX());
    }

    public void editNhaSanXuat(int index, NhaSanXuat n) {
        if (index >= 0 && index < nhaSanXuats.size()) {
            nhaSanXuats.set(index, n);
            System.out.println("Nha San Xuat updated at index " + index);
        }
    }

    public void deleteNhaSanXuat(int index) {
        if (index >= 0 && index < nhaSanXuats.size()) {
            nhaSanXuats.remove(index);
            System.out.println("Nha San Xuat deleted at index " + index);
        }
    }

    public void listNhaSanXuats() {
        System.out.println("--- Nha San Xuat List ---");
        for (NhaSanXuat n : nhaSanXuats) {
            System.out.println(n.getTenNhaSX());
        }
    }

    // Thong Tin Tiem Chung management
    public void listThongTinTiemChung() {
        System.out.println("--- Thong Tin Tiem Chung List ---");
        for (TiemChung t : tiemChungs) {
            System.out.println(t.getMaTiemChung());
        }
    }

    // Giao Dich Mua Vaccine management
    public void listGiaoDichMuaVaccine() {
        System.out.println("--- Giao Dich Mua Vaccine List ---");
        for (ThongTinMua g : giaoDichMuas) {
            System.out.println(g.getMaGiaoDich());
        }
    }

    // Edit personal info
    public void editPersonalInfo() {
        System.out.println("Edit personal info feature");
    }
}
