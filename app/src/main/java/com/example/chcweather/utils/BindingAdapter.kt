package com.example.chcweather.utils

import android.graphics.drawable.Icon
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.example.chcweather.R

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("setIcon")
fun setIcon(imageView: ImageView, condition: String?) {
    condition?.let {
        val context = imageView.context
        val icon = Icon.createWithResource(context, R.drawable.weather)
        imageView.setImageIcon(icon)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
    }
}

@BindingAdapter("setTemperature")
fun setTemperature(view: TextView, double: Double) {
    val context = view.context
    view.text = double.toString().plus(
        context.resources.getString(R.string.temp_symbol_celsius)
    )
}