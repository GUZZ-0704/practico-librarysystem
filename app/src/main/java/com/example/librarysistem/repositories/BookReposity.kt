package com.example.librarysistem.repositories

import com.example.librarysistem.api.APIBookService
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Books
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BookReposity {
    val instanceRetrofit = RetrofitRepository.instanceRetrofit
    val service = instanceRetrofit.create(APIBookService::class.java)
    fun getBookList(success: (Books?) -> Unit, failure: (Throwable) -> Unit) {
        service.getBooks().enqueue(object : Callback<Books> {
            override fun onResponse(res: Call<Books>, response: Response<Books>) {
                val bookList = response.body()
                success(bookList)
            }

            override fun onFailure(res: Call<Books>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateBook(book: Book, success: (Book) -> Unit,
                   failure: (Throwable) -> Unit) {
        val id = book.id ?: 0
        service.updateBook(book,id).enqueue(object : Callback<Book> {
            override fun onResponse(res: Call<Book>, response: Response<Book>) {
                val objBook = response.body()
                success(objBook!!)
            }

            override fun onFailure(res: Call<Book>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getBook(id: Int, success: (Book?) -> Unit, failure: (Throwable) -> Unit) {
        service.getBookById(id).enqueue(object : Callback<Book> {
            override fun onResponse(res: Call<Book>, response: Response<Book>) {
                val objBook = response.body()
                success(objBook)
            }

            override fun onFailure(res: Call<Book>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun addBook(book: Book, success: (Book?) -> Unit, failure: (Throwable) -> Unit)  {
        service.addBook(book).enqueue(object : Callback<Book> {
            override fun onResponse(res: Call<Book>, response: Response<Book>) {
                val objBook = response.body()
                success(objBook)
            }

            override fun onFailure(res: Call<Book>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteBook(id: Int,
                   success: () -> Unit,
                   failure: (Throwable) -> Unit) {
        service.deleteBook(id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

}