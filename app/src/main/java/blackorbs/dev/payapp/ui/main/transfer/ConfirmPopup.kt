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

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import blackorbs.dev.payapp.databinding.ConfirmBoxBinding
import blackorbs.dev.payapp.entities.Transaction
import blackorbs.dev.payapp.helpers.DataAccess.format
import blackorbs.dev.payapp.ui.main.accounts.AccountsViewModel
import blackorbs.dev.payapp.ui.main.history.HistoryViewModel

class ConfirmPopup : DialogFragment() {
    private lateinit var accountViewModel: AccountsViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var transaction: Transaction

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        accountViewModel = ViewModelProvider(requireActivity())[AccountsViewModel::class]
        historyViewModel = ViewModelProvider(requireActivity())[HistoryViewModel::class]
        transaction = ViewModelProvider(requireActivity())[TransferViewModel::class].transaction

        val binding = ConfirmBoxBinding.inflate(layoutInflater)
        binding.amount.text = format(transaction.amount)
        binding.sourceNumber.text = transaction.sourceAccount.number
        binding.sourceName.text = transaction.sourceAccount.name
        binding.destNumber.text = transaction.destinationAccount.number
        binding.destName.text = transaction.destinationAccount.name
        binding.confirmBtn.setOnClickListener { makePayment() }
        binding.closeBtn.setOnClickListener { dismiss() }
        isCancelable = false
        return AlertDialog.Builder(activity).setView(binding.root).create()
    }

    private fun makePayment(){
        accountViewModel.deposit(transaction)
        historyViewModel.addTransaction(transaction)
        SuccessPopup().show(parentFragmentManager, "success")
        dismiss()
    }

}