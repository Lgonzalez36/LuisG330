package com.example.words.screens.add

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import com.example.words.database.WordDao
import com.example.words.entity.Word
import kotlinx.coroutines.runBlocking

class AddWordViewModel(
    word: Word, application: Application,var  dao: WordDao
) : AndroidViewModel(application) {
    val word = word

    fun addWord() = runBlocking {
        Log.e(TAG, "******************* Word *************: ${word.id}")
        dao.insertWord(word)

        if (dao.wordExists(word.id)){
            Log.e(TAG, "******************* Word EXITS *************: ")
            Log.e(TAG, "******************* Word2 *************: ${word.id}")
        }else{
            Log.e(TAG, "******************* Word DOES NOT EXITS *************: ")
        }
    }
}