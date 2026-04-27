package com.example.so_dia_chi;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

    private String id;
    private String fullName;
    private String phone;
    private String detailAddress;
    private String fullAddress;
    private boolean isDefault;

    public Address() {}

    public Address(String id, String fullName, String phone, String detailAddress, String fullAddress, boolean isDefault) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.detailAddress = detailAddress;
        this.fullAddress = fullAddress;
        this.isDefault = isDefault;
    }

    public Address(String fullName, String phone, String detailAddress, String fullAddress, boolean isDefault) {
        this.fullName = fullName;
        this.phone = phone;
        this.detailAddress = detailAddress;
        this.fullAddress = fullAddress;
        this.isDefault = isDefault;
    }

    protected Address(Parcel in) {
        id = in.readString();
        fullName = in.readString();
        phone = in.readString();
        detailAddress = in.readString();
        fullAddress = in.readString();
        isDefault = in.readByte() != 0;
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

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDetailAddress() { return detailAddress; }
    public void setDetailAddress(String detailAddress) { this.detailAddress = detailAddress; }

    public String getFullAddress() { return fullAddress; }
    public void setFullAddress(String fullAddress) { this.fullAddress = fullAddress; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fullName);
        dest.writeString(phone);
        dest.writeString(detailAddress);
        dest.writeString(fullAddress);
        dest.writeByte((byte) (isDefault ? 1 : 0));
    }
}
