package com.example.bookshelf.data

import com.example.bookshelf.network.BookInfoResponse
import com.example.bookshelf.network.BooksResponse
import com.example.bookshelf.network.BookshelfApiService

interface BookshelfRepository {
    suspend fun getBooks(query:String): BooksResponse
    suspend fun getSpecificBook(id:String): BookInfoResponse
}

class NetworkBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBooks(query:String): BooksResponse = bookshelfApiService.getBooks(query = query)

    override suspend fun getSpecificBook(id:String): BookInfoResponse = bookshelfApiService.getSpecificBook(id = id)
}