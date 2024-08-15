/*
 * Copyright 2024 BlackOrbs (blackorbs@icloud.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package blackorbs.dev.payapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import blackorbs.dev.payapp.utils.MainCoroutineRule
import blackorbs.dev.payapp.utils.TestExtensions.getOrAwaitValue
import blackorbs.dev.payapp.utils.TestUtil.testTransaction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@SmallTest
class DatabaseTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var localDatabase: LocalDatabase
    private lateinit var transactionDao: TransactionDao
    private lateinit var repository: Repository

    @Before
    fun setUp(){
        localDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).allowMainThreadQueries().build()
        transactionDao = localDatabase.transactionDao()
        repository = Repository(transactionDao, coroutineRule.testDispatcher)
    }

    @Test
    fun `room database access successful`() = runTest{
        val data = transactionDao.getAll()
        assertTrue(!data.isInitialized)
        assertTrue(data.getOrAwaitValue { runCurrent() }.isEmpty())
        transactionDao.add(testTransaction(5000))
        assertEquals(5000, data.getOrAwaitValue { runCurrent() }[0].amount)
        transactionDao.add(testTransaction(6000))
        transactionDao.add(testTransaction(7000))
        assertEquals(3, data.getOrAwaitValue { runCurrent() }.size)
    }

    @Test
    fun `repository test successful`() = runTest{
        val data = repository.getTransactions()
        assertTrue(!data.isInitialized)
        assertTrue(data.getOrAwaitValue { runCurrent() }.isEmpty())
        repository.addTransaction(testTransaction(5000))
        assertEquals(5000, data.getOrAwaitValue { runCurrent() }[0].amount)
        repository.addTransaction(testTransaction(6000))
        repository.addTransaction(testTransaction(7000))
        assertEquals(3, data.getOrAwaitValue { runCurrent() }.size)
    }

    @After
    fun cleanUp(){
        localDatabase.close()
    }
}