package com.example.common;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {

    private static final String PREF_NAME = "dahuka_user_session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_MA_KHACH_HANG = "ma_khach_hang";

    public static void saveLogin(Context context, int userId, String username, String role) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putInt(KEY_USER_ID, userId)
                .putString(KEY_USERNAME, username)
                .putString(KEY_ROLE, role)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public static void saveMaKhachHang(Context context, String maKhachHang) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_MA_KHACH_HANG, maKhachHang).apply();
    }

    public static int getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public static String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USERNAME, "");
    }

    public static String getRole(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_ROLE, "");
    }

    public static String getMaKhachHang(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String saved = prefs.getString(KEY_MA_KHACH_HANG, null);
        if (saved != null) return saved;
        // fallback: format từ userId
        int userId = getUserId(context);
        if (userId < 0) return null;
        return String.format("KH_%03d", userId);
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
