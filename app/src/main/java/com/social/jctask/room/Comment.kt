package com.social.jctask.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Comment : Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id = 0

    @ColumnInfo(name = "postId")
    var postId: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "email")
    var email: String? = null

    @ColumnInfo(name = "body")
    var body: String? = null

}