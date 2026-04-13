package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

import android.content.res.ColorStateList
import android.graphics.Color
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.google.android.material.button.MaterialButton

fun View.setBounceClickEffect(action: (View) -> Unit) {
    this.setOnClickListener { view ->
        var originalTint: ColorStateList? = null
        var originalStrokeWidth = 0
        var originalStrokeColor: ColorStateList? = null
        var originalTextColor: ColorStateList? = null
        var colorToUse = Color.parseColor("#1A1A1A")

        if (view is MaterialButton) {
            originalTint = view.backgroundTintList
            originalStrokeWidth = view.strokeWidth
            originalStrokeColor = view.strokeColor
            originalTextColor = view.textColors

            colorToUse = originalTint?.defaultColor ?: colorToUse

            view.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            view.strokeWidth = 2.dpToPx(view.context)
            view.strokeColor = ColorStateList.valueOf(colorToUse)
            view.setTextColor(colorToUse)
        }

        val anim = ScaleAnimation(
            1f, 0.95f,
            1f, 0.95f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.duration = 25
        anim.repeatCount = 1
        anim.repeatMode = Animation.REVERSE

        view.startAnimation(anim)

        view.postDelayed({
            if (view is MaterialButton) {
                view.backgroundTintList = originalTint
                view.strokeWidth = originalStrokeWidth
                view.strokeColor = originalStrokeColor
                view.setTextColor(originalTextColor)
            }
            action(view)
        }, 50)
    }
}

fun Int.dpToPx(context: android.content.Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun android.app.Activity.showSuccessToast(message: String) {
    val dialog = android.app.Dialog(this)
    val view = layoutInflater.inflate(R.layout.thong_bao, null)
    view.findViewById<android.widget.TextView>(R.id.toastMessage).text = message

    dialog.setContentView(view)
    dialog.window?.let {
        it.setBackgroundDrawableResource(android.R.color.transparent)
        it.setDimAmount(0f)
        it.setGravity(android.view.Gravity.BOTTOM)
        val lp = it.attributes
        lp.y = 100
        it.attributes = lp
    }

    dialog.show()

    view.postDelayed({
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }, 2000)
}
