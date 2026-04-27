package com.example.common.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Custom deserializer để xử lý server trả về cả PascalCase lẫn camelCase
 * trong cùng một JSON object.
 * Ưu tiên PascalCase (dữ liệu gốc), fallback sang camelCase nếu không có.
 */
public class SoDiaChiDeserializer implements JsonDeserializer<SoDiaChi> {

    @Override
    public SoDiaChi deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        SoDiaChi sdc = new SoDiaChi();

        sdc.set_id(getString(obj, "_id"));
        sdc.setMaDiaChi(getString(obj, "MaDiaChi"));

        // MaKhachHang có thể là số hoặc string
        JsonElement maKH = obj.get("MaKhachHang");
        if (maKH == null) maKH = obj.get("maKhachHang");
        if (maKH != null && !maKH.isJsonNull()) {
            sdc.setMaKhachHang(maKH.getAsString());
        }

        // Các field text: ưu tiên PascalCase, fallback camelCase
        sdc.setTenNguoiNhan(getStringFallback(obj, "TenNguoiNhan", "tenNguoiNhan"));
        sdc.setEmail(getStringFallback(obj, "Email", "email"));
        sdc.setThanhPho(getStringFallback(obj, "ThanhPho", "thanhPho"));
        sdc.setQuanHuyen(getStringFallback(obj, "QuanHuyen", "quanHuyen"));
        sdc.setPhuongXa(getStringFallback(obj, "PhuongXa", "phuongXa"));
        sdc.setDiaChiCuThe(getStringFallback(obj, "DiaChiCuThe", "diaChiCuThe"));

        // DiaChiMacDinh: ưu tiên PascalCase
        JsonElement macDinh = obj.get("DiaChiMacDinh");
        if (macDinh == null) macDinh = obj.get("diaChiMacDinh");
        if (macDinh != null && !macDinh.isJsonNull()) {
            sdc.setDiaChiMacDinh(macDinh.getAsInt());
        }

        return sdc;
    }

    private String getString(JsonObject obj, String key) {
        JsonElement el = obj.get(key);
        if (el == null || el.isJsonNull()) return null;
        return el.getAsString();
    }

    private String getStringFallback(JsonObject obj, String primary, String fallback) {
        JsonElement el = obj.get(primary);
        if (el != null && !el.isJsonNull()) return el.getAsString();
        el = obj.get(fallback);
        if (el != null && !el.isJsonNull()) return el.getAsString();
        return null;
    }
}
