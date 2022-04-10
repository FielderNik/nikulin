package com.devlife.nikulin.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devlife.nikulin.presentation.viewmodels.*
import com.devlife.nikulin.presentation.viewmodels.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LastMemesViewModel::class)
    internal abstract fun lastMemesViewModel(viewModel: LastMemesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HotMemesViewModel::class)
    internal abstract fun hotMemesViewModel(viewModel: HotMemesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BestMemesViewModel::class)
    internal abstract fun bestMemesViewModel(viewModel: BestMemesViewModel): ViewModel

}