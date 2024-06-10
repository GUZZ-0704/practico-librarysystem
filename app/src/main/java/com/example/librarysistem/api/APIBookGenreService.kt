package com.example.librarysistem.api

import com.example.librarysistem.models.Book_has_Genre
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import retrofit2.http.POST

interface APIBookGenreService {

    @POST("libro-generos")
    fun addBookGenre(
        @Body bookGenre: Book_has_Genre
    ): Call<Book_has_Genre>

    @HTTP(method = "DELETE", path = "libro-generos", hasBody = true)
    fun deleteBookGenre(
        @Body bookGenre: Book_has_Genre
    ): Call<Void>

}