package com.example.worddictionaryapp.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DictionaryDatabaseTest : TestCase() {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var dictionaryDao: DictionaryDataBaseDao
    private lateinit var db: DictionaryDataBase

    @Before
    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, DictionaryDataBase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dictionaryDao = db.dictionaryDataBaseDao
    }

    @After
    fun closeDb(){
        db.close()
    }


    


//    @Test
//    @Throws(Exception::class)
//    fun getByNightId() = runBlocking {
//        val night = Dictionary("baseball", "ball", "ball2", "ball3", "img" )
//        dictionaryDao.insert(night)
//        val dictionary = dictionaryDao.get(6000L)
//        assertThat(dictionary?.word, `is`("baseball"))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun getAllNights() = runBlocking {
//        val word1 = Dictionary("baseball", "ball", "ball2", "ball3", "img" )
//        val word2 = Dictionary("baseball", "ball", "ball2", "ball3", "img" )
//        val word3 = Dictionary("baseball", "ball", "ball2", "ball3", "img" )
//
//        dictionaryDao.insert(word1)
//        dictionaryDao.insert(word2)
//        dictionaryDao.insert(word3)
//
//        dictionaryDao.update(word3)
//        val dictionaryList = dictionaryDao.getAllNights()
//
//        assertThat(dictionaryList.getOrAwaitValue().size, `is`(3))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun getTonight() = runBlocking {
//        val night1 = Dictionary(6000L, 100000L, 5000000, 4 )
//        val night2 = Dictionary(7000L, 10000L, 500000, -1 )
//        val night3 = Dictionary(2000L, 10000000L, 50000000, 3 )
//        dictionaryDao.insert(night1)
//        dictionaryDao.insert(night2)
//        dictionaryDao.insert(night3)
//
//        val tonight = dictionaryDao.getTonight()
//        assertThat(tonight?.word, `is`("word") )
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun updateNight() = runBlocking {
//        val night1 = Dictionary(6000L, 100000L, 5000000, 4 )
//        val night2 = Dictionary(7000L, 10000L, 500000, -1 )
//        val night3 = Dictionary(2000L, 10000000L, 50000000, 3 )
//        dictionaryDao.insert(night1)
//        dictionaryDao.insert(night2)
//        dictionaryDao.insert(night3)
//
//        night3.sleepQuality = 1
//        dictionaryDao.update(night3)
//        val updatedNight3 = dictionaryDao.get(2000L)
//
//        assertThat(updatedNight3?.sleepQuality, `is`(1))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun clear() = runBlocking {
//        val night1 = Dictionary(6000L, 100000L, 5000000, 4 )
//        val night2 = Dictionary(7000L, 10000L, 500000, -1 )
//        val night3 = Dictionary(2000L, 10000000L, 50000000, 3 )
//
//        dictionaryDao.insert(night1)
//        dictionaryDao.insert(night2)
//        dictionaryDao.insert(night3)
//
//        dictionaryDao.update(night3)
//        val nightList = dictionaryDao.getAllNights()
//
//        dictionaryDao.clear().apply { nightList }
//
//        assertThat(nightList.getOrAwaitValue().size, `is`(0))
//    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() = runBlocking {
        // Given
        val word = Dictionary("baseball", "ball", "ball2", "ball3", "img", false )

        // When
        dictionaryDao.insert(word)

        // Then
        val dictionary = dictionaryDao.getTonight()
        MatcherAssert.assertThat(dictionary?.word, Matchers.`is`("baseball"))
    }
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetNight() {
//        val night = SleepNight(6000L, 100000L, 5000000, -1 )
//        sleepDao.insert(night)
//        val tonight = sleepDao.getTonight()
//        assertThat(tonight?.sleepQuality,`is`(-1))
//    }
}

//                      need                        done


//get                   getByNightId                getByNightId
//getAllNights          getAllNights*               getAllNights              need to do codeLab 5.3
//update                updateNight                 updateNight
//getTonight            getTonight                  getTonight
//clear                 clear
//insert                insertAndGetNight           insertAndGetNight

