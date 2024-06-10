package com.example.librarysistem.ui.book.insertBook

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityBookAddBinding
import com.example.librarysistem.ui.main.MainViewModel

class BookAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityBookAddBinding
    private val model: BookAddViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookAddBinding.inflate(layoutInflater)
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
        binding.btnAddBook.setOnClickListener {
            var name = binding.txtNameBookAdd.text.toString()
            var author = binding.txtAuthorBookAdd.text.toString()
            var editorial = binding.txtEditorialBookAdd.text.toString()
            var imgUrl = binding.txtImgBookAdd.text.toString()
            var synopsis = binding.txtSynopsisBookAdd.text.toString()
            var isbn = binding.txtISBNBookAdd.text.toString()
            var rating = binding.txtRatingBookAdd.text.toString().toFloat()
            model.addBook(name, author, editorial, imgUrl, synopsis, isbn, rating)
        }
    }
}