package com.example.librarysistem.ui.genre.insertGenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Genre
import com.example.librarysistem.repositories.GenreRepository

class GenreAddViewModel: ViewModel(){
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    fun addGenre(name: String) {
        val genre = Genre(name)
        GenreRepository.addGenre(genre,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }

}