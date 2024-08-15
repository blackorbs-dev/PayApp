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

package blackorbs.dev.payapp.helpers

import blackorbs.dev.payapp.R
import blackorbs.dev.payapp.entities.Account
import blackorbs.dev.payapp.entities.AccountStatus
import blackorbs.dev.payapp.entities.AccountType
import java.util.Locale

object DataAccess {
    const val DELIM = ","

    val mockAccounts = listOf(
        Account("Jamiu Abiodun Akinyemi",100000, "0245552162", AccountType.Savings),
        Account("AbdulRoqeeb Soliu Ade",250000, "0703346489"),
        Account("Maryam Musbahudeen",600000, "1725373405"),
        Account("Mustapha Olanrewaju",1350000, "8168156922")
    )

    fun getResource(status: AccountStatus) = when(status){
        AccountStatus.Active -> R.string.status_active
        AccountStatus.Closed -> R.string.status_closed
    }

    fun getResource(type: AccountType) = when(type){
        AccountType.Savings -> R.string.type_savings
        AccountType.Current -> R.string.type_current
    }

    fun getStatusFromRes(res: Int) = when(res){
        R.string.status_active -> AccountStatus.Active
        else -> AccountStatus.Closed
    }

    fun getTypeFromRes(res: Int) = when(res){
        R.string.type_savings -> AccountType.Savings
        else -> AccountType.Current
    }

    fun format(number: Long) = String.format(Locale.getDefault(), "%,d", number)
}