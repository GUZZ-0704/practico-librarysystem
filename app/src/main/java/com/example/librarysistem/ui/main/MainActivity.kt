package com.example.librarysistem.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityMainBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.ui.adapters.BookAdapter
import com.example.librarysistem.ui.adapters.OnBookClickListener
import com.example.librarysistem.ui.book.showBook.BookDetailActivity
import com.example.librarysistem.ui.book.editBook.BookEditActivity
import com.example.librarysistem.ui.book.insertBook.BookAddActivity
import com.example.librarysistem.ui.genre.lstGenres.GenreActivity

class MainActivity : AppCompatActivity(), OnBookClickListener {
    lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
        setupRecyclerView()
        setupViewModelListeners()
    }
    override fun onResume() {
        super.onResume()
        model.fetchBooksList()
    }

    private fun setupEventListeners() {
        binding.fabAddBook.setOnClickListener {
            val intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
        }
        binding.btnSeeGenres.setOnClickListener {
            val intent = Intent(this, GenreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModelListeners() {
        model.bookList.observe(this) {
            val adapter = (binding.lstBooks.adapter as BookAdapter)
            adapter.updateData(it)
        }
    }


    private fun setupRecyclerView() {
        binding.lstBooks.apply {
            this.adapter = BookAdapter(ArrayList(), this@MainActivity)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }


    override fun onBookClick(book: Book) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra("bookID", book.id)
        startActivity(intent)
    }

    override fun onBookDelete(book: Book) {
        model.deleteBook(book)
    }

    override fun onBookEdit(book: Book) {
        val intent = Intent(this, BookEditActivity::class.java)
        intent.putExtra("bookID", book.id)
        startActivity(intent)
    }

}