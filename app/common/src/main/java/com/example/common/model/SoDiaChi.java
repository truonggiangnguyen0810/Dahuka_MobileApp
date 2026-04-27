package com.example.common.model;

/**
 * Model địa chỉ - KHÔNG dùng @SerializedName vì server trả về
 * cùng lúc cả PascalCase và camelCase trong một object.
 * Dùng SoDiaChiDeserializer để parse thủ công.
 */
public class SoDiaChi {
    private String _id;
    private String maDiaChi;
    private String maKhachHang;
    private String tenNguoiNhan;
    private String email;
    private String thanhPho;
    private String quanHuyen;
    private String phuongXa;
    private String diaChiCuThe;
    private int diaChiMacDinh;

    public String get_id() { return _id; }
    public void set_id(String v) { this._id = v; }

    public String getMaDiaChi() { return maDiaChi; }
    public void setMaDiaChi(String v) { this.maDiaChi = v; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String v) { this.maKhachHang = v; }

    public String getTenNguoiNhan() { return tenNguoiNhan; }
    public void setTenNguoiNhan(String v) { this.tenNguoiNhan = v; }

    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }

    public String getThanhPho() { return thanhPho; }
    public void setThanhPho(String v) { this.thanhPho = v; }

    public String getQuanHuyen() { return quanHuyen; }
    public void setQuanHuyen(String v) { this.quanHuyen = v; }

    public String getPhuongXa() { return phuongXa; }
    public void setPhuongXa(String v) { this.phuongXa = v; }

    public String getDiaChiCuThe() { return diaChiCuThe; }
    public void setDiaChiCuThe(String v) { this.diaChiCuThe = v; }

    public int getDiaChiMacDinh() { return diaChiMacDinh; }
    public void setDiaChiMacDinh(int v) { this.diaChiMacDinh = v; }
}
