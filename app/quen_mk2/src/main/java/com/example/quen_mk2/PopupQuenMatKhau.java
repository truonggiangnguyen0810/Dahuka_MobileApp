//package com.example.quen_mk2;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class PopupQuenMatKhau {
//
//    public static void hienPopup(Context context) {
//        try {
//            Dialog dialog = new Dialog(context);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.popup_quen_mat_khau);
//            dialog.setCancelable(true);
//
//            if (dialog.getWindow() != null) {
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().setLayout(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                );
//            }
//
//            EditText edtSoDienThoai = dialog.findViewById(R.id.edtSoDienThoaiQuenMk);
//            Button btnXacNhan = dialog.findViewById(R.id.btnXacNhanPopupQuenMk);
//
//            if (edtSoDienThoai == null || btnXacNhan == null) {
//                Toast.makeText(context, "Popup thiếu view", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            btnXacNhan.setOnClickListener(v -> {
//                String soDienThoai = edtSoDienThoai.getText().toString().trim();
//
//                if (soDienThoai.isEmpty()) {
//                    Toast.makeText(context, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
//                } else {
//                    dialog.dismiss();
//                    Intent intent = new Intent(context, MainActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//
//            dialog.show();
//
//        } catch (Exception e) {
//            Toast.makeText(context, "Lỗi popup: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//    }
//}


package com.example.quen_mk2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PopupQuenMatKhau {

    public static void hienPopup(Context context) {
        try {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_quen_mat_khau);
            dialog.setCancelable(true);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
            }

            EditText edtSoDienThoai = dialog.findViewById(R.id.edtSoDienThoaiQuenMk);
            Button btnXacNhan = dialog.findViewById(R.id.btnXacNhanPopupQuenMk);

            if (edtSoDienThoai == null || btnXacNhan == null) {
                Toast.makeText(context, "Popup thiếu view", Toast.LENGTH_LONG).show();
                return;
            }

            btnXacNhan.setOnClickListener(v -> {
                String soDienThoai = edtSoDienThoai.getText().toString().trim();

                if (soDienThoai.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });

            dialog.show();

        } catch (Exception e) {
            Toast.makeText(context, "Lỗi popup: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}