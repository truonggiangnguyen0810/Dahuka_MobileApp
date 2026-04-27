package com.example.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TaskbarLayout extends LinearLayout {

    private static final String TAG = "TaskbarLayout";
    private PopupWindow menuPopup;
    private View dimOverlay;

    public TaskbarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskbarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupCartButton();
        setupMenuButton();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dongMenu();
    }

    private void setupCartButton() {
        ImageButton btnCart = findViewById(R.id.btnCart);
        if (btnCart != null && getContext() instanceof Activity) {
            btnCart.setOnClickListener(v -> {
                try {
                    Class<?> clazz = Class.forName("com.example.gio_hang.GioHangActivity");
                    ((Activity) getContext()).startActivity(new Intent(getContext(), clazz));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void setupMenuButton() {
        ImageButton btnMenu = findViewById(R.id.btnMenu);
        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> {
                if (menuPopup != null && menuPopup.isShowing()) {
                    dongMenu();
                } else {
                    moMenu();
                }
            });
        }
    }

    private void moMenu() {
        Context ctx = getContext();
        if (!(ctx instanceof Activity)) return;
        Activity activity = (Activity) ctx;

        boolean loggedIn = UserManager.isLoggedIn(ctx);

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View menuView;
        if (loggedIn) {
            menuView = inflater.inflate(R.layout.menu_saudangnhap, null);
        } else {
            menuView = inflater.inflate(R.layout.menu, null);
        }

        menuPopup = new PopupWindow(menuView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                true);

        menuPopup.setOutsideTouchable(true);
        menuPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        menuPopup.setElevation(16f);
        menuPopup.setOnDismissListener(() -> removeDimOverlay());

        int popupWidth = (int) (280 * ctx.getResources().getDisplayMetrics().density);

        ImageButton btnMenu = findViewById(R.id.btnMenu);
        if (btnMenu == null) return;

        int[] location = new int[2];
        btnMenu.getLocationOnScreen(location);
        int x = Math.max(0, location[0] + btnMenu.getWidth() - popupWidth);
        int y = location[1] + btnMenu.getHeight() + 4;

        menuPopup.showAtLocation(this, Gravity.NO_GRAVITY, x, y);

        addDimOverlay(activity);

        setupMenuClicks(menuView, loggedIn, activity);
    }

    private void dongMenu() {
        if (menuPopup != null && menuPopup.isShowing()) {
            menuPopup.dismiss();
        }
        removeDimOverlay();
    }

    private void addDimOverlay(Activity activity) {
        View rootView = activity.findViewById(android.R.id.content);
        if (rootView instanceof FrameLayout) {
            dimOverlay = new View(activity);
            dimOverlay.setBackgroundColor(Color.parseColor("#80000000"));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            dimOverlay.setLayoutParams(params);
            dimOverlay.setOnClickListener(v -> dongMenu());
            ((FrameLayout) rootView).addView(dimOverlay);
        }
    }

    private void removeDimOverlay() {
        if (dimOverlay != null && dimOverlay.getParent() instanceof ViewGroup) {
            ((ViewGroup) dimOverlay.getParent()).removeView(dimOverlay);
            dimOverlay = null;
        }
    }

    private void setupMenuClicks(View menuView, boolean loggedIn, Activity activity) {
        TextView tvTrangChu = menuView.findViewById(R.id.tvTrangChu);
        if (tvTrangChu != null) {
            tvTrangChu.setOnClickListener(v -> {
                dongMenu();
                chuyenManHinh(activity, "com.example.common.TrangChuActivity");
            });
        }

        View btnUser = menuView.findViewById(R.id.btnDangNhapMenu);
        Log.d(TAG, "setupMenuClicks: btnDangNhapMenu=" + btnUser + ", loggedIn=" + loggedIn);
        if (btnUser != null) {
            btnUser.setOnClickListener(v -> {
                Log.d(TAG, "btnDangNhapMenu clicked, loggedIn=" + loggedIn);
                dongMenu();
                if (loggedIn) {
                    chuyenManHinh(activity, "com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.MainActivity");
                } else {
                    chuyenManHinh(activity, "com.example.dang_nhap.MainActivity");
                }
            });
        }

        View btnDangKy = menuView.findViewById(R.id.btnDangKyMenu);
        if (btnDangKy != null) {
            btnDangKy.setOnClickListener(v -> {
                dongMenu();
                chuyenManHinh(activity, "com.example.dang_ky.ManHinhDangKySdtActivity");
            });
        }

        View btnDangXuat = menuView.findViewById(R.id.btnDangXuat);
        if (btnDangXuat != null) {
            btnDangXuat.setOnClickListener(v -> {
                UserManager.logout(activity);
                dongMenu();
                Toast.makeText(activity, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                chuyenManHinh(activity, "com.example.common.TrangChuActivity");
                activity.finish();
            });
        }

        TextView tvMayLocNuoc = menuView.findViewById(R.id.tvMayLocNuoc);
        if (tvMayLocNuoc != null) {
            tvMayLocNuoc.setOnClickListener(v -> {
                dongMenu();
                chuyenManHinhLoc(activity, "Máy lọc nước");
            });
        }

        TextView tvLinhKien = menuView.findViewById(R.id.tvLinhKien);
        if (tvLinhKien != null) {
            tvLinhKien.setOnClickListener(v -> {
                dongMenu();
                chuyenManHinhLoc(activity, "Linh kiện");
            });
        }

        TextView tvDichVu = menuView.findViewById(R.id.tvDichVu);
        if (tvDichVu != null) {
            tvDichVu.setOnClickListener(v -> {
                dongMenu();
                chuyenManHinhLoc(activity, "Dịch vụ");
            });
        }

        int[] closeOnlyIds = {
                R.id.tvHuongDanLapDat, R.id.tvDiemBanBaoHanh
        };
        for (int id : closeOnlyIds) {
            View item = menuView.findViewById(id);
            if (item != null) {
                item.setOnClickListener(v -> dongMenu());
            }
        }
    }

    private void chuyenManHinh(Context context, String className) {
        try {
            Log.d(TAG, "chuyenManHinh: " + className);
            Class<?> clazz = Class.forName(className);
            context.startActivity(new Intent(context, clazz));
            Log.d(TAG, "chuyenManHinh: started activity");
        } catch (Exception e) {
            Log.e(TAG, "chuyenManHinh FAILED: " + className, e);
        }
    }

    private void chuyenManHinhLoc(Context context, String loaiFilter) {
        try {
            Class<?> clazz = Class.forName("com.example.xem_san_pham.ManHinhDanhSachSanPhamActivity");
            Intent intent = new Intent(context, clazz);
            intent.putExtra("loai_filter", loaiFilter);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
