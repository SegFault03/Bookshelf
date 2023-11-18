package com.example.bookshelf.ui

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.models.Volume
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import java.io.IOException

private const val TAG = "BookshelfViewModel"

sealed interface BookshelfUiState{
    object Idle: BookshelfUiState
    object Failure: BookshelfUiState
    data class Success(
        val searchResults: List<Volume>
    ): BookshelfUiState
    object Loading: BookshelfUiState
    data class Error(
        val errorInfo: String
    ): BookshelfUiState
}
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class BookshelfViewModel(private val booksRepository: BooksRepository): ViewModel() {

    companion object{
        val bookshelfViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val booksRepository = application.applicationContainer.BooksRepository
                BookshelfViewModel(booksRepository)
            }
        }
    }
    //TODO Create MutableStateFlow data holder class later
    var bookshelfUiState: BookshelfUiState by mutableStateOf(
        BookshelfUiState.Idle
    )
        private set

    var currentSearchQuery: String by mutableStateOf(
        ""
    )
        private set

    fun updateSearchQuery(newQuery: String){
        currentSearchQuery = newQuery
    }

    fun initiateSearch(){
        bookshelfUiState = BookshelfUiState.Loading
        loadSearchResults()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadSearchResults(){
        viewModelScope.launch {
            bookshelfUiState = try{
                val response = booksRepository.getSearchResults(query = currentSearchQuery)
                if(response.listOfVolumes.isNullOrEmpty()) {
                    currentSearchQuery = ""
                    BookshelfUiState.Failure
                }
                else {
                    BookshelfUiState.Success(response.listOfVolumes)
                }
            }
            catch (e: IOException){
                Log.e(TAG, "$e")
                BookshelfUiState.Error("IOException: $e")
            }
            catch (e: HttpException){
                Log.e(TAG, "$e")
                BookshelfUiState.Error("HTTPException: $e")
            }
            catch (e: SerializationException){
                Log.e(TAG, "$e")
                BookshelfUiState.Error("SerializationException: $e")
            }
        }
    }
}