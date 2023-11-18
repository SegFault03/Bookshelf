package com.example.bookshelf.data

import com.example.bookshelf.models.ResponseBody
import com.example.bookshelf.models.VolumeInfo
import com.example.bookshelf.network.GoogleBooksAPIService
import retrofit2.Retrofit

interface BooksRepository {
    suspend fun getSearchResults(query: String): ResponseBody
    suspend fun getVolumeInfo(volumeId: String): VolumeInfo
}

class NetworkBookRepository(private val googleBooksAPIService: GoogleBooksAPIService): BooksRepository{
    override suspend fun getSearchResults(query: String): ResponseBody {
        return googleBooksAPIService.getSearchResults(query = query)
    }

    override suspend fun getVolumeInfo(volumeId: String): VolumeInfo {
        return googleBooksAPIService.getVolumeInfo(id = volumeId)
    }
}