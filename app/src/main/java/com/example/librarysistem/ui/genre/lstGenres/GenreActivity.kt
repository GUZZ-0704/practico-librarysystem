package com.example.librarysistem.ui.genre.lstGenres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityGenreBinding
import com.example.librarysistem.models.Genre
import com.example.librarysistem.ui.adapters.GenreAdapter
import com.example.librarysistem.ui.adapters.OnGenreClickListener
import com.example.librarysistem.ui.genre.editGenre.GenreEditActivity
import com.example.librarysistem.ui.genre.insertGenre.GenreAddActivity
import com.example.librarysistem.ui.genre.showGenre.GenreDetailActivity

class GenreActivity : AppCompatActivity(), OnGenreClickListener{
    lateinit var binding: ActivityGenreBinding
    private val model: GenreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGenreBinding.inflate(layoutInflater)
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
        model.fetchGenresList()
    }

    private fun setupViewModelListeners() {
        model.genreList.observe(this) { genres ->
            if (genres != null) {
                Log.d("GenreActivity", "Loaded ${genres.size} genres")
                val adapter = (binding.lstGenre.adapter as GenreAdapter)
                adapter.updateData(genres)
            } else {
                Log.e("GenreActivity", "Failed to load genres")
            }
        }
    }

    private fun setupRecyclerView() {
        binding.lstGenre.apply {
            this.adapter = GenreAdapter(ArrayList(), this@GenreActivity)
            layoutManager = LinearLayoutManager(this@GenreActivity)
        }
    }

    private fun setupEventListeners() {
        binding.fabAddGenre.setOnClickListener {
            val intent = Intent(this, GenreAddActivity::class.java)
            startActivity(intent)
        }
        binding.btnSeeBooks.setOnClickListener {
            /*val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
            finish()

        }
    }

    override fun onGenreClick(genre: Genre) {
        val intent = Intent(this, GenreDetailActivity::class.java).apply {
            putExtra("genreID", genre.id)
        }
        startActivity(intent)
    }

    override fun onGenreDelete(genre: Genre) {
        model.deleteGenre(genre)
    }

    override fun onGenreEdit(genre: Genre) {
        val intent = Intent(this, GenreEditActivity::class.java)
        intent.putExtra("genreID", genre.id)
        startActivity(intent)
    }
}