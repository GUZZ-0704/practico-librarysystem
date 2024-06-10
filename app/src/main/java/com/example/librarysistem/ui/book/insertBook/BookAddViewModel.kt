package com.example.librarysistem.ui.book.insertBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Book
import com.example.librarysistem.repositories.BookReposity

class BookAddViewModel: ViewModel() {
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    fun addBook(name: String, author: String, editorial: String, imgUrl: String, synopsis: String, isbn: String, rating: Float) {
        val book = Book(name, author, editorial, imgUrl, synopsis, isbn, rating)
        BookReposity.addBook(book,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }
}