package com.payz.softpos.presentation.ui.history


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.payz.softpos.R
import com.payz.softpos.domain.model.Transaction
import com.payz.softpos.presentation.core.extension.setSingleClickListener
import kotlinx.android.synthetic.main.item_transaction.view.*


class TransactionsAdapter(
    private var data: MutableList<Transaction> = mutableListOf(),
    private val onItemSelected: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionsAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var name: TextView = itemView.tvTransactionName
        private var time: TextView = itemView.tvTransactionTime
        private var amount: TextView = itemView.tvTransactionAmount

        fun bind(transaction: Transaction, callBack: (Transaction) -> Unit) {
            name.text = transaction.transactionName
            time.text = transaction.transactionTime
            amount.text = transaction.transactionAmount.toString()
            itemView.setSingleClickListener { callBack(transaction) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position], onItemSelected)
    }
}
