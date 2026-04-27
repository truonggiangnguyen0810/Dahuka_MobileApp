package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class AuthUser {
    @SerializedName("_id")
    private String _id;

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("Role")
    private String role;

    @SerializedName("is_staff")
    private boolean isStaff;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("is_superuser")
    private boolean isSuperuser;

    @SerializedName("last_login")
    private String lastLogin;

    @SerializedName("TenKhachHang")
    private String tenKhachHang;

    @SerializedName("Email")
    private String email;

    @SerializedName("NgaySinh")
    private String ngaySinh;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isStaff() { return isStaff; }
    public void setStaff(boolean staff) { isStaff = staff; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isSuperuser() { return isSuperuser; }
    public void setSuperuser(boolean superuser) { isSuperuser = superuser; }

    public String getLastLogin() { return lastLogin; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
}
