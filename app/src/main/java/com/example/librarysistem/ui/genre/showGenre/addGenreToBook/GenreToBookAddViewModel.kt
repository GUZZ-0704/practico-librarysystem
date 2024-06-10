package com.example.librarysistem.ui.genre.showGenre.addGenreToBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Books
import com.example.librarysistem.models.Genre
import com.example.librarysistem.repositories.BookReposity
import com.example.librarysistem.repositories.Book_has_GenreRepository
import com.example.librarysistem.repositories.GenreRepository

class GenreToBookAddViewModel: ViewModel() {
    private val _genre: MutableLiveData<Genre?> by lazy {
        MutableLiveData<Genre?>(null)
    }
    val genre: LiveData<Genre?> get() = _genre

    private val _bookList: MutableLiveData<Books> by lazy {
        MutableLiveData<Books>(null)
    }
    val bookList: LiveData<Books> get() = _bookList

    private val _selectedBooks: MutableLiveData<Books> by lazy {
        MutableLiveData<Books>(arrayListOf())
    }
    val selectedBooks: LiveData<Books> get() = _selectedBooks

    private val _showErrorMesssage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val showErrorMesssage: LiveData<String> get() = _showErrorMesssage

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    fun loadGenre(genreId: Int) {
        GenreRepository.getGenre(genreId,
            success = {
                _genre.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun fetchBookList() {
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

    fun filterBooksNotInGenre(): ArrayList<Book> {
        val currentGenre = _genre.value
        val allBooks = _bookList.value

        if (currentGenre != null && allBooks != null) {
            val filteredBooks = allBooks.filter { book ->
                book.genres.none { genre -> genre.id == currentGenre.id }
            }
            return filteredBooks as ArrayList<Book>
        }
        return arrayListOf()
    }

    fun addToSelectedBooks(book: Book) {
        val currentSelectedBooks = _selectedBooks.value
        if (currentSelectedBooks != null) {
            currentSelectedBooks.add(book)
            _selectedBooks.value = currentSelectedBooks
        }
    }

    fun removeFromSelectedBooks(book: Book) {
        val currentSelectedBooks = _selectedBooks.value
        if (currentSelectedBooks != null) {
            currentSelectedBooks.remove(book)
            _selectedBooks.value = currentSelectedBooks
        }
    }

    fun addGenreToBook(book: Book) {
        val currentGenre = _genre.value
        if (currentGenre != null) {
            Book_has_GenreRepository.addGenreToBook(book.id, currentGenre.id,
                success = {
                    _closeActivity.value = true
                },
                failure = {
                    _showErrorMesssage.value = it.message ?: ""
                })
        }
    }
}