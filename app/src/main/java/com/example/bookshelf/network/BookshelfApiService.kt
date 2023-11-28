package com.example.bookshelf.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {

    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): BooksResponse

    @GET("volumes/{id}")
    suspend fun getSpecificBook(@Path("id") id: String): BookInfoResponse
}