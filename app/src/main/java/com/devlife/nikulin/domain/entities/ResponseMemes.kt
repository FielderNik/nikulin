package com.devlife.nikulin.domain.entities


import com.google.gson.annotations.SerializedName

data class ResponseMemes(
    @SerializedName("result")
    val result: MutableList<MemesEntity>,
    @SerializedName("totalCount")
    val totalCount: Int
)