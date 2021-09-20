package com.example.nikulin.presentation.di

import com.example.nikulin.domain.RemoteRepository
import com.example.nikulin.domain.usecase.GetMemesUseCase
import dagger.Module
import dagger.Provides


@Module
object UseCaseModule {

    @Provides
    fun provideGetMemesUseCase(remoteRepository: RemoteRepository): GetMemesUseCase {
        return GetMemesUseCase(remoteRepository)
    }

}