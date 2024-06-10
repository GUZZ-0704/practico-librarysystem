package com.example.librarysistem.ui.book.showBook.addBookToGenre

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityBookToGenreAddBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Genre
import com.example.librarysistem.ui.adapters.GenreAdapter
import com.example.librarysistem.ui.adapters.OnGenreClickListener

class BookToGenreAddActivity : AppCompatActivity(), OnGenreClickListener {
    lateinit var binding: ActivityBookToGenreAddBinding
    private val model: BookToGenreAddViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookToGenreAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupViewmodelObservers()
        var bookId = intent.getIntExtra("bookID", -1)
        if (bookId != -1) {
            model.loadBook(bookId)
            model.fetchGenresList()
        }
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnAddBookToGenre.setOnClickListener {
            if (model.selectedGenres.value.isNullOrEmpty()) {
                Toast.makeText(this, "Please select at least one genre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            model.selectedGenres.value?.forEach {
                model.addBookToGenre(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.lstGenreToBook.apply {
            this.adapter = GenreAdapter(ArrayList(), this@BookToGenreAddActivity, false)
            layoutManager = LinearLayoutManager(this@BookToGenreAddActivity)
        }
    }

    private fun setupViewmodelObservers() {
        model.book.observe(this) {
            if (it == null) {
                return@observe
            }
            setupBookData(it)
        }
        model.genreList.observe(this) { genres ->
            genres?.let {
                val adapter = (binding.lstGenreToBook.adapter as GenreAdapter)
                adapter.updateData(model.filterGenresNotInBook())
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

    fun setupBookData(book: Book){
        binding.apply {
            Glide.with(this@BookToGenreAddActivity)
                .load(book.urlImage)
                .into(imgBookAddToGenre)
            txtBookToGenreName.text = book.name
        }
    }

    override fun onGenreClick(genre: Genre) {
        val adapter = binding.lstGenreToBook.adapter as GenreAdapter
        genre.isSelected = !genre.isSelected
        if (genre.isSelected) {
            model.addToSelectedGenres(genre)
            adapter.selectedGenres.add(genre)
        } else {
            model.removeFromSelectedGenres(genre)
            adapter.selectedGenres.remove(genre)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onGenreDelete(genre: Genre) {
        TODO("Not yet implemented")
    }

    override fun onGenreEdit(genre: Genre) {
        TODO("Not yet implemented")
    }
}