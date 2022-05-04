package com.example.words.screens.overview

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.words.database.WordDao
import com.example.words.entity.Word
import com.example.words.network.WordsFilter
import kotlinx.coroutines.launch

class DictWordsViewModel(
    private val dao: WordDao,
    application: Application
) : AndroidViewModel(application) {

    private var _currentFilter = MutableLiveData<WordsFilter>()
    val currentFilter: LiveData<WordsFilter>
        get() = _currentFilter


    lateinit var dictWords: LiveData<List<Word>>


    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        Log.e(ContentValues.TAG, "******************* ViewModel Created *************: ")
        getWords()
    }

    private fun getWords() {
        Log.e(ContentValues.TAG, "******************* ViewModel Created / getWords() *************: ")
        viewModelScope.launch {
            dictWords = dao.getAllWords()
            _currentFilter.value = WordsFilter.SHOW_ALL

        }
    }

    fun changeFilter(filter: WordsFilter) {
        Log.e(ContentValues.TAG, "******************* ViewModel Created / changeFilter() *************: ")
        dictWords = when (filter) {
            WordsFilter.SHOW_ACTIVE ->   dao.getActiveWords()
            WordsFilter.SHOW_INACTIVE -> dao.getInactiveWords()
            else ->  dao.getAllWords()
        }
        _currentFilter.value = filter
    }
}