package com.example.librarysistem.ui.genre.lstGenres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Genre
import com.example.librarysistem.models.Genres
import com.example.librarysistem.repositories.GenreRepository

class GenreViewModel: ViewModel() {
    private val _genreList: MutableLiveData<Genres> by lazy {
        MutableLiveData<Genres>(Genres())
    }
    val genreList: LiveData<Genres> get() = _genreList


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

    fun deleteBook(genre: Genre) {
        GenreRepository.deleteGenre(genre.id!!,
            success = {
                fetchGenresList()
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun deleteGenre(genre: Genre) {
        GenreRepository.deleteGenre(genre.id!!,
            success = {
                fetchGenresList()
            },
            failure = {
                it.printStackTrace()
            })
    }
}