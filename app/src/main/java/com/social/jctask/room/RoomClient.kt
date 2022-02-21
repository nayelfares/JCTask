package com.social.jctask.room

import androidx.room.Room
import com.social.jctask.support.MyApplication

object RoomClient {
    val appDatabase: RoomDatabase =
        Room.databaseBuilder(MyApplication.applicationContext(), RoomDatabase::class.java, "jctask").build()

}