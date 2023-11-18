package com.example.bookshelf.data

import com.example.bookshelf.models.ImageLinks
import com.example.bookshelf.models.Volume
import com.example.bookshelf.models.VolumeInfo

val testVolumeData = Volume(
        id = "abcd",
        VolumeInfo(
            "TITLE",
            "SUBTITLE",
            listOf("AUTHOR, AUTHOR"),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin id dolor vitae " +
                    "nisi cursus tincidunt. Nullam dapibus euismod laoreet. Nunc vulputate accumsan " +
                    "ex, et tempor ipsum. Curabitur vehicula tincidunt massa, vitae accumsan neque " +
                    "sollicitudin non. Nunc sit amet suscipit dolor. Sed sodales dignissim sapien. " +
                    "Pellentesque ac egestas justo. Praesent lacinia lorem eget neque tempus, hendrerit " +
                    "vulputate turpis rhoncus. Sed consequat, lacus at consectetur hendrerit, neque " +
                    "magna fringilla metus, quis posuere dui odio eu sapien.",
            ImageLinks("")
        )
    )