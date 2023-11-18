package com.example.bookshelf.models

import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    val title: String? = "No info available",
    val subtitle: String? = "No info available",
    val authors: List<String?> = listOf("No info available"),
    val description: String? = "No info available",
    val imageLinks: ImageLinks? = ImageLinks(),
)
