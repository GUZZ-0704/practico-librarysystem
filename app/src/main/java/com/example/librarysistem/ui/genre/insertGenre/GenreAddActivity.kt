package com.example.librarysistem.ui.genre.insertGenre

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityGenreAddBinding

class GenreAddActivity : AppCompatActivity() {
    lateinit var binding : ActivityGenreAddBinding
    private val model : GenreAddViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGenreAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    private fun setupEventListeners() {
        binding.btnAddGenre.setOnClickListener {
            var name = binding.txtGenreAddName.text.toString()
            model.addGenre(name)
        }
    }
}