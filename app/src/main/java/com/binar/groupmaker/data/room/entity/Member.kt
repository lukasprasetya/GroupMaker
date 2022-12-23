package com.binar.groupmaker.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binar.groupmaker.constant.CommonConstant
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = CommonConstant.DATABASE_TABLE)
data class Member(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = CommonConstant.KEY_ROWID)
    var id: Int? = null,

    @ColumnInfo(name = CommonConstant.KEY_NAME_MEMBER)
    var nameMember: String,

    @ColumnInfo(name = CommonConstant.KEY_ID_GROUP)
    var idGroup: String
) : Parcelable
