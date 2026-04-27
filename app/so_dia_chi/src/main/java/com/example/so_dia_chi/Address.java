package com.example.so_dia_chi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    @SerializedName("_id")
    private String id;

    @SerializedName("TenNguoiNhan")
    private String tenNguoiNhan;

    @SerializedName("Email")
    private String email;

    @SerializedName("ThanhPho")
    private String thanhPho;

    @SerializedName("QuanHuyen")
    private String quanHuyen;

    @SerializedName("PhuongXa")
    private String phuongXa;

    @SerializedName("DiaChiCuThe")
    private String diaChiCuThe;

    @SerializedName("DiaChiMacDinh")
    private int diaChiMacDinh;

    public Address() {}

    protected Address(Parcel in) {
        id = in.readString();
        tenNguoiNhan = in.readString();
        email = in.readString();
        thanhPho = in.readString();
        quanHuyen = in.readString();
        phuongXa = in.readString();
        diaChiCuThe = in.readString();
        diaChiMacDinh = in.readInt();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenNguoiNhan() { return tenNguoiNhan; }
    public void setTenNguoiNhan(String tenNguoiNhan) { this.tenNguoiNhan = tenNguoiNhan; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getThanhPho() { return thanhPho; }
    public void setThanhPho(String thanhPho) { this.thanhPho = thanhPho; }

    public String getQuanHuyen() { return quanHuyen; }
    public void setQuanHuyen(String quanHuyen) { this.quanHuyen = quanHuyen; }

    public String getPhuongXa() { return phuongXa; }
    public void setPhuongXa(String phuongXa) { this.phuongXa = phuongXa; }

    public String getDiaChiCuThe() { return diaChiCuThe; }
    public void setDiaChiCuThe(String diaChiCuThe) { this.diaChiCuThe = diaChiCuThe; }

    public int getDiaChiMacDinh() { return diaChiMacDinh; }
    public void setDiaChiMacDinh(int diaChiMacDinh) { this.diaChiMacDinh = diaChiMacDinh; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(tenNguoiNhan);
        dest.writeString(email);
        dest.writeString(thanhPho);
        dest.writeString(quanHuyen);
        dest.writeString(phuongXa);
        dest.writeString(diaChiCuThe);
        dest.writeInt(diaChiMacDinh);
    }
}

//27/04/2026