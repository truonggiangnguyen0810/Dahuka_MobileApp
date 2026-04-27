package com.example.quen_mk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ManHinhXacThucSdtQuenMkActivity extends AppCompatActivity {

    private EditText edtPhone;
    private Button btnTiepTuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_xac_thuc_sdt_quenmk);

        edtPhone = findViewById(R.id.edtPhone);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic xử lý tiếp theo
            }
        });
    }
}
