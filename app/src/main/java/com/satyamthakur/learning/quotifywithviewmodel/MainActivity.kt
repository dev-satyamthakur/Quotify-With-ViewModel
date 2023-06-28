package com.satyamthakur.learning.quotifywithviewmodel

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.satyamthakur.learning.quotifywithviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private var binding: ActivityMainBinding? = null
    lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        _binding = binding!!
        setContentView(_binding.root)

        // making status bar transparent
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(this@MainActivity))
            .get(MainViewModel::class.java)

        mainViewModel.index.observe(this, Observer {
            setQuote(mainViewModel.quoteList[it])
        })

        _binding.nextButton.setOnClickListener {
            mainViewModel.nextQuote()
        }

        _binding.prevButton.setOnClickListener {
            mainViewModel.prevQuote()
        }

        _binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuote().text)
            startActivity(intent)
        }
    }

    private fun setQuote(quote: Quote) {
        _binding.tvQuote.text = quote.text
        _binding.tvAuthor.text = quote.author
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}