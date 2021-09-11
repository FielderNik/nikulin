package com.example.nikulin.di

import com.example.nikulin.domain.IRemoteRepository
import com.example.nikulin.domain.RemoteRepository
import dagger.Binds
import dagger.Module

@Module
interface AppBindModule {

    @Binds
    fun bindIRemoteRepositoryToRemoteRepository(remoteRepository: RemoteRepository): IRemoteRepository
}