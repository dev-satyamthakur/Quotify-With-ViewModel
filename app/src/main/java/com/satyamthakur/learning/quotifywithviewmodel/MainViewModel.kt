package com.satyamthakur.learning.quotifywithviewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.nio.charset.Charset
import kotlin.math.sign

class MainViewModel(val context: Context): ViewModel() {

    private var quoteList: Array<Quote> = emptyArray()
    private var index = 0

    init {
        quoteList = loadQuotesFromAssets()
    }

    private fun loadQuotesFromAssets(): Array<Quote> {
        val inputStream = context.assets.open("quotes.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote() = quoteList[index]

    fun nextQuote(): Quote {
        index++
        index = index % quoteList.size
        return quoteList[index]
    }

    fun prevQuote(): Quote {
        index--
        index = (index + quoteList.size) % quoteList.size
        return quoteList[index]
    }

}