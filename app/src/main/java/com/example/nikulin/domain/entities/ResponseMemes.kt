package com.example.nikulin.domain.entities


import com.example.nikulin.domain.entities.MemesEntity
import com.google.gson.annotations.SerializedName

data class ResponseMemes(
    @SerializedName("result")
    val result: MutableList<MemesEntity>,
    @SerializedName("totalCount")
    val totalCount: Int
)