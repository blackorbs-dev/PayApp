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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import blackorbs.dev.payapp.databinding.HistoryPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryPage : Fragment() {
    private var binding : HistoryPageBinding? = null
    private lateinit var viewModel: HistoryViewModel
    private val adapter = HistoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if(binding == null){
            binding = HistoryPageBinding.inflate(layoutInflater)
            viewModel = ViewModelProvider(requireActivity())[HistoryViewModel::class]
            binding!!.historyList.adapter = adapter
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactions.observe(viewLifecycleOwner){
            adapter.updateList(it)
        }
    }
}