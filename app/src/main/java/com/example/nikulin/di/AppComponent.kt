package com.example.nikulin.di

import androidx.lifecycle.ViewModel
import com.example.nikulin.domain.RemoteRepository
import com.example.nikulin.ui.fragments.HotMemesFragment
import com.example.nikulin.ui.fragments.LatestMemesFragment
import com.example.nikulin.ui.fragments.TopMemesFragment
import dagger.Component
import dagger.Module

@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(viewModel: ViewModel)
    fun inject(lastMemesFragment: LatestMemesFragment)
    fun inject(hotMemesFragment: HotMemesFragment)
    fun inject(topMemesFragment: TopMemesFragment)


    val remoteRepository: RemoteRepository

}


@Module(includes = [AppBindModule::class])
class AppModule

