package com.example.librarysistem.ui.book.showBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Book
import com.example.librarysistem.repositories.BookReposity

class BookDetailViewModel: ViewModel(){
    private val _book: MutableLiveData<Book?> by lazy {
        MutableLiveData<Book?>(null)
    }
    val book: LiveData<Book?> get() = _book

    fun loadBook(bookId : Int){
        BookReposity.getBook(bookId,
            success = {
                _book.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }


}