package com.example.librarysistem.ui.book.editBook

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.librarysistem.R
import com.example.librarysistem.databinding.ActivityBookEditBinding
import com.example.librarysistem.models.Book

class BookEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityBookEditBinding
    private val model: BookEditViewModel by viewModels()
    private var bookId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bookId = intent.getIntExtra("bookID", -1)
        if (bookId != -1) {
            model.loadBook(bookId)
        }
        setupViewModelObservers()
        setupEventListeners()
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
        model.book.observe(this) {
            if (it == null) {
                return@observe
            }
            setupBookData(it)
        }
    }

    private fun setupEventListeners() {
        binding.btnSaveBookChanges.setOnClickListener {
            var name = binding.txtNameBookEdit.text.toString()
            var author = binding.txtAuthorBookEdit.text.toString()
            var editorial = binding.txtEditorialBookEdit.text.toString()
            var urlImage = binding.txtImgBookEdit.text.toString()
            var synopsis = binding.txtSynopsisBookEdit.text.toString()
            var ISBN = binding.txtISBNBookEdit.text.toString()
            var rating = binding.txtRatingEdit.text.toString().toFloat()
            model.update(name, author, editorial, urlImage, synopsis, ISBN, rating, bookId)
        }
    }

    fun setupBookData(book: Book){
        binding.apply {
            txtNameBookEdit.setText(book.name)
            txtAuthorBookEdit.setText(book.author)
            txtEditorialBookEdit.setText(book.editorial)
            txtImgBookEdit.setText(book.urlImage)
            txtSynopsisBookEdit.setText(book.synopsis)
            txtISBNBookEdit.setText(book.ISBN)
            txtRatingEdit.setText(book.rating.toString())
        }
    }
}