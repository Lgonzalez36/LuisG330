package com.example.worddictionaryapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.worddictionaryapp.database.Word

class DictionayViewModel: ViewModel() {
    var currentWordFromQuery: Word? = null
}