package com.example.librarysistem.models

import com.google.gson.annotations.SerializedName

data class Book_has_Genre(
    @SerializedName("libro_id")var book_id: Int,
    @SerializedName("genero_id")var genre_id: Int
) {
}