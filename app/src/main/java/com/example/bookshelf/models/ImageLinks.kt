package com.example.bookshelf.models

import kotlinx.serialization.Serializable

@Serializable
data class ImageLinks(
    val thumbnail: String? = ""
)
