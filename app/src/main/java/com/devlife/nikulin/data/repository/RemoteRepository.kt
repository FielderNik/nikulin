package com.devlife.nikulin.data.repository


import com.devlife.nikulin.data.remote.Api
import com.devlife.nikulin.domain.entities.Failure
import com.devlife.nikulin.domain.entities.MemesEntity
import com.devlife.nikulin.utils.Either
import javax.inject.Inject

interface IRemoteRepository {
    suspend fun getMemes(memesType: String, numPage: Int): Either<Failure, MutableList<MemesEntity>>
}


class RemoteRepository @Inject constructor(private val api: Api) : IRemoteRepository {

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