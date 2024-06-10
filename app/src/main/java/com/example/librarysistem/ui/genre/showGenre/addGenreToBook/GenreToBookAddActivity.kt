package com.example.librarysistem.ui.genre.showGenre.addGenreToBook

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityGenreToBookAddBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Genre
import com.example.librarysistem.ui.adapters.BookAdapter
import com.example.librarysistem.ui.adapters.GenreAdapter
import com.example.librarysistem.ui.adapters.OnBookClickListener

class GenreToBookAddActivity : AppCompatActivity(), OnBookClickListener {
    lateinit var binding: ActivityGenreToBookAddBinding
    private val model: GenreToBookAddViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGenreToBookAddBinding.inflate(layoutInflater)
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
        binding.btnAddGenreToBook.setOnClickListener {
            if (model.selectedBooks.value.isNullOrEmpty()) {
                Toast.makeText(this, "Please select at least one book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            model.selectedBooks.value?.forEach {
                model.addGenreToBook(it)
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
        model.bookList.observe(this) { genres ->
            genres?.let {
                val adapter = (binding.lstGenreToBook.adapter as BookAdapter)
                adapter.updateData(model.filterBooksNotInGenre())
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
        binding.txtGenreToBookName.text = genre.name
    }

    private fun setupRecyclerView() {
        binding.lstGenreToBook.apply {
            this.adapter = BookAdapter(ArrayList(), this@GenreToBookAddActivity, false)
            layoutManager = LinearLayoutManager(this@GenreToBookAddActivity)
        }
    }

    override fun onBookClick(book: Book) {
        val adapter = binding.lstGenreToBook.adapter as BookAdapter
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