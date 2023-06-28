package com.satyamthakur.learning.quotifywithviewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.nio.charset.Charset
import kotlin.math.sign

class MainViewModel(val context: Context): ViewModel() {

    var quoteList: Array<Quote> = emptyArray()
    var index = MutableLiveData<Int>(0)

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

    fun getQuote() = quoteList[index.value!!]

    fun nextQuote() {
        index.value = (index.value!! + 1) % quoteList.size
    }

    fun prevQuote() {
        index.value = (index.value!! - 1 + quoteList.size) % quoteList.size
    }

}