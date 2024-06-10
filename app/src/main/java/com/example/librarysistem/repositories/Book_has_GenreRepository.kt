package com.example.librarysistem.repositories

import com.example.librarysistem.api.APIBookGenreService
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Book_has_Genre

object Book_has_GenreRepository {

    val instanceRetrofit = RetrofitRepository.instanceRetrofit
    val service = instanceRetrofit.create(APIBookGenreService::class.java)
    fun addGenreToBook(bookId: Int?, genreId: Int?, success: (Book_has_Genre?) -> Unit, failure: (Throwable) -> Unit) {
        val bookGenre = Book_has_Genre(bookId!!, genreId!!)
        service.addBookGenre(bookGenre).enqueue(object : retrofit2.Callback<Book_has_Genre> {
            override fun onResponse(res: retrofit2.Call<Book_has_Genre>, response: retrofit2.Response<Book_has_Genre>) {
                val bookGenre = response.body()
                success(bookGenre)
            }
            override fun onFailure(res: retrofit2.Call<Book_has_Genre>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteBookToGenre(bookId: Int?, genreId: Int?, success: () -> Unit,
                          failure: (Throwable) -> Unit) {
        val bookGenre = Book_has_Genre(bookId!!, genreId!!)
        service.deleteBookGenre(bookGenre).enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(res: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                success()
            }
            override fun onFailure(res: retrofit2.Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

}