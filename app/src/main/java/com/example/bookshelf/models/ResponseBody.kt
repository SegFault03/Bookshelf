package com.example.bookshelf.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBody(
    @SerialName(value = "items")
    val listOfVolumes: (List<Volume>)? = null
)
