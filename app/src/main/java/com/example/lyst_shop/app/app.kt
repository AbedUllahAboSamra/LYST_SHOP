package com.example.lyst_shop.app

import android.app.Application
import com.google.firebase.FirebaseApp

class app() : Application() {
    override fun onCreate() {
        FirebaseApp.initializeApp(applicationContext)
        super.onCreate()
    }
}