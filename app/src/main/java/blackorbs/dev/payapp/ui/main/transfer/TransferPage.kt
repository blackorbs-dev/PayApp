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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import blackorbs.dev.payapp.R
import blackorbs.dev.payapp.databinding.AccountItemBinding
import blackorbs.dev.payapp.databinding.TransferPageBinding
import blackorbs.dev.payapp.entities.Account
import blackorbs.dev.payapp.helpers.DataAccess.format
import blackorbs.dev.payapp.helpers.DataAccess.getResource
import blackorbs.dev.payapp.ui.main.accounts.AccountsViewModel
import com.google.android.material.snackbar.Snackbar

class TransferPage : Fragment() {
    private var binding : TransferPageBinding? = null
    private lateinit var accountViewModel: AccountsViewModel
    private lateinit var viewModel: TransferViewModel
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if(binding == null){
            binding = TransferPageBinding.inflate(layoutInflater)
            viewModel = ViewModelProvider(requireActivity())[TransferViewModel::class]
            accountViewModel = ViewModelProvider(requireActivity())[AccountsViewModel::class]
            adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1)
            setUpPage()
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpPage(){
        binding!!.selectSource.setAdapter(adapter)
        binding!!.selectDestination.setAdapter(adapter)
        binding!!.selectSource.setOnItemClickListener { _,_,position,_->
            if(!viewModel.setSource(accountViewModel.accountsList.value!![position]))
                showMessage(getString(R.string.same_account_error))
        }
        binding!!.selectDestination.setOnItemClickListener { _,_,position,_->
            if(!viewModel.setDestination(accountViewModel.accountsList.value!![position]))
                showMessage(getString(R.string.same_account_error))
        }
        binding!!.amount.doAfterTextChanged{ _ ->
            handleInputError()
        }
        binding!!.proceedBtn.setOnClickListener {
            viewModel.amount = binding!!.amount.text.toString().toLong()
            ConfirmPopup().show(parentFragmentManager, "confirm")
        }
    }

    private fun setUpObservers(){
        accountViewModel.accountsList.observe(viewLifecycleOwner){
            if(adapter.count==0){
                adapter.addAll(it.map {account -> getString(R.string.select_account_details, account.name, account.number) })
                adapter.notifyDataSetChanged()
            }
            viewModel.sourceAccount.value?.let { account ->
                viewModel.setSource(it[it.indexOfFirst { item -> item.number == account.number }])
            }
            viewModel.destinationAccount.value?.let { account ->
                viewModel.setDestination(it[it.indexOfFirst { item -> item.number == account.number }])
            }
        }
        viewModel.sourceAccount.observe(viewLifecycleOwner){
            bind(it, binding!!.sourceAccount)
        }
        viewModel.destinationAccount.observe(viewLifecycleOwner){
            bind(it, binding!!.destinationAccount)
        }
    }

    private fun bind(account: Account, binding: AccountItemBinding){
        binding.name.text = account.name
        binding.balance.text = format(account.balance)
        binding.status.text = binding.root.context.getString(getResource(account.status))
        binding.accountNo.text = account.number
        binding.type.text = binding.root.context.getString(getResource(account.type))
        binding.root.isVisible = true
        handleInputError()
    }

    private fun handleInputError(){
        val text = binding!!.amount.text?.toString()
        val amount = if(text?.isBlank() == true) 0 else text?.toLong()
        val account = viewModel.sourceAccount.value
        val amountOK = if(amount != null) {
            when{
                amount <= 0L -> {
                    binding!!.error.text = getString(R.string.zero_input_error)
                    false
                }
                account != null && amount > account.balance -> {
                    binding!!.error.text = getString(R.string.insufficient_bal_error)
                    false
                }
                else -> true
            }

        } else false
        binding!!.error.isVisible = !text.isNullOrBlank() && !amountOK
        binding!!.proceedBtn.isEnabled = amountOK && account != null && viewModel.destinationAccount.value != null
    }

    private fun showMessage(msg: String){
        Snackbar.make(binding!!.root, msg, Snackbar.LENGTH_LONG).show()
    }

}