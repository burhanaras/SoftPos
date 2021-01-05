package com.payz.softpos.presentation.ui.refund

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.payz.softpos.R
import com.payz.softpos.presentation.core.extension.getViewModelFactory
import com.payz.softpos.presentation.core.view.LoadingPopUp
import com.payz.softpos.presentation.core.view.LoadingPopUpType
import com.payz.softpos.presentation.ui.history.TransactionsAdapter
import kotlinx.android.synthetic.main.fragment_refund.*

class RefundFragment : Fragment() {

    private lateinit var refundViewModel: RefundViewModel
    private val loadingPopUp = LoadingPopUp(LoadingPopUpType.REFUND)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        refundViewModel =
            ViewModelProvider(this, getViewModelFactory()).get(RefundViewModel::class.java)
        return inflater.inflate(R.layout.fragment_refund, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvRefund.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvRefund.setHasFixedSize(true)

        refundViewModel.transactions.observe(viewLifecycleOwner, {
            rvRefund.adapter = TransactionsAdapter(it.toMutableList()) { transaction ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Are you sure?")
                    .setMessage("You want to refund ${transaction.transactionAmount} to customer.")
                    .setPositiveButton("Yes") { dialog, _ ->
                        refundViewModel.refund(transaction)
                        dialog.dismiss()
                    }
                    .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        })

        refundViewModel.showProgressPopup.observe(viewLifecycleOwner, {
            loadingPopUp.show(requireContext(), it)
        })
        refundViewModel.updateProgressPopup.observe(viewLifecycleOwner, {
            loadingPopUp.update(it)
        })
    }
}