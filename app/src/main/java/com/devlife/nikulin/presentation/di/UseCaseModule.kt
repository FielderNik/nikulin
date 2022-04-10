package com.devlife.nikulin.presentation.di

import com.devlife.nikulin.data.repository.RemoteRepository
import com.devlife.nikulin.domain.usecase.GetMemesUseCase
import dagger.Module
import dagger.Provides


@Module
object UseCaseModule {

    @Provides
    fun provideGetMemesUseCase(remoteRepository: RemoteRepository): GetMemesUseCase {
        return GetMemesUseCase(remoteRepository)
    }

}