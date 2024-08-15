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

import androidx.test.filters.SmallTest
import blackorbs.dev.payapp.entities.Account
import blackorbs.dev.payapp.entities.AccountConverter
import blackorbs.dev.payapp.entities.LocalDateTimeConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

@SmallTest
class ConverterTest {

    private val timeConverter = LocalDateTimeConverter()
    private val accountConverter = AccountConverter()

    @Test
    fun `LocalDateTime converter test success`(){
        val time = LocalDateTime.now()
        assertEquals(
            time,
            timeConverter.stringToTime(timeConverter.timeToString(time))
        )
    }

    @Test
    fun `Account converter test success`(){
        val account = Account("Jamiu", number = "1234")
        assertEquals(
            account,
            accountConverter.stringToAccount(
                accountConverter.accountToString(account)
            )
        )
    }
}