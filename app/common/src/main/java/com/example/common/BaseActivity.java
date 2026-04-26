package com.example.common;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupTaskbar() {
        // TaskbarLayout tự xử lý btnCart và btnMenu trong onFinishInflate()
    }

    protected void chuyenManHinh(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            startActivity(new Intent(this, clazz));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
