package com.social.jctask.koin

import com.social.jctask.networkLayer.ApiInterface
import com.social.jctask.room.CommentsDao
import com.social.jctask.room.PostDao
import com.social.jctask.room.RoomClient
import com.social.jctask.viewmodel.CommentsViewModel
import com.social.jctask.viewmodel.PostViewModel
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModules = listOf( module {
    single { provideCommentViewModel() }
    single { providePostViewModel() }
})

fun provideCommentViewModel(): CommentsViewModel {
    return CommentsViewModel(provideApiInterface(provideRetrofit()),provideCommentsDao())
}

fun providePostViewModel(): PostViewModel {
    return PostViewModel(provideApiInterface(provideRetrofit()), providePostDao())
}

fun provideRetrofit():Retrofit{
    val client = OkHttpClient
        .Builder()
        .build()

    return Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

}

fun provideApiInterface(retrofit: Retrofit): ApiInterface {
    return retrofit.create(ApiInterface::class.java)
}

fun providePostDao(): PostDao? {
    return RoomClient.appDatabase.postDao()
}
fun provideCommentsDao(): CommentsDao? {
    return RoomClient.appDatabase.commentsDao()
}
