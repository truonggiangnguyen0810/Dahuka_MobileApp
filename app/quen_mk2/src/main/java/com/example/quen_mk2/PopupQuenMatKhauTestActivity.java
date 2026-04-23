//package com.example.quen_mk2;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.widget.FrameLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class PopupQuenMatKhauTestActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        FrameLayout root = new FrameLayout(this);
//        setContentView(root);
//
//        new Handler(Looper.getMainLooper()).post(() -> {
//            PopupQuenMatKhau.hienPopup(PopupQuenMatKhauTestActivity.this);
//        });
//    }
//}

package com.example.quen_mk2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PopupQuenMatKhauTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout root = new FrameLayout(this);
        setContentView(root);

        new Handler(Looper.getMainLooper()).post(() -> {
            PopupQuenMatKhau.hienPopup(PopupQuenMatKhauTestActivity.this);
        });
    }
}