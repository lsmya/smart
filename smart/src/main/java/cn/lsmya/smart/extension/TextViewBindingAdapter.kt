package cn.lsmya.smart.extension

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewBindingAdapter {
    @BindingAdapter("colorString")
    @JvmStatic
    fun setColor(view: TextView, color: String?) {
        color?.let {
            view.setTextColor(Color.parseColor(it))
        }
    }

}