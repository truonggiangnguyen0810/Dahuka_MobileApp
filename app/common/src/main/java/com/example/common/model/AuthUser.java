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
    private String isStaff;

    @SerializedName("is_active")
    private String isActive;

    @SerializedName("is_superuser")
    private String isSuperuser;

    @SerializedName("last_login")
    private String lastLogin;

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

    public String getIsStaff() { return isStaff; }
    public void setIsStaff(String isStaff) { this.isStaff = isStaff; }

    public String getIsActive() { return isActive; }
    public void setIsActive(String isActive) { this.isActive = isActive; }

    public String getIsSuperuser() { return isSuperuser; }
    public void setIsSuperuser(String isSuperuser) { this.isSuperuser = isSuperuser; }

    public String getLastLogin() { return lastLogin; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }
}
