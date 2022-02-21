package com.social.jctask.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class  Post (

    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id :Int = 0,

    @ColumnInfo(name = "userId")
    var userId: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "body")
    var body: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean? = false
    ):Parcelable