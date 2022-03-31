/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class SleepDatabaseTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun getByNightId() = runBlocking {
        val night = SleepNight(6000L, 100000L, 5000000, -1 )
        sleepDao.insert(night)
        val sleepNight = sleepDao.get(6000L)
        assertThat(sleepNight?.nightId, `is`(6000L))
    }

    @Test
    @Throws(Exception::class)
    fun getAllNights() = runBlocking {
        val night1 = SleepNight(6000L, 100000L, 5000000, 4 )
        val night2 = SleepNight(7000L, 10000L, 500000, -1 )
        val night3 = SleepNight(2000L, 10000000L, 50000000, 3 )

        sleepDao.insert(night1)
        sleepDao.insert(night2)
        sleepDao.insert(night3)

        sleepDao.update(night3)
        val nightList = sleepDao.getAllNights()

        assertThat(nightList?.getOrAwaitValue().size, `is`(3))
    }

    @Test
    @Throws(Exception::class)
    fun getTonight() = runBlocking {
        val night1 = SleepNight(6000L, 100000L, 5000000, 4 )
        val night2 = SleepNight(7000L, 10000L, 500000, -1 )
        val night3 = SleepNight(2000L, 10000000L, 50000000, 3 )
        sleepDao.insert(night1)
        sleepDao.insert(night2)
        sleepDao.insert(night3)

        val tonight = sleepDao.getTonight()
        assertThat(tonight?.nightId, `is`(7000L) )
    }

    @Test
    @Throws(Exception::class)
    fun updateNight() = runBlocking {
        val night1 = SleepNight(6000L, 100000L, 5000000, 4 )
        val night2 = SleepNight(7000L, 10000L, 500000, -1 )
        val night3 = SleepNight(2000L, 10000000L, 50000000, 3 )
        sleepDao.insert(night1)
        sleepDao.insert(night2)
        sleepDao.insert(night3)

        night3.sleepQuality = 1
        sleepDao.update(night3)
        val updatedNight3 = sleepDao.get(2000L)

        assertThat(updatedNight3?.sleepQuality, `is`(1))
    }

    @Test
    @Throws(Exception::class)
    fun clear() = runBlocking {
        val night1 = SleepNight(6000L, 100000L, 5000000, 4 )
        val night2 = SleepNight(7000L, 10000L, 500000, -1 )
        val night3 = SleepNight(2000L, 10000000L, 50000000, 3 )

        sleepDao.insert(night1)
        sleepDao.insert(night2)
        sleepDao.insert(night3)

        sleepDao.update(night3)
        val nightList = sleepDao.getAllNights()

        sleepDao.clear().apply { nightList }

        assertThat(nightList.getOrAwaitValue().size, `is`(0))
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() = runBlocking {
        // Given
        val night = SleepNight(6000L, 100000L, 5000000, -1 )

        // When
        sleepDao.insert(night)

        // Then
        val tonight = sleepDao.getTonight()
        assertThat(tonight?.sleepQuality, `is`(-1))
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

