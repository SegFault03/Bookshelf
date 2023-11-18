package com.example.bookshelf.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    @SerialName(value = "id")
    val id: String? = "No info available",
    val volumeInfo: VolumeInfo? = VolumeInfo()
)