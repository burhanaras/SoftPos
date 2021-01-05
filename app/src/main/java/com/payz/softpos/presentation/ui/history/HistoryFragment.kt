package com.payz.softpos.presentation.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.payz.softpos.R
import com.payz.softpos.presentation.core.extension.getViewModelFactory
import com.payz.softpos.presentation.ui.detail.TransactionDetailActivity
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewModel =
            ViewModelProvider(this, getViewModelFactory()).get(HistoryViewModel::class.java)
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvHistory.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvHistory.setHasFixedSize(true)

        historyViewModel.transactions.observe(viewLifecycleOwner, {
            rvHistory.adapter = TransactionsAdapter(it.toMutableList()) {
                val intent = Intent(requireContext(), TransactionDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }
}