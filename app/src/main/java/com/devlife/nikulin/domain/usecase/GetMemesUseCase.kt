package com.devlife.nikulin.domain.usecase


import com.devlife.nikulin.data.repository.RemoteRepository
import com.devlife.nikulin.domain.entities.Failure
import com.devlife.nikulin.domain.entities.MemesEntity
import com.devlife.nikulin.utils.Either
import javax.inject.Inject

class GetMemesUseCase @Inject constructor(private val remoteRepository: RemoteRepository) {

    suspend fun run(memesType: String, numPage: Int): Either<Failure, MutableList<MemesEntity>> {
        return remoteRepository.getMemes(memesType, numPage)
    }
}