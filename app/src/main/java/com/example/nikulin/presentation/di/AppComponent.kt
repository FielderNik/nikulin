package com.example.nikulin.presentation.di

import androidx.lifecycle.ViewModel
import com.example.nikulin.domain.RemoteRepository
import com.example.nikulin.domain.usecase.GetMemesUseCase
import com.example.nikulin.presentation.fragments.HotMemesFragment
import com.example.nikulin.presentation.fragments.LatestMemesFragment
import com.example.nikulin.presentation.fragments.TopMemesFragment
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

