//package com.example.quen_mk2;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}



//package com.example.quen_mk2;
//
//import android.os.CountDownTimer;
//import android.widget.TextView;
//import java.util.Locale;
//
//import android.app.Dialog;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.text.InputType;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity extends AppCompatActivity {
//
//    private boolean dangHienMatKhau = false;
//    private boolean dangHienXacNhan = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.man_hinh_quen_mat_khau);
//
//        txtGuiLaiMa = findViewById(R.id.txtGuiLaiMa);
//
//        batDauDemNguoc();
//
//        Button btnXacNhan = findViewById(R.id.btnXacNhan);
//        EditText edtMatKhau = findViewById(R.id.edtMatKhauMoi);
//        ImageView imgMatKhau = findViewById(R.id.imgAnHienMatKhau);
//        EditText edtXacNhan = findViewById(R.id.edtXacNhanMatKhau);
//        ImageView imgXacNhan = findViewById(R.id.imgAnHienXacNhanMatKhau);
//
//        if (btnXacNhan == null || edtMatKhau == null || imgMatKhau == null
//                || edtXacNhan == null || imgXacNhan == null) {
//            return;
//        }
//
//        imgMatKhau.setOnClickListener(v -> {
//            if (dangHienMatKhau) {
//                edtMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            } else {
//                edtMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//            }
//            edtMatKhau.setSelection(edtMatKhau.getText().length());
//            dangHienMatKhau = !dangHienMatKhau;
//        });
//
//        imgXacNhan.setOnClickListener(v -> {
//            if (dangHienXacNhan) {
//                edtXacNhan.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            } else {
//                edtXacNhan.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//            }
//            edtXacNhan.setSelection(edtXacNhan.getText().length());
//            dangHienXacNhan = !dangHienXacNhan;
//        });
//
//        btnXacNhan.setOnClickListener(v -> hienPopupDoiMatKhauThanhCong());
//    }
//    private CountDownTimer countDownTimer;
//    private TextView txtGuiLaiMa;
//
//    private void batDauDemNguoc() {
//        long thoiGianBatDau = 5 * 60 * 1000; // 5 phút = 300000 ms
//
//        countDownTimer = new CountDownTimer(thoiGianBatDau, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                int phut = (int) (millisUntilFinished / 1000) / 60;
//                int giay = (int) (millisUntilFinished / 1000) % 60;
//
//                String thoiGian = String.format(Locale.getDefault(), "%02d:%02d", phut, giay);
//                txtGuiLaiMa.setText("Gửi lại mã sau: " + thoiGian);
//            }
//
//            @Override
//            public void onFinish() {
//                txtGuiLaiMa.setText("Gửi lại mã");
//            }
//        }.start();
//    }
//
//    private void hienPopupDoiMatKhauThanhCong() {
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.popup_doi_mat_khau_thanh_cong);
//        dialog.setCancelable(true);
//
//        if (dialog.getWindow() != null) {
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.getWindow().setLayout(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//        }
//
//        Button btnTiepTucPopup = dialog.findViewById(R.id.btnTiepTucPopupDoiMk);
//        btnTiepTucPopup.setOnClickListener(v -> dialog.dismiss());
//
//        dialog.show();
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//    }
//}

package com.example.quen_mk2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean dangHienMatKhau = false;
    private boolean dangHienXacNhan = false;
    private CountDownTimer countDownTimer;
    private TextView txtGuiLaiMa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_quen_mat_khau);

        txtGuiLaiMa = findViewById(R.id.txtGuiLaiMa);
        batDauDemNguoc();

        Button btnXacNhan = findViewById(R.id.btnXacNhan);
        EditText edtMatKhau = findViewById(R.id.edtMatKhauMoi);
        ImageView imgMatKhau = findViewById(R.id.imgAnHienMatKhau);
        EditText edtXacNhan = findViewById(R.id.edtXacNhanMatKhau);
        ImageView imgXacNhan = findViewById(R.id.imgAnHienXacNhanMatKhau);

        if (btnXacNhan == null || edtMatKhau == null || imgMatKhau == null
                || edtXacNhan == null || imgXacNhan == null || txtGuiLaiMa == null) {
            return;
        }

        imgMatKhau.setOnClickListener(v -> {
            if (dangHienMatKhau) {
                edtMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                edtMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            edtMatKhau.setSelection(edtMatKhau.getText().length());
            dangHienMatKhau = !dangHienMatKhau;
        });

        imgXacNhan.setOnClickListener(v -> {
            if (dangHienXacNhan) {
                edtXacNhan.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                edtXacNhan.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            edtXacNhan.setSelection(edtXacNhan.getText().length());
            dangHienXacNhan = !dangHienXacNhan;
        });

        btnXacNhan.setOnClickListener(v -> hienPopupDoiMatKhauThanhCong());
    }

    private void batDauDemNguoc() {
        long thoiGianBatDau = 5 * 60 * 1000;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(thoiGianBatDau, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int phut = (int) (millisUntilFinished / 1000) / 60;
                int giay = (int) (millisUntilFinished / 1000) % 60;

                String thoiGian = String.format(Locale.getDefault(), "%02d:%02d", phut, giay);
                txtGuiLaiMa.setText("Gửi lại mã sau: " + thoiGian);
            }

            @Override
            public void onFinish() {
                txtGuiLaiMa.setText("Gửi lại mã");
            }
        }.start();
    }

    private void hienPopupDoiMatKhauThanhCong() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_doi_mat_khau_thanh_cong);
        dialog.setCancelable(true);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        Button btnTiepTucPopup = dialog.findViewById(R.id.btnTiepTucPopupDoiMk);

        if (btnTiepTucPopup != null) {
            btnTiepTucPopup.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}