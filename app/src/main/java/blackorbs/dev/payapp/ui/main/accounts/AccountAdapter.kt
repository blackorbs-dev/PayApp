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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import blackorbs.dev.payapp.databinding.AccountItemBinding
import blackorbs.dev.payapp.entities.Account
import blackorbs.dev.payapp.helpers.DataAccess.format
import blackorbs.dev.payapp.helpers.DataAccess.getResource
import java.util.Locale

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    fun updateList(accounts: List<Account>){
        asyncListDiffer.submitList(accounts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AccountItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(asyncListDiffer.currentList[position])

    override fun getItemCount() = asyncListDiffer.currentList.size

    private val diffCallback = object : ItemCallback<Account>() {
        override fun areItemsTheSame(oldItem: Account, newItem: Account) = oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: Account, newItem: Account) = oldItem.balance == newItem.balance
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(private val binding: AccountItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(account: Account){
            binding.name.text = account.name
            binding.balance.text = format(account.balance)
            binding.status.text = binding.root.context.getString(getResource(account.status))
            binding.accountNo.text = account.number
            binding.type.text = binding.root.context.getString(getResource(account.type))
        }
    }
}