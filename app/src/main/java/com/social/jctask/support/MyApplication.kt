package com.social.jctask.support

import android.app.Application
import com.social.jctask.koin.appModules
import org.koin.android.ext.android.startKoin

class MyApplication: Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        startKoin (this@MyApplication, appModules )
    }

    companion object {
        private var instance: Application? = null

        @JvmStatic
        fun applicationContext() : MyApplication {
            return instance as MyApplication
        }

    }
}