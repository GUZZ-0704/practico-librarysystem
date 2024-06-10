package com.example.librarysistem.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Books
import com.example.librarysistem.repositories.BookReposity

class MainViewModel: ViewModel() {
    private val _bookList: MutableLiveData<Books> by lazy {
        MutableLiveData<Books>(Books())
    }
    val bookList: LiveData<Books> get() = _bookList


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

    fun deleteBook(book: Book) {
        BookReposity.deleteBook(book.id!!,
            success = {
                fetchBooksList()
            },
            failure = {
                it.printStackTrace()
            })
    }

}