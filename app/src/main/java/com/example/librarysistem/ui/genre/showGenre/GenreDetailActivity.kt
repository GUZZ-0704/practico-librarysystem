package com.example.librarysistem.ui.genre.showGenre

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityGenreDetailBinding
import com.example.librarysistem.models.Book
import com.example.librarysistem.models.Genre
import com.example.librarysistem.ui.adapters.BookAdapter
import com.example.librarysistem.ui.adapters.OnBookClickListener
import com.example.librarysistem.ui.genre.showGenre.addGenreToBook.GenreToBookAddActivity
import com.example.librarysistem.ui.genre.showGenre.deleteGenreToBook.GenreToBookDeleteActivity

class GenreDetailActivity : AppCompatActivity(), OnBookClickListener{
    lateinit var binding: ActivityGenreDetailBinding
    private val model: GenreDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGenreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupViewModelObservers()
        var genreId = intent.getIntExtra("genreID", -1)
        if (genreId != -1) {
            model.loadGenre(genreId)
            model.fetchBooksList()
        }
        model.genre.value?.let { genre ->
            setupGenreData(genre)
        }
        setupEventListeners()
    }

    override fun onResume() {
        super.onResume()
        var genreId = intent.getIntExtra("genreID", -1)
        if (genreId != -1) {
            model.loadGenre(genreId)
            model.fetchBooksList()
        }
    }

    private fun setupEventListeners() {
        binding.apply {
            btnAddGenreToBook.setOnClickListener {
                val intent = Intent(this@GenreDetailActivity, GenreToBookAddActivity::class.java)
                intent.putExtra("genreID", model.genre.value?.id)
                model.loadGenre(model.genre.value?.id ?: -1)
                startActivity(intent)
            }
            btnDeleteGenreToBook.setOnClickListener {
                val intent = Intent(this@GenreDetailActivity, GenreToBookDeleteActivity::class.java)
                intent.putExtra("genreID", model.genre.value?.id)
                model.loadGenre(model.genre.value?.id ?: -1)
                startActivity(intent)
            }
        }
    }

    private fun setupViewModelObservers() {
        model.genre.observe(this) { genre ->
            genre?.let {
                setupGenreData(it)
                val adapter = (binding.lstBooksOfGenre.adapter as BookAdapter)
                adapter.updateData(model.filterBookListByGenre())
            }
        }

        model.bookList.observe(this) { books ->
            books?.let {
                val adapter = (binding.lstBooksOfGenre.adapter as BookAdapter)
                adapter.updateData(model.filterBookListByGenre())
            }
        }
    }

    private fun setupRecyclerView() {
        val adapter = BookAdapter(ArrayList(), this, false)
        binding.lstBooksOfGenre.layoutManager = LinearLayoutManager(this)
        binding.lstBooksOfGenre.adapter = adapter
    }

    private fun setupGenreData(genre: Genre){
        binding.apply {
            lblGenreDetailName.text = genre.name
        }

    }

    override fun onBookClick(book: Book) {
        Toast.makeText(this, "${book.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onBookDelete(book: Book) {
        TODO("Not yet implemented")
    }

    override fun onBookEdit(book: Book) {
        TODO("Not yet implemented")
    }
}