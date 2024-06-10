package com.example.librarysistem.ui.genre.showGenre.deleteGenreToBook

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityGenreToBookDeleteBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Genre
import com.example.librarysistem.ui.adapters.BookAdapter
import com.example.librarysistem.ui.adapters.OnBookClickListener

class GenreToBookDeleteActivity : AppCompatActivity(), OnBookClickListener{
    lateinit var binding: ActivityGenreToBookDeleteBinding
    private val model: GenreToBookDeleteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGenreToBookDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupViewmodelObservers()
        var genreId = intent.getIntExtra("genreID", -1)
        if (genreId != -1) {
            model.loadGenre(genreId)
            model.fetchBookList()
        }
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnDeleteGenreToBook.setOnClickListener {
            if (model.selectedBooks.value.isNullOrEmpty()) {
                Toast.makeText(this, "Please select at least one book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            model.selectedBooks.value?.forEach {
                model.deleteGenreToBook(it)
            }
        }
    }

    private fun setupViewmodelObservers() {
        model.genre.observe(this) {
            if (it == null) {
                return@observe
            }
            setupGenreData(it)
        }
        model.bookList.observe(this) { books ->
            books?.let {
                val adapter = (binding.lstDeleteGenreToBook.adapter as BookAdapter)
                adapter.updateData(model.filterBooksInGenre())
            }
        }
        model.showErrorMesssage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    fun setupGenreData(genre: Genre) {
        binding.txtDeleteGenreToBookName.text = genre.name
    }

    private fun setupRecyclerView() {
        binding.lstDeleteGenreToBook.apply {
            this.adapter = BookAdapter(ArrayList(), this@GenreToBookDeleteActivity, false)
            layoutManager = LinearLayoutManager(this@GenreToBookDeleteActivity)
        }
    }

    override fun onBookClick(book: Book) {
        val adapter = binding.lstDeleteGenreToBook.adapter as BookAdapter
        book.isSelected = !book.isSelected
        if(book.isSelected){
            model.addToSelectedBooks(book)
            adapter.selectedBooks.add(book)
        }else{
            model.removeFromSelectedBooks(book)
            adapter.selectedBooks.remove(book)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onBookDelete(book: Book) {
        TODO("Not yet implemented")
    }

    override fun onBookEdit(book: Book) {
        TODO("Not yet implemented")
    }
}