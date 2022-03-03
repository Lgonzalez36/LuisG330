package com.example.hellotoast

import android.util.Log
import androidx.lifecycle.ViewModel

/**
 * ViewModel containing all the logic needed to run the game
 */
class ViewModel : ViewModel() {


    // The current count
    var mCount = 0


    init {
        Log.i("GameViewModel", "GameViewModel created!")
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    /** Methods for updating the UI **/

    fun countup() {
        mCount++
    }
}