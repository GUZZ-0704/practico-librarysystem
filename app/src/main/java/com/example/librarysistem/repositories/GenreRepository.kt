package com.example.librarysistem.repositories

import android.util.Log
import com.example.librarysistem.api.APIGenresService
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Genre
import com.example.librarysistem.models.Genres
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GenreRepository {
    val instanceRetrofit = RetrofitRepository.instanceRetrofit
    val service = instanceRetrofit.create(APIGenresService::class.java)
    fun getGenresList(success: (Genres?) -> Unit, failure: (Throwable) -> Unit) {
        service.getGenres().enqueue(object : Callback<Genres> {
            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    Log.e("GenreRepository", "Failed to load genres: ${response.errorBody()?.string()}")
                    failure(Exception("Failed to load genres"))
                }
            }

            override fun onFailure(call: Call<Genres>, t: Throwable) {
                Log.e("GenreRepository", "Failed to load genres", t)
                failure(t)
            }
        })
    }

    fun deleteGenre(id: Int,
                    success: () -> Unit,
                    failure: (Throwable) -> Unit) {
        service.deleteGenre(id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }
            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })

    }

    fun getGenre(id: Int, success: (Genre?) -> Unit, failure: (Throwable) -> Unit) {
        service.getGenreById(id).enqueue(object : Callback<Genre> {
            override fun onResponse(res: Call<Genre>, response: Response<Genre>) {
                val objGenre = response.body()
                success(objGenre)
            }

            override fun onFailure(res: Call<Genre>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateGenre(genre: Genre, success: (Genre) -> Unit,
                    failure: (Throwable) -> Unit){
        val id = genre.id ?: 0
        service.updateGenre(genre,id).enqueue(object : Callback<Genre> {
            override fun onResponse(res: Call<Genre>, response: Response<Genre>) {
                val objGenre = response.body()
                success(objGenre!!)
            }

            override fun onFailure(res: Call<Genre>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun addGenre(genre: Genre, success: (Genre?) -> Unit, failure: (Throwable) -> Unit) {
        service.addGenre(genre).enqueue(object : Callback<Genre> {
            override fun onResponse(res: Call<Genre>, response: Response<Genre>) {
                val objGenre = response.body()
                success(objGenre)
            }

            override fun onFailure(res: Call<Genre>, t: Throwable) {
                failure(t)
            }
        })
    }

}