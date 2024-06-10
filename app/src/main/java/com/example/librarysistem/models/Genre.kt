package com.example.librarysistem.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

typealias Genres = ArrayList<Genre>

data class Genre (
    @SerializedName("nombre")var name : String
): Serializable{
    var id : Int? = null
    @SerializedName("created_at")var createdAt: String? = null
    @SerializedName("updated_at")var updatedAt: String? = null
    var isSelected: Boolean = false

    override fun toString(): String {
        return name
    }
}
