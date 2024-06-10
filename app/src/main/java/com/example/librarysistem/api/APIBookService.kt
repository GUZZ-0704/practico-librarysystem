package com.example.librarysistem.api

import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Books
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIBookService {

    @GET("libros")
    fun getBooks(): Call<Books>

    @GET("libros/{id}")
    fun getBookById(
        @Path("id") id: Int
    ): Call<Book>

    @PUT("libros/{id}")
    fun updateBook(
        @Body book: Book,
        @Path("id") id: Int
    ): Call<Book>

    @POST("libros")
    fun addBook(
        @Body book: Book
    ): Call<Book>

    @DELETE("libros/{id}")
    fun deleteBook(
        @Path("id") id: Int
    ): Call<Void>
}