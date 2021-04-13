package com.example.measureapplication

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MeasureApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        Realm.init(this)
    }
}