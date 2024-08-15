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

package blackorbs.dev.payapp.ui.main.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import blackorbs.dev.payapp.entities.Account
import blackorbs.dev.payapp.entities.Transaction
import blackorbs.dev.payapp.helpers.DataAccess.mockAccounts

class AccountsViewModel(defAccounts: List<Account> = emptyList()) : ViewModel() {
    private val _accounts = mutableListOf<Account>()
    private val _accountsList : MutableLiveData<List<Account>> = MutableLiveData()
    val accountsList : LiveData<List<Account>> = _accountsList

    init {
        _accounts.addAll(defAccounts.ifEmpty{ mockAccounts })
        _accountsList.value = _accounts
    }

    fun deposit(transaction: Transaction){
        getAccount(transaction.sourceAccount.number).balance -= transaction.amount
        getAccount(transaction.destinationAccount.number).balance += transaction.amount
        _accountsList.value = _accounts
    }

    fun getAccount(number: String) = _accounts[_accounts.indexOfFirst { it.number == number }]
}