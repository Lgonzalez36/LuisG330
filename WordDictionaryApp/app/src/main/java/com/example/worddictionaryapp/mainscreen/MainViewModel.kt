package com.example.worddictionaryapp.mainscreen

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.worddictionaryapp.database.Dictionary
import com.example.worddictionaryapp.database.DictionaryDataBaseDao
import com.example.worddictionaryapp.database.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    var dataSource: DictionaryDataBaseDao,
    application: Application) : AndroidViewModel(application) {


    private lateinit var word: MutableLiveData<Dictionary>
    var allWords = dataSource.getAllWords()
    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        Log.i("MainViewModel", "MainViewModel created! 1")
    }


    fun getWordsFromDataBase(): List<Dictionary>? {
        Log.i("MainViewModel", "MainViewModel created! 4")
        return allWords.value
    }
    fun checkDb(): Boolean {
        if (dataSource.getAllWords().value!!.isEmpty()){
            return false
        }
        else if  (dataSource.getAllWords().value!!.size >= 0){
                return true
            }
        return false
    }


    suspend fun addWord(word: Dictionary){
        viewModelScope.launch(Dispatchers.IO){
            Log.e(ContentValues.TAG, "******************* addWord *************:")
            dataSource.insert(word)
            // Then
            val dictionary = dataSource.get(word.word)
            dictionary?.word?.let { assert(it == word.word) }
            Log.e(ContentValues.TAG, "******************* Word *************: ${dictionary?.word}")
            Log.e(ContentValues.TAG, "******************* Def1 *************: ${dictionary?.shortDef1}")
            Log.e(ContentValues.TAG, "******************* Def2 *************: ${dictionary?.shortDef2}")
            Log.e(ContentValues.TAG, "******************* Def3 *************: ${dictionary?.shortDef3}")
            Log.e(ContentValues.TAG, "******************* img *************: ${dictionary?.img}")
            Log.e(ContentValues.TAG, "******************* status *************: ${dictionary?.status}")
        }
    }

    suspend fun updateData(word: Dictionary) {
        dataSource.update(word)
    }

    fun getCount():Int{
       return dataSource.getAllWords().value!!.size
    }

}