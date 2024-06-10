package com.example.librarysistem.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librarysistem.databinding.ItemBookLayoutBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Books

class BookAdapter(
    val booksList: ArrayList<Book>,
    val listener : OnBookClickListener,
    val showButtons: Boolean = true
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    var selectedBooks = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = booksList[position]
        holder.bind(book, listener)
        if (selectedBooks.contains(book)) {
            holder.itemView.setBackgroundColor(Color.LTGRAY) // Cambia esto al color que prefieras
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT) // Cambia esto al color de fondo normal
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    fun updateData(bookList: Books) {
        this.booksList.clear()
        this.booksList.addAll(bookList)
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(book: Book, listener: OnBookClickListener) {
            val binding = ItemBookLayoutBinding.bind(itemView)
            binding.apply {
                if (book.isSelected) {
                    root.setBackgroundResource(android.R.color.holo_blue_light)
                } else {
                    root.setBackgroundResource(android.R.color.white)
                }
                lblBookName.text = book.name
                Glide.with(itemView.context.applicationContext)
                    .load(book.urlImage)
                    .fitCenter()
                    .into(imgBook)
                btnEditBook.visibility = if (showButtons) View.VISIBLE else View.GONE
                btnDeleteBook.visibility = if (showButtons) View.VISIBLE else View.GONE

                btnEditBook.setOnClickListener {
                    listener.onBookEdit(book)
                }
                btnDeleteBook.setOnClickListener {
                    listener.onBookDelete(book)
                }
                root.setOnClickListener {
                    listener.onBookClick(book)
                }
            }

        }
    }
}

interface OnBookClickListener {
    fun onBookClick(book: Book)
    fun onBookDelete(book: Book)
    fun onBookEdit(book: Book)
}
