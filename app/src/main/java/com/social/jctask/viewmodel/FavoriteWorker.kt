package com.social.jctask.viewmodel

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.social.jctask.utils.FAVORITE
import com.social.jctask.utils.FAVORITE_RESULT
import com.social.jctask.utils.POST_ID

class FavoriteWorker(context: Context, userParameters: WorkerParameters) :
    Worker(context, userParameters) {
    override fun doWork(): Result {
        // 2
        val postId = inputData.getInt(POST_ID,0)
        val favorite = inputData.getBoolean(FAVORITE,true)
        // 3
        return try {
            val outputData = Data.Builder()
                .putString(FAVORITE_RESULT, if(favorite) "WorkManager added post to favorite list" else "WorkManager removed post to favorite list")
                .build()
            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}