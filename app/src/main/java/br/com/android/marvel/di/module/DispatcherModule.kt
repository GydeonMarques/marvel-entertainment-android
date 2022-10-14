package br.com.android.marvel.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier


@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IODispatcher()


@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher()


@Module
class DispatcherModule {


    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}