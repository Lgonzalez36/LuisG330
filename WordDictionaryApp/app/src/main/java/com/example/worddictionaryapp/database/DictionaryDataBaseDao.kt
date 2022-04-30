package com.example.worddictionaryapp.database

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface DictionaryDataBaseDao {

    @Insert
    suspend fun insert(word: Dictionary)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    suspend  fun update(word: Dictionary)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from word_table WHERE word = :key")
    suspend fun get(key: String): Dictionary?
    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM word_table")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAllWords(): LiveData<List<Dictionary>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM word_table ORDER BY word DESC LIMIT 1")
    suspend fun getTonight(): Dictionary?
}