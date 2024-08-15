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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import blackorbs.dev.payapp.entities.Account
import blackorbs.dev.payapp.entities.AccountType
import blackorbs.dev.payapp.entities.Transaction
import blackorbs.dev.payapp.ui.main.accounts.AccountsViewModel
import blackorbs.dev.payapp.ui.main.history.HistoryViewModel
import blackorbs.dev.payapp.ui.main.transfer.TransferViewModel
import blackorbs.dev.payapp.utils.MainCoroutineRule
import blackorbs.dev.payapp.utils.TestExtensions.getOrAwaitValue
import blackorbs.dev.payapp.utils.TestUtil.testTransaction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
class ViewModelsTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val testAccounts = listOf(
        Account("Jamiu Abiodun",150000, "0245552162", AccountType.Savings),
        Account("AbdulRoqeeb Soliu Ade",250000, "0703346489"),
        Account("Maryam Musbahudeen",500000, "1725373405"),
        Account("Mustapha Olanrewaju",1350000, "8168156922")
    )

    private lateinit var accountsViewModel: AccountsViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private var transferViewModel = TransferViewModel()

    @Test
    fun `transfer page viewModel test successful`() = runTest{
        val source = testAccounts[2]
        val dest = testAccounts[1]

        assertTrue(transferViewModel.setSource(source))
        assertEquals(source, transferViewModel.sourceAccount.value)
        assertEquals(false, transferViewModel.setDestination(source))
        assertEquals(null, transferViewModel.destinationAccount.value)
        assertTrue(transferViewModel.setDestination(dest))
        assertEquals(dest, transferViewModel.destinationAccount.value)
        assertEquals(false, transferViewModel.setSource(dest))
        transferViewModel.amount = 5000
        val transaction = transferViewModel.transaction
        assertEquals(5000, transaction.amount)
        assertEquals(source, transaction.sourceAccount)
        assertEquals(dest, transaction.destinationAccount)
    }

    @Test
    fun `history page viewModel test successful`() = runTest{
        val data = historyViewModel.transactions
        assertTrue(!data.isInitialized)
        historyViewModel.addTransaction(testTransaction(5000))
        assertEquals(5000, data.getOrAwaitValue { runCurrent() }[0].amount)
        historyViewModel.addTransaction(testTransaction(6900))
        val transaction = testTransaction(21110)
        historyViewModel.addTransaction(transaction)
        assertEquals(3, data.getOrAwaitValue { runCurrent() }.size)
        assertEquals(transaction,data.value!![data.value!!.size-1])
    }

    @Test
    fun `accounts page viewModel test successful`(){
        assertEquals(testAccounts[0], accountsViewModel.getAccount(testAccounts[0].number))
        assertEquals(testAccounts, accountsViewModel.accountsList.value)
        val source = testAccounts[2]
        val dest = testAccounts[1]
        val sourceBal = source.balance
        val destBal = dest.balance

        val accountList = accountsViewModel.accountsList
        accountsViewModel.deposit(Transaction(5000, source, dest))
        assertEquals(
            sourceBal-5000,
            accountList.value!![
                accountList.value!!.indexOfFirst { it.number == source.number}
            ].balance
        )
        assertEquals(
            destBal+5000,
            accountList.value!![
                accountList.value!!.indexOfFirst { it.number == dest.number}
            ].balance
        )
    }

    @Before
    fun setUp(){
        accountsViewModel = AccountsViewModel(testAccounts)
        historyViewModel = HistoryViewModel(FakeRepository())
    }
}