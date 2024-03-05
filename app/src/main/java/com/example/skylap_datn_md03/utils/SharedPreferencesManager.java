package com.example.skylap_datn_md03.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String SHARED_PREF_NAME = "id_user_auth";
    private static final String KEY_USER_ID = "uid";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        // Tạo đối tượng SharedPreferences
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Tạo đối tượng Editor
        editor = sharedPreferences.edit();
    }

    // Lưu trữ userId vào SharedPreferences
    public void saveUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
    }

    // Lấy userId từ SharedPreferences
    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, "");
    }

    public void clearUserId() {
        editor.remove(KEY_USER_ID);
        editor.apply();
    }
}
