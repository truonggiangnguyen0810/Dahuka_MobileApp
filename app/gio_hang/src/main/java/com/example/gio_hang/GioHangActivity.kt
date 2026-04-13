package com.example.gio_hang

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

class GioHangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gio_hang)

        findViewById<MaterialButton>(R.id.btnMuaNgay).setOnClickListener {
            startActivity(Intent(this, DonDatHangActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnChinhSuaDiaChi).setOnClickListener {
            startActivity(Intent(this, ChinhSuaDiaChiActivity::class.java))
        }

        val product1Card = findViewById<CardView>(R.id.cardProduct1)
        val product2Card = findViewById<CardView>(R.id.cardProduct2)
        val product3Card = findViewById<CardView>(R.id.cardProduct3)

        findViewById<ImageButton>(R.id.btnDelete1).setOnClickListener {
            showDeleteDialog(product1Card)
        }
        findViewById<ImageButton>(R.id.btnDelete2).setOnClickListener {
            showDeleteDialog(product2Card)
        }
        findViewById<ImageButton>(R.id.btnDelete3).setOnClickListener {
            showDeleteDialog(product3Card)
        }
    }

    private fun showDeleteDialog(cardView: CardView) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_xac_nhan_xoa)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                (resources.displayMetrics.widthPixels * 0.85).toInt(),
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        dialog.findViewById<TextView>(R.id.btnDongY).setOnClickListener {
            cardView.visibility = android.view.View.GONE
            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.btnHuy).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showSuccessToast() {
        val inflater = LayoutInflater.from(this)
        val layout = inflater.inflate(R.layout.toast_cap_nhat_thanh_cong, null)
        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 80)
        toast.show()
    }
}
