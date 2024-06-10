package com.example.librarysistem.ui.book.showBook.deleteBookToGenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Genre
import com.example.librarysistem.models.Genres
import com.example.librarysistem.repositories.BookReposity
import com.example.librarysistem.repositories.Book_has_GenreRepository
import com.example.librarysistem.repositories.GenreRepository

class BookToGenreDeleteViewModel: ViewModel() {
    private val _book: MutableLiveData<Book?> by lazy {
        MutableLiveData<Book?>(null)
    }
    val book: LiveData<Book?> get() = _book

    private val _genreList: MutableLiveData<Genres> by lazy {
        MutableLiveData<Genres>(null)
    }
    val genreList: LiveData<Genres> get() = _genreList

    private val _selectedGenres: MutableLiveData<Genres> by lazy {
        MutableLiveData<Genres>(arrayListOf())
    }
    val selectedGenres: LiveData<Genres> get() = _selectedGenres

    private val _showErrorMesssage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val showErrorMesssage: LiveData<String> get() = _showErrorMesssage

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    fun loadBook(bookId : Int){
        BookReposity.getBook(bookId,
            success = {
                _book.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun fetchGenresList() {
        GenreRepository.getGenresList(
            success = { genres ->
                genres?.let {
                    _genreList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun filterGenresInBook(): ArrayList<Genre> {
        val currentBook = _book.value
        val allGenres = _genreList.value

        if (currentBook != null && allGenres != null) {
            val filteredGenres = allGenres.filter { genre ->
                currentBook.genres.any { it.id == genre.id }
            }
            return filteredGenres as ArrayList<Genre>
        } else {
            _showErrorMesssage.value = "El libro no forma parte de ningún género"
            return arrayListOf()
        }
    }

    fun addToSelectedGenres(genre: Genre) {
        val selectedGenres = _selectedGenres.value
        selectedGenres?.add(genre)
        _selectedGenres.value = selectedGenres
    }

    fun removeFromSelectedGenres(genre: Genre) {
        val selectedGenres = _selectedGenres.value
        selectedGenres?.remove(genre)
        _selectedGenres.value = selectedGenres
    }

    fun deleteBookToGenre(genre: Genre) {
        Book_has_GenreRepository.deleteBookToGenre(_book.value?.id!!, genre.id,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }
}