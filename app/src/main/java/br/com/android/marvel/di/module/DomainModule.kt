package br.com.android.marvel.di.module

import br.com.gms.domain.repository.MarvelLocalRepository
import br.com.gms.domain.repository.MarvelRemoteRepository
import br.com.gms.domain.usecase.MarvelUseCase
import br.com.gms.domain.usecase.MarvelUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideMarvelUseCase(
        localRepository: MarvelLocalRepository,
        remoteRepository: MarvelRemoteRepository
    ): MarvelUseCase {
        return MarvelUseCaseImpl(localRepository, remoteRepository)
    }
}