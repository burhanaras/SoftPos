package com.payz.softpos.presentation.ui.detail.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.payz.softpos.R

class TransactionDetailFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionDetailFragment()
    }

    private lateinit var viewModel: TransactionDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_transaction_detail0, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionDetailViewModel::class.java)
        // TODO: Use the ViewModel

    }

}