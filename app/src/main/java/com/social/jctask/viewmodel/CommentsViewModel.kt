package com.social.jctask.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.jctask.networkLayer.ApiInterface
import com.social.jctask.room.Comment
import com.social.jctask.room.CommentsDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CommentsViewModel(private val apiService: ApiInterface,private val commentsDao: CommentsDao?): ViewModel() {
    var id : String = ""

    fun getAllCommentsAsLiveData(): LiveData<List<Comment>>{
        commentsDao?.apply {
            return allComment
        }
        return MutableLiveData()
    }

    fun deleteAll(){
        commentsDao?.deleteAll()
        getAllCommentsAsLiveData()
    }

    @SuppressLint("CheckResult")
    fun callCommentsApi(id :String){

        apiService.fetchComments(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { response ->
                onHandleResponse(response)
            }
    }

    private fun onHandleResponse(response: List<Comment>){
        if(response.isNotEmpty()){
            response.forEach { comment ->
                viewModelScope.launch {
                    commentsDao?.insert(comment)
                }
            }
        }
    }

}