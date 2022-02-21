package com.social.jctask.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.jctask.networkLayer.ApiInterface
import com.social.jctask.room.Post
import com.social.jctask.room.PostDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*


class PostViewModel(private val apiInterface: ApiInterface,private val postDao: PostDao?): ViewModel() {
    var id :String = ""

    fun getAllPostAsLiveData(): LiveData<List<Post>>{
        postDao?.apply {
            return all
        }
        return MutableLiveData()
    }

    fun getFavouriteAsLiveData(): LiveData<List<Post>>{
        postDao?.apply {
            return favouritePosts
        }
        return MutableLiveData()
    }

    @SuppressLint("CheckResult")
    fun callPostsApi(){

        apiInterface.fetchPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { response ->
                onHandleResponse(response)
            }
    }

    private fun onHandleResponse(response: List<Post>){
        if(response.isNotEmpty()){
            response.forEach { post ->
                viewModelScope.launch {
                    postDao?.insert(post)
                }
            }
        }
    }

    fun addOrRemoveFavourite(post: Post){
        viewModelScope.launch {
            post.isFavorite = !post.isFavorite!!
            postDao?.update(post)
        }
    }
}