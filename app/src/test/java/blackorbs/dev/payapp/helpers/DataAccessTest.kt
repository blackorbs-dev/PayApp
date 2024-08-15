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
import blackorbs.dev.payapp.R
import blackorbs.dev.payapp.entities.AccountStatus
import blackorbs.dev.payapp.entities.AccountType
import blackorbs.dev.payapp.helpers.DataAccess.getResource
import blackorbs.dev.payapp.helpers.DataAccess.getStatusFromRes
import blackorbs.dev.payapp.helpers.DataAccess.getTypeFromRes
import org.junit.Assert.assertEquals
import org.junit.Test

@SmallTest
class DataAccessTest {
    @Test
    fun `data access get resources from values successful`(){
        assertEquals(R.string.status_active, getResource(AccountStatus.Active))
        assertEquals(R.string.status_closed, getResource(AccountStatus.Closed))
        assertEquals(R.string.type_savings, getResource(AccountType.Savings))
        assertEquals(R.string.type_current, getResource(AccountType.Current))
    }
    @Test
    fun `data access get values from resources successful`(){
        assertEquals(AccountStatus.Active, getStatusFromRes(R.string.status_active))
        assertEquals(AccountStatus.Closed, getStatusFromRes(R.string.status_closed))
        assertEquals(AccountType.Savings, getTypeFromRes(R.string.type_savings))
        assertEquals(AccountType.Current, getTypeFromRes(R.string.type_current))
    }
}