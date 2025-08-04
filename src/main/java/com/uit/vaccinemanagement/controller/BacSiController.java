package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.model.TiemChung;
import java.util.*;

public class BacSiController {

    private List<TiemChung> tiemChungs = new ArrayList<>();

    public void createTiemChung(TiemChung t) {
        tiemChungs.add(t);
        System.out.println("Chỉ định tiêm mới đã được tạo: " + t.getMaTiemChung());
    }

    public void listTiemChungs() {
        System.out.println("--- Danh sách chỉ định tiêm ---");
        for (TiemChung t : tiemChungs) {
            System.out.println(t.getMaTiemChung());
        }
    }
}
