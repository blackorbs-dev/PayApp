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

package blackorbs.dev.payapp.ui.main.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import blackorbs.dev.payapp.R
import blackorbs.dev.payapp.databinding.HistoryItemBinding
import blackorbs.dev.payapp.entities.Transaction
import blackorbs.dev.payapp.helpers.DataAccess.format
import java.time.format.DateTimeFormatter

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun updateList(accounts: List<Transaction>){
        asyncListDiffer.submitList(accounts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(asyncListDiffer.currentList[position])

    override fun getItemCount() = asyncListDiffer.currentList.size

    private val diffCallback = object : ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.amount == newItem.amount
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(transaction: Transaction){
            binding.amount.text = format(transaction.amount)
            binding.title.text = binding.root.context.getString(R.string.transaction_details, transaction.id, transaction.sourceAccount.name, transaction.destinationAccount.name)
            binding.time.text = transaction.dateTime.format(DateTimeFormatter.ofPattern("MMM d, h:mm a"))
        }
    }

}