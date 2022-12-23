package com.binar.groupmaker.model

import android.os.Parcelable


@kotlinx.parcelize.Parcelize
data class SliderData(
    val title: String,
    val desc: String,
    val imgSlider: Int
): Parcelable