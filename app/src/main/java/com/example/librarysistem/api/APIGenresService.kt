package com.example.librarysistem.api

import com.example.librarysistem.models.Genre
import com.example.librarysistem.models.Genres
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIGenresService {

    @GET("generos")
    fun getGenres(): Call<Genres>

    @GET("generos/{id}")
    fun getGenreById(
        @Path("id") id: Int
    ): Call<Genre>

    @PUT("generos/{id}")
    fun updateGenre(
        @Body genre: Genre,
        @Path("id") id: Int
    ): Call<Genre>

    @POST("generos")
    fun addGenre(
        @Body genre: Genre
    ): Call<Genre>

    @DELETE("generos/{id}")
    fun deleteGenre(
        @Path("id") id: Int
    ): Call<Void>

}