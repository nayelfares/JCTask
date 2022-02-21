package com.social.jctask.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CommentsDao {
    @get:Query("SELECT * FROM comment")
    val allComment: LiveData<List<Comment>>


    @Insert
    suspend fun insert(comment: Comment?)

    @Delete
    fun delete(comment: Comment?)

    @Query("DELETE FROM comment")
    fun deleteAll()

    @Update
    suspend fun update(comment: Comment?)
}