package com.binar.groupmaker.ui.slider.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SliderData(
    val title: String,
    val desc: String,
    val imgSlider: Int
): Parcelable
