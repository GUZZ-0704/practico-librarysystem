package com.example.librarysistem.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

typealias Books = ArrayList<Book>
data class Book(
    @SerializedName("nombre") var name: String,
    @SerializedName("autor")var author : String,
    @SerializedName("editorial")var editorial : String,
    @SerializedName("imagen")var urlImage : String,
    @SerializedName("isbn")var ISBN : String,
    @SerializedName("sinopsis")var synopsis : String
): Serializable{
    var id : Int? = null
    @SerializedName("calificacion")var rating: Float? = null
    var createdAt: String? = null
    var updatedAt: String? = null
    @SerializedName("generos")var genres: ArrayList<Genre> = ArrayList()
    var isSelected: Boolean = false

    constructor(name: String, author: String, editorial: String, urlImage: String, synopsis: String, ISBN: String, rating: Float) : this(name, author, editorial, urlImage, ISBN, synopsis) {
        this.rating = rating
    }

}
