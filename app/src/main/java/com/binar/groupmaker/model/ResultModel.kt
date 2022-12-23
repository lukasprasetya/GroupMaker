package com.binar.groupmaker.model

import android.os.Parcelable


@kotlinx.parcelize.Parcelize
data class ResultModel(
    val name_result : String,
    val group_name_result: String,
): Parcelable