package br.com.android.marvel.di.module

import br.com.gms.data.data.api.MarvelApiService
import br.com.gms.data.repository.MarvelRemoteRepositoryImpl
import br.com.gms.domain.repository.MarvelRemoteRepository
import dagger.Module
import dagger.Provides

@Module
class RemoteDataModule {

    @Provides
    fun provideMarvelRemoteRepository(api: MarvelApiService): MarvelRemoteRepository {
        return MarvelRemoteRepositoryImpl(api)
    }
}
