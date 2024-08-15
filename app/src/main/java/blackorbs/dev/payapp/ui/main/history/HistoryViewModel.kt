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

package blackorbs.dev.payapp.ui.main.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blackorbs.dev.payapp.database.BaseRepository
import blackorbs.dev.payapp.entities.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel  @Inject constructor(private val repository: BaseRepository): ViewModel() {
    val transactions = repository.getTransactions()

    fun addTransaction(transaction: Transaction){
        viewModelScope.launch {
            repository.addTransaction(transaction)
        }
    }
}