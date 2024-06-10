package com.example.librarysistem.ui.book.showBook.deleteBookToGenre

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
import com.example.librarysistem.databinding.ActivityBookToGenreDeleteBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Genre
import com.example.librarysistem.ui.adapters.GenreAdapter
import com.example.librarysistem.ui.adapters.OnGenreClickListener


class BookToGenreDeleteActivity : AppCompatActivity(), OnGenreClickListener {
    lateinit var binding: ActivityBookToGenreDeleteBinding
    private val model: BookToGenreDeleteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookToGenreDeleteBinding.inflate(layoutInflater)
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

    fun setupBookData(book: Book){
        binding.apply {
            Glide.with(this@BookToGenreDeleteActivity)
                .load(book.urlImage)
                .into(imgBookDeleteToGenre)
            txtDeleteBookToGenreName.text = book.name
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
                val adapter = (binding.lstGenreToBookDelete.adapter as GenreAdapter)
                adapter.updateData(model.filterGenresInBook())
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

    private fun setupEventListeners() {
        binding.btnDeleteBookToGenre.setOnClickListener {
            if (model.selectedGenres.value.isNullOrEmpty()) {
                Toast.makeText(this, "Please select at least one genre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            model.selectedGenres.value?.forEach {
                model.deleteBookToGenre(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.lstGenreToBookDelete.apply {
            this.adapter = GenreAdapter(ArrayList(), this@BookToGenreDeleteActivity, false)
            layoutManager = LinearLayoutManager(this@BookToGenreDeleteActivity)
        }
    }

    override fun onGenreClick(genre: Genre) {
        val adapter = binding.lstGenreToBookDelete.adapter as GenreAdapter
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