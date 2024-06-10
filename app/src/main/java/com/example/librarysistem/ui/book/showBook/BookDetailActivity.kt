package com.example.librarysistem.ui.book.showBook

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityBookDetailBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.ui.book.showBook.addBookToGenre.BookToGenreAddActivity
import com.example.librarysistem.ui.book.showBook.deleteBookToGenre.BookToGenreDeleteActivity

class BookDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityBookDetailBinding
    private val model: BookDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var bookId = intent.getIntExtra("bookID", -1)
        if (bookId != -1) {
            model.loadBook(bookId)
        }
        setupViewModelObservers()
        setupEventListeners()
    }

    override fun onResume() {
        super.onResume()
        var bookId = intent.getIntExtra("bookID", -1)
        if (bookId != -1) {
            model.loadBook(bookId)
        }
    }

    private fun setupEventListeners() {
        binding.btnAddGenrestoBook.setOnClickListener {
            val intent = Intent(this, BookToGenreAddActivity::class.java)
            intent.putExtra("bookID", model.book.value?.id)
            model.loadBook(model.book.value?.id ?: -1)
            startActivity(intent)
        }
        binding.btnDeleteGenres.setOnClickListener {
            val intent = Intent(this, BookToGenreDeleteActivity::class.java)
            intent.putExtra("bookID", model.book.value?.id)
            model.loadBook(model.book.value?.id ?: -1)
            startActivity(intent)
        }
    }

    private fun setupViewModelObservers() {
        model.book.observe(this) {
            if (it == null) {
                return@observe
            }
            setupBookData(it)
        }
    }


    fun setupBookData(book: Book){
        binding.apply {
            Glide.with(this@BookDetailActivity)
                .load(book.urlImage)
                .into(imgBookDetail)
            lblBookNameDetail.text = book.name
            txtISBN.text = book.ISBN
            txtAuthor.text = book.author
            txtEditorial.text = book.editorial
            txtGenres.text = book.genres.joinToString("\n") { it.name } // Agrega el nombre del género
            txtRating.text = book.rating.toString()
            txtSynopsis.text = book.synopsis
        }
    }
}