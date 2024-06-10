package com.example.librarysistem.ui.genre.showGenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Books
import com.example.librarysistem.models.Genre
import com.example.librarysistem.repositories.BookReposity
import com.example.librarysistem.repositories.GenreRepository

class GenreDetailViewModel: ViewModel() {
    private val _genre: MutableLiveData<Genre?> by lazy {
        MutableLiveData<Genre?>(null)
    }
    val genre: LiveData<Genre?> get() = _genre

    private val _bookList: MutableLiveData<Books> by lazy {
        MutableLiveData<Books>(Books())
    }
    val bookList: LiveData<Books> get() = _bookList

    fun loadGenre(genreId: Int) {
        GenreRepository.getGenre(genreId,
            success = {
                _genre.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun fetchBooksList() {
        BookReposity.getBookList(
            success = { books ->
                books?.let {
                    _bookList.value = Books(it.sortedByDescending { it -> it.rating })
                }
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun filterBookListByGenre(): ArrayList<Book> {
        val currentGenre = _genre.value
        val allBooks = _bookList.value

        if (currentGenre != null && allBooks != null) {
            val filteredBooks = allBooks.filter { book ->
                book.genres.any { genre -> genre.id == currentGenre.id }
            }
            return filteredBooks as ArrayList<Book>
        }
        return arrayListOf()
    }

}