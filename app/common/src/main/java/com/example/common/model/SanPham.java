package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class SanPham {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaSP")
    private String maSP;

    @SerializedName("MaDMSP")
    private int maDMSP;

    @SerializedName("TenSP")
    private String tenSP;

    @SerializedName("ThongTinSanPham")
    private String thongTinSanPham;

    @SerializedName("MoTa")
    private String moTa;

    @SerializedName("MoTaChiTiet")
    private String moTaChiTiet;

    @SerializedName("TinhNang")
    private String tinhNang;

    @SerializedName("ThongSo")
    private String thongSo;

    @SerializedName("CongSuatLoc")
    private String congSuatLoc;

    @SerializedName("LoaiMay")
    private String loaiMay;

    @SerializedName("CongNgheLoc")
    private String congNgheLoc;

    @SerializedName("DungTichBinhChua")
    private String dungTichBinhChua;

    @SerializedName("NhietDoNuocNong")
    private double nhietDoNuocNong;

    @SerializedName("NhietDoNuocLanh")
    private double nhietDoNuocLanh;

    @SerializedName("NamRaMat")
    private int namRaMat;

    @SerializedName("SoLoiLoc")
    private int soLoiLoc;

    @SerializedName("KichThuoc")
    private String kichThuoc;

    @SerializedName("KhoiLuong")
    private String khoiLuong;

    @SerializedName("NoiSX")
    private String noiSX;

    @SerializedName("ThoiGianBH")
    private String thoiGianBH;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public int getMaDMSP() { return maDMSP; }
    public void setMaDMSP(int maDMSP) { this.maDMSP = maDMSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public String getThongTinSanPham() { return thongTinSanPham; }
    public void setThongTinSanPham(String thongTinSanPham) { this.thongTinSanPham = thongTinSanPham; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getMoTaChiTiet() { return moTaChiTiet; }
    public void setMoTaChiTiet(String moTaChiTiet) { this.moTaChiTiet = moTaChiTiet; }

    public String getTinhNang() { return tinhNang; }
    public void setTinhNang(String tinhNang) { this.tinhNang = tinhNang; }

    public String getThongSo() { return thongSo; }
    public void setThongSo(String thongSo) { this.thongSo = thongSo; }

    public String getCongSuatLoc() { return congSuatLoc; }
    public void setCongSuatLoc(String congSuatLoc) { this.congSuatLoc = congSuatLoc; }

    public String getLoaiMay() { return loaiMay; }
    public void setLoaiMay(String loaiMay) { this.loaiMay = loaiMay; }

    public String getCongNgheLoc() { return congNgheLoc; }
    public void setCongNgheLoc(String congNgheLoc) { this.congNgheLoc = congNgheLoc; }

    public String getDungTichBinhChua() { return dungTichBinhChua; }
    public void setDungTichBinhChua(String dungTichBinhChua) { this.dungTichBinhChua = dungTichBinhChua; }

    public double getNhietDoNuocNong() { return nhietDoNuocNong; }
    public void setNhietDoNuocNong(double nhietDoNuocNong) { this.nhietDoNuocNong = nhietDoNuocNong; }

    public double getNhietDoNuocLanh() { return nhietDoNuocLanh; }
    public void setNhietDoNuocLanh(double nhietDoNuocLanh) { this.nhietDoNuocLanh = nhietDoNuocLanh; }

    public int getNamRaMat() { return namRaMat; }
    public void setNamRaMat(int namRaMat) { this.namRaMat = namRaMat; }

    public int getSoLoiLoc() { return soLoiLoc; }
    public void setSoLoiLoc(int soLoiLoc) { this.soLoiLoc = soLoiLoc; }

    public String getKichThuoc() { return kichThuoc; }
    public void setKichThuoc(String kichThuoc) { this.kichThuoc = kichThuoc; }

    public String getKhoiLuong() { return khoiLuong; }
    public void setKhoiLuong(String khoiLuong) { this.khoiLuong = khoiLuong; }

    public String getNoiSX() { return noiSX; }
    public void setNoiSX(String noiSX) { this.noiSX = noiSX; }

    public String getThoiGianBH() { return thoiGianBH; }
    public void setThoiGianBH(String thoiGianBH) { this.thoiGianBH = thoiGianBH; }
}
