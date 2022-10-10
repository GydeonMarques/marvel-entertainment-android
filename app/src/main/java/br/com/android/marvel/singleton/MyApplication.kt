package br.com.android.marvel.singleton

import android.app.Application
import br.com.android.marvel.di.component.DaggerAppComponent
import br.com.android.marvel.entertainment.BuildConfig
import timber.log.Timber

open class MyApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}