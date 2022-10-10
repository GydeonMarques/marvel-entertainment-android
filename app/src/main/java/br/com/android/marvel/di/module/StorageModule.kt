package br.com.android.marvel.di.module

import android.content.Context
import br.com.gms.storage.repository.LocalStorage
import br.com.gms.storage.sharedPrefs.LocalSharedPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): LocalStorage {
        return LocalSharedPreference(context)
    }
}