package com.devlife.nikulin.presentation.di

import androidx.lifecycle.ViewModel
import com.devlife.nikulin.data.repository.RemoteRepository
import com.devlife.nikulin.domain.usecase.GetMemesUseCase
import com.devlife.nikulin.presentation.fragments.HotMemesFragment
import com.devlife.nikulin.presentation.fragments.LatestMemesFragment
import com.devlife.nikulin.presentation.fragments.TopMemesFragment
import dagger.Component
import dagger.Module

@Component(modules = [AppModule::class, ViewModelModule::class, UseCaseModule::class])
interface AppComponent {

    fun inject(viewModel: ViewModel)
    fun inject(lastMemesFragment: LatestMemesFragment)
    fun inject(hotMemesFragment: HotMemesFragment)
    fun inject(topMemesFragment: TopMemesFragment)

    val remoteRepository: RemoteRepository
    val getMemesUseCase: GetMemesUseCase

}


@Module(includes = [AppBindModule::class])
class AppModule

