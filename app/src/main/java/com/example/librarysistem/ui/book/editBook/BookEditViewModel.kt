package com.example.librarysistem.ui.book.editBook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarysistem.models.Book
import com.example.librarysistem.repositories.BookReposity
import kotlin.math.log

class BookEditViewModel: ViewModel(){
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity
    private val _book: MutableLiveData<Book?> by lazy {
        MutableLiveData<Book?>(null)
    }
    val book: LiveData<Book?> get() = _book

    fun update(name: String, author: String, editorial: String, urlImage: String, synopsis: String, isbn: String,rating: Float, id: Int?) {
        val book = Book(
            name = name,
            author = author,
            editorial = editorial,
            urlImage = urlImage,
            synopsis = synopsis,
            ISBN = isbn,
            rating = rating
        )
        Log.d("BookEditViewModel", "update: $book")
        Log.i("BookEditViewModel", "bookid: ${book.id}")
        if (id != null) {
            book.id = id
            Log.i("BookEditViewModel", "bookid: ${book.id}")
            BookReposity.updateBook(book,
                success = {
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }
    }

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