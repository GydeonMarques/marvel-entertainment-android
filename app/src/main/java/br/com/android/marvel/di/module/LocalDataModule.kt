package br.com.android.marvel.di.module

import android.content.Context
import br.com.gms.data.data.db.MoviesDatabase
import br.com.gms.data.repository.MarvelLocalRepositoryImpl
import br.com.gms.domain.repository.MarvelLocalRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(context: Context): MoviesDatabase {
        return MoviesDatabase.getInstanceDatabase(context)
    }

    @Provides
    fun provideMarvelLocalRepository(database: MoviesDatabase): MarvelLocalRepository {
        return MarvelLocalRepositoryImpl(database)
    }
}