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

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import blackorbs.dev.payapp.entities.Transaction
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val transactionDao: TransactionDao, private val ioDispatcher: CoroutineDispatcher) : BaseRepository {
    override suspend fun addTransaction(transaction: Transaction){
        transactionDao.add(transaction)
    }

    override fun getTransactions() = liveData(ioDispatcher) {
        emitSource(transactionDao.getAll())
    }
}

interface BaseRepository {
    suspend fun addTransaction(transaction: Transaction)
    fun getTransactions(): LiveData<List<Transaction>>
}