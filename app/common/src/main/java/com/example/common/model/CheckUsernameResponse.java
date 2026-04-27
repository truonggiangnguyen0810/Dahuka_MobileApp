package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class CheckUsernameResponse {
    @SerializedName("exists")
    private boolean exists;

    @SerializedName("message")
    private String message;

    public boolean isExists() { return exists; }
    public void setExists(boolean exists) { this.exists = exists; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
