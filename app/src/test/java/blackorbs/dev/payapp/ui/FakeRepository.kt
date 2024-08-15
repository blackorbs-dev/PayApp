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

package blackorbs.dev.payapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import blackorbs.dev.payapp.database.BaseRepository
import blackorbs.dev.payapp.entities.Transaction

class FakeRepository(private val transactions: MutableList<Transaction> = mutableListOf()): BaseRepository {
    private val liveTransactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    override suspend fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
        liveTransactions.value = transactions
    }

    override fun getTransactions(): LiveData<List<Transaction>> =  liveData{
        emitSource(liveTransactions)
    }
}