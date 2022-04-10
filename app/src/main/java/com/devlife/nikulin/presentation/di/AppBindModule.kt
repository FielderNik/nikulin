package com.devlife.nikulin.presentation.di

import com.devlife.nikulin.data.repository.IRemoteRepository
import com.devlife.nikulin.data.repository.RemoteRepository
import dagger.Binds
import dagger.Module

@Module
interface AppBindModule {

    @Binds
    fun bindIRemoteRepositoryToRemoteRepository(remoteRepository: RemoteRepository): IRemoteRepository
}