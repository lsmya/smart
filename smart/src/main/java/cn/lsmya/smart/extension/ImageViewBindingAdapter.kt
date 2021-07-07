package cn.lsmya.smart.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBindingAdapter {
    @BindingAdapter(value = ["imageUrl","placeholder","error"],requireAll = false)
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?, placeholder: Drawable?, error: Drawable?) {
        val into = Glide.with(view.context).load(imageUrl)
        placeholder?.let {
            into.placeholder(it)
        }
        error?.let {
            into.error(it)
        }
        into.into(view)
    }

}