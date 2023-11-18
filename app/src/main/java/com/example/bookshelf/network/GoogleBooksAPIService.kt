package com.example.bookshelf.network

import com.example.bookshelf.models.ResponseBody
import com.example.bookshelf.models.VolumeInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksAPIService{
    @GET("volumes")
    suspend fun getSearchResults(
        @Query("q") query:String
    ): ResponseBody

    @GET("volumes/{id}")
    suspend fun getVolumeInfo(
        @Path("id") id: String
    ): VolumeInfo
}
