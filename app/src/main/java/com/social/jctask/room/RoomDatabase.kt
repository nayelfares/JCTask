package com.social.jctask.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.social.jctask.room.Comment
import com.social.jctask.room.CommentsDao
import com.social.jctask.room.Post
import com.social.jctask.room.PostDao


@Database(entities = [Post::class, Comment::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao?
    abstract  fun commentsDao(): CommentsDao?
}