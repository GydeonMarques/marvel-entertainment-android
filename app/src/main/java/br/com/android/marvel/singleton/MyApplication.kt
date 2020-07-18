package br.com.android.marvel.singleton

import android.app.Application
import com.orhanobut.hawk.Hawk

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }
}