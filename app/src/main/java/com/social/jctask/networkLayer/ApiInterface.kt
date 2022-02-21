package com.social.jctask.networkLayer

import com.social.jctask.room.Comment
import com.social.jctask.room.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/posts")
    fun fetchPosts() : Observable<List<Post>>

    @GET("/posts/{id}/comments")
    fun fetchComments(@Path(value = "id", encoded = false) id:String) : Observable<List<Comment>>
}