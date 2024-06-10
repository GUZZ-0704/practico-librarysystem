package com.example.librarysistem.ui.genre.editGenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Genre
import com.example.librarysistem.repositories.GenreRepository

class GenreEditViewModel: ViewModel() {
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity
    private val _genre: MutableLiveData<Genre?> by lazy {
        MutableLiveData<Genre?>(null)
    }
    val genre: LiveData<Genre?> get() = _genre

    fun update(name: String, id: Int?) {
        val genre = Genre(
            name = name
        )
        if (id != null) {
            genre.id = id
            GenreRepository.updateGenre(genre,
                success = {
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }
    }

    fun loadGenre(genreId: Int) {
        GenreRepository.getGenre(genreId,
            success = {
                _genre.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }
}