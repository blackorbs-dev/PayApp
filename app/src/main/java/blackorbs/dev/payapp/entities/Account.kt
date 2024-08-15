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

package blackorbs.dev.payapp.entities

data class Account(
    val name: String,
    var balance: Long = 0L,
    val number: String,
    val type: AccountType = AccountType.Current,
    val status: AccountStatus = AccountStatus.Active
)

enum class AccountType{
    Savings, Current
}

enum class AccountStatus {
    Active, Closed
}
