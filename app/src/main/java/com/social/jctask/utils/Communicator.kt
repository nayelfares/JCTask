package com.social.jctask.utils

import android.os.AsyncTask

interface Communicator {
    fun passDataCom(editTextInput : String)
}

const val POST_ID = "POST_ID"
const val FAVORITE = "FAVORITE"
const val FAVORITE_RESULT = "FAVORITE_RESULT"

class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}