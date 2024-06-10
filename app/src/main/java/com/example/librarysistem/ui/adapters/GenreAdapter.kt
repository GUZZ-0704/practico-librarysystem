package com.example.librarysistem.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ItemBookLayoutBinding
import com.example.librarysistem.databinding.ItemGenreLayoutBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Genre
import com.example.librarysistem.models.Genres

class GenreAdapter(
    val genresList: ArrayList<Genre>,
    val listener : OnGenreClickListener,
    val showButtons: Boolean = true
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    var selectedGenres = mutableListOf<Genre>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreAdapter.GenreViewHolder {
        val binding = ItemGenreLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenreViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GenreAdapter.GenreViewHolder, position: Int) {
        val genre = genresList[position]
        holder.bind(genre, listener)
        if (selectedGenres.contains(genre)) {
            holder.itemView.setBackgroundColor(Color.LTGRAY) // Cambia esto al color que prefieras
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT) // Cambia esto al color de fondo normal
        }
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    fun updateData(genreList: Genres) {
        this.genresList.clear()
        this.genresList.addAll(genreList)
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        fun bind(genre: Genre, listener: OnGenreClickListener) {
            val binding = ItemGenreLayoutBinding.bind(itemView)
            binding.apply {
                txtGenreName.text = genre.name
                btnEditGenre.visibility = if (showButtons) View.VISIBLE else View.GONE
                btnDeleteGenre.visibility = if (showButtons) View.VISIBLE else View.GONE

                btnEditGenre.setOnClickListener {
                    listener.onGenreEdit(genre)
                }
                btnDeleteGenre.setOnClickListener {
                    listener.onGenreDelete(genre)
                }

                root.setOnClickListener {
                    listener.onGenreClick(genre)
                }
            }
        }
    }
}

interface OnGenreClickListener {
    fun onGenreClick(genre: Genre)
    fun onGenreDelete(genre: Genre)
    fun onGenreEdit(genre: Genre)
}
