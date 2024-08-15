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

package blackorbs.dev.payapp.ui.main.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import blackorbs.dev.payapp.entities.Account
import blackorbs.dev.payapp.entities.Transaction

class TransferViewModel : ViewModel() {
    private val _sourceAccount = MutableLiveData<Account>()
    val sourceAccount: LiveData<Account> = _sourceAccount
    private val _destinationAccount = MutableLiveData<Account>()
    val destinationAccount: LiveData<Account> = _destinationAccount
    var amount = 0L

    fun setSource(source: Account) : Boolean{
        return if(source == destinationAccount.value) false
        else{
            _sourceAccount.value = source
            true
        }
    }

    fun setDestination(destination: Account) : Boolean{
        return if(destination == sourceAccount.value) false
        else{
            _destinationAccount.value = destination
            true
        }
    }

    val transaction get() =
        Transaction(amount, sourceAccount.value!!, destinationAccount.value!!)
}