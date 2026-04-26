package com.example.xem_san_pham;

public class SanPham {
    private String _id;
    private String maSP;
    private String ten;
    private String gia;
    private String hinhAnhUrl;

    public SanPham(String _id, String maSP, String ten, String gia, String hinhAnhUrl) {
        this._id = _id;
        this.maSP = maSP;
        this.ten = ten;
        this.gia = gia;
        this.hinhAnhUrl = hinhAnhUrl;
    }

    public String get_id() { return _id; }
    public String getMaSP() { return maSP; }
    public String getTen() { return ten; }
    public String getGia() { return gia; }
    public String getHinhAnhUrl() { return hinhAnhUrl; }
}
