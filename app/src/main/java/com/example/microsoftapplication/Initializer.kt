package com.example.microsoftapplication

import android.app.Application

object Initializer {
    fun init(application: Application) {
        initAppCenter(application)
    }

    private fun initAppCenter(application: Application) {
//        AppCenter.start(application, BuildConfig.APP_CENTER_SECRET,
//            Analytics::class.java,
//            Crashes::class.java,
//            Distribute::class.java
//        )
    }
}