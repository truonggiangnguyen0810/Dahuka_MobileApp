package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private AuthUser user;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public AuthUser getUser() { return user; }
    public void setUser(AuthUser user) { this.user = user; }
}
