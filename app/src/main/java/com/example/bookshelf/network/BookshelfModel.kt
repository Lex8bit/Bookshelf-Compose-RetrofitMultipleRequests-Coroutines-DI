package com.example.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<BookItem>
)
@Serializable
data class BookItem(
    val kind: String,
    val id: String
)

@Serializable
data class BookInfoResponse(
    val id: String,
    val volumeInfo: SpecificInfo
)
@Serializable
data class SpecificInfo(
    val imageLinks: BookCover
)
@Serializable
data class BookCover(
    val thumbnail: String
)