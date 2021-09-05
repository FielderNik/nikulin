package com.example.nikulin.domain

import com.example.nikulin.data.remote.Api
import com.example.nikulin.domain.entities.MemesEntity
import com.example.nikulin.utils.Either
import com.example.nikulin.domain.entities.Failure

interface IRemoteRepository {
    suspend fun getMemes(memesType: String, numPage: Int): Either<Failure, MutableList<MemesEntity>>
}


class RemoteRepository : IRemoteRepository {
    val api = Api()


    override suspend fun getMemes(memesType: String, numPage: Int): Either<Failure, MutableList<MemesEntity>> {
        return try {
            val memesList = api.memesService.getMemes(memesType, numPage).result

            if (memesList.size == 0) {
                Either.Left(Failure.DataFailure())
            } else {
                Either.Right(memesList)
            }
        } catch (ex: Exception) {
            Either.Left(Failure.RemoteFailure(ex))
        }

    }
}