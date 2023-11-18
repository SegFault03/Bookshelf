package com.example.bookshelf.data

import com.example.bookshelf.network.GoogleBooksAPIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ApplicationContainer{
    val BooksRepository: BooksRepository
}

class DefaultAppContainer: ApplicationContainer{
    private val baseUrl = "https://www.googleapis.com/books/v1/"
    private val json: Json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    private val googleBooksAPIService by lazy {
        retrofit.create(GoogleBooksAPIService::class.java)
    }
    override val BooksRepository: BooksRepository by lazy {
        NetworkBookRepository(googleBooksAPIService)
    }
}