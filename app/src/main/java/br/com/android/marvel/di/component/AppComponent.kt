package br.com.android.marvel.di.component

import android.content.Context
import br.com.android.marvel.di.module.*
import br.com.android.marvel.ui.pages.main.MainActivity
import br.com.android.marvel.ui.pages.movies.details.MovieDetailsFragment
import br.com.android.marvel.ui.pages.movies.main.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DomainModule::class,
        StorageModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        LocalDataModule::class,
        RemoteDataModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: MoviesFragment)
    fun inject(fragment: MovieDetailsFragment)
}