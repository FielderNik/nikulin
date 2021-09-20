package com.example.nikulin.domain.usecase

import com.example.nikulin.domain.RemoteRepository
import com.example.nikulin.domain.entities.Failure
import com.example.nikulin.domain.entities.MemesEntity
import com.example.nikulin.utils.Either
import javax.inject.Inject

class GetMemesUseCase @Inject constructor(val remoteRepository: RemoteRepository) {

    suspend fun run(memesType: String, numPage: Int): Either<Failure, MutableList<MemesEntity>> {
        return remoteRepository.getMemes(memesType, numPage)
    }
}