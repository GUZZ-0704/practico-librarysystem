package com.example.librarysistem.ui.genre.editGenre

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityGenreEditBinding
import com.example.librarysistem.models.Genre

class GenreEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityGenreEditBinding
    private val model: GenreEditViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGenreEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val genreId = intent.getIntExtra("genreID", -1)
        if (genreId != -1) {
            model.loadGenre(genreId)
        }
        setupViewModelObservers()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnSaveGenreChanges.setOnClickListener {
            val name = binding.txtGenreEditName.text.toString()
            model.update(name, intent.getIntExtra("genreID", -1))
        }
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
        model.genre.observe(this) {
            if (it == null) {
                return@observe
            }
            setupGenreData(it)
        }
    }

    private fun setupGenreData(genre: Genre) {
        binding.txtGenreEditName.setText(genre.name)
    }
}