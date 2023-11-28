package com.example.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookshelfRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success(val covers: List<String>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookshelfViewModel(private val bookshelfRepository: BookshelfRepository) : ViewModel(){
    /** The mutable State that stores the status of the most recent request */
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getBookCover()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getBookCover() {
        viewModelScope.launch {
            bookshelfUiState = try {
                val bookResult = bookshelfRepository.getBooks("music+history")

                val bookCover = mutableListOf<String>()
                bookResult.items.forEach {
                    val coverResponse = bookshelfRepository.getSpecificBook(it.id)
                    bookCover.add(coverResponse.volumeInfo.imageLinks.thumbnail.replace("http","https"))
                }

                BookshelfUiState.Success(bookCover)
            } catch (e: IOException) {
                BookshelfUiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}
