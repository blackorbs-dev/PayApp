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

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import blackorbs.dev.payapp.helpers.DataAccess.DELIM
import blackorbs.dev.payapp.helpers.DataAccess.getResource
import blackorbs.dev.payapp.helpers.DataAccess.getStatusFromRes
import blackorbs.dev.payapp.helpers.DataAccess.getTypeFromRes
import java.time.LocalDateTime

@Entity(tableName = "transactions")
data class Transaction(
    val amount: Long,
    val sourceAccount: Account,
    val destinationAccount: Account,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0L
)

class AccountConverter{
    @TypeConverter
    fun accountToString(account: Account) =
        account.name+DELIM+account.balance+DELIM+account.number+DELIM+getResource(account.type)+DELIM+getResource(account.status)
        
    @TypeConverter
    fun stringToAccount(string: String) = run {
        val array = string.split(DELIM)
        Account(array[0], array[1].toLong(), array[2], getTypeFromRes(array[3].toInt()), getStatusFromRes(array[4].toInt()))
    }
}

class LocalDateTimeConverter{
    @TypeConverter
    fun timeToString(time: LocalDateTime) = time.toString()

    @TypeConverter
    fun stringToTime(string: String) = LocalDateTime.parse(string)
}
