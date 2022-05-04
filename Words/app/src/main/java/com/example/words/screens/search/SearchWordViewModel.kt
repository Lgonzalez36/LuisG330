package com.example.words.screens.search

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.words.R
import com.example.words.database.WordDao
import com.example.words.entity.Word
import com.example.words.network.DictionaryApi
import com.example.words.network.WordsFilter
import com.example.words.network.parseJsonToStringList
import com.example.words.network.parseJsonToWord
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

// The view model uses the database (dao) and the API to
// access data for this fragment.
class SearchWordViewModel(private val dao: WordDao) : ViewModel() {
    private val TAG = javaClass.simpleName


    private val _wordInDb = MutableLiveData<Boolean>()
    val wordInDb: LiveData<Boolean>
        get() = _wordInDb
    // The fragment observes changes to the wordDef, so that when wordDef
    // is populated with a word found using the API it can navigate
    // to the add word fragment
    private val _wordDef = MutableLiveData<Word>()
    val wordDef: LiveData<Word>
        get() = _wordDef

    // The fragment observes changes to the suggestedWords, so that when it
    // is populated with a word found using the API it displays the list
    // of suggestion on the screen.
    private val _suggestedWords = MutableLiveData<List<String>>()
    val suggestedWords: LiveData<List<String>>
        get() = _suggestedWords

//    /**
//     * Call getMarsRealEstateProperties() on init so we can display status immediately.
//     */
//    init {
//        Log.e(ContentValues.TAG, "******************* SearchWordViewModel Created *************: ")
//        _suggestedWords.value = emptyList()
//    }

    // Search the API for the searchWord
    fun performWordSearch(searchWord: String) {
        Log.d(TAG, "Search for word $searchWord")
        if (searchWord.isBlank()) {
            return
        }

        viewModelScope.launch {
            val response = DictionaryApi.retrofitService.getWord(searchWord)
            Log.d(TAG, response.body()!!.substring(0, 30))
            val jsonString = response.body()!!
            if (jsonString.startsWith("[{")) {
                Log.d(TAG, "parseJsonToWord")
                _wordDef.value = parseJsonToWord(searchWord, jsonString)
            } else if (jsonString.startsWith("[")) {
                Log.e(ContentValues.TAG, "******************* LIST OF WORDS  *************: ")
                _suggestedWords.value = parseJsonToStringList(jsonString)
                Log.e(ContentValues.TAG, "******************* LIST OF WORDS  *************: $jsonString")
            }
        }
    }

    suspend fun isWordInDictionary(searchWord: String): Boolean {
        _wordInDb.value = dao.wordExists(searchWord)
        return dao.wordExists(searchWord)
    }

    fun getwords(): List<String>? {
        return _suggestedWords.value
    }

    @SuppressLint("NullSafeMutableLiveData")
    // This is needed so that observers (the fragment) can reset the wordDef
    // to prevent re-triggering of the data change event.
    fun resetWordDef() {
        _wordDef.value = null
    }
}