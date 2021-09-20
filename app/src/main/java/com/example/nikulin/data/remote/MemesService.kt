package com.example.nikulin.data.remote

import com.example.nikulin.domain.entities.ResponseMemes
import retrofit2.http.GET
import retrofit2.http.Path


interface MemesService {

    @GET("{memesType}/0?json=true")
    suspend fun getLatestMemes(@Path("memesType") memesType: String): ResponseMemes

    @GET("{memesType}/{numPage}?json=true")
    suspend fun getNewLatestMemes(
        @Path("memesType") memesType: String,
        @Path("numPage") numPage: Int
    )
            : ResponseMemes


    @GET("{memesType}/{numPage}?json=true")
    suspend fun getMemes(
        @Path("memesType") memesType: String,
        @Path("numPage") numPage: Int
    )
            : ResponseMemes
}