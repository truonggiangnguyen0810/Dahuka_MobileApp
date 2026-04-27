package com.example.dang_nhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.common.UserManager;
import com.example.common.model.AuthUser;
import com.example.common.model.LoginResponse;
import com.example.common.network.RetrofitClient;
import com.example.dang_ky.ManHinhDangKySdtActivity;
import com.example.quen_mk.ManHinhXacThucSdtQuenMkActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtPhone;
    private EditText edtPassword;
    private TextView tvRegister;
    private TextView tvQuenMatKhau;
    private View btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (UserManager.isLoggedIn(this)) {
            navigateToMain();
            return;
        }

        setContentView(R.layout.dang_nhap);

        anhXaView();
        xuLyDangNhap();
        xuLyChuyenDangKy();
        xuLyChuyenQuenMatKhau();
    }

    private void navigateToMain() {
        try {
            Class<?> clazz = Class.forName("com.example.common.TrangChuActivity");
            Intent intent = new Intent(this, clazz);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Không thể chuyển màn hình chính", Toast.LENGTH_SHORT).show();
        }
    }

    private void anhXaView() {
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvQuenMatKhau = findViewById(R.id.quenmatkhau);
    }

    private void xuLyDangNhap() {
        btnLogin.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (phone.isEmpty()) {
                edtPhone.setError("Vui lòng nhập số điện thoại");
                edtPhone.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                edtPassword.setError("Vui lòng nhập mật khẩu");
                edtPassword.requestFocus();
                return;
            }

            AuthUser user = new AuthUser();
            user.setUsername(phone);
            user.setPassword(password);

            btnLogin.setEnabled(false);

            RetrofitClient.getApiService().login(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    btnLogin.setEnabled(true);

                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse loginResponse = response.body();
                        AuthUser loggedInUser = loginResponse.getUser();

                        UserManager.saveLogin(MainActivity.this,
                                loggedInUser.getId(),
                                loggedInUser.getUsername(),
                                loggedInUser.getRole());

                        Toast.makeText(MainActivity.this,
                                "Đăng nhập thành công",
                                Toast.LENGTH_SHORT).show();

                        navigateToMain();
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Sai tài khoản hoặc mật khẩu",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    btnLogin.setEnabled(true);
                    Toast.makeText(MainActivity.this,
                            "Lỗi kết nối: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void xuLyChuyenDangKy() {
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManHinhDangKySdtActivity.class);
            startActivity(intent);
        });
    }

    private void xuLyChuyenQuenMatKhau() {
        tvQuenMatKhau.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManHinhXacThucSdtQuenMkActivity.class);
            startActivity(intent);
        });
    }
}
