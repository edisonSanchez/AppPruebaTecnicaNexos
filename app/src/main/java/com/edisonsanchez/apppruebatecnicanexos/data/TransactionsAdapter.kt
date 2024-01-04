package com.edisonsanchez.apppruebatecnicanexos.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edisonsanchez.apppruebatecnicanexos.R

class TransactionsAdapter(private val transactions: List<Transaction>, private val context: Context)
    : RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_transactions_approved, parent,
            false)
        return TransactionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val currentTransaction = transactions[position]
        holder.idTransaction.text = currentTransaction.idTransaction
        holder.valueTransaction.text = currentTransaction.valueTransaction
        holder.numberCard.text = currentTransaction.numberCard
        holder.idReceiptTransaction.text = currentTransaction.numberReceipt
        if (currentTransaction.isAnnulation) {
            holder.stateTransaction.text = context.getString(R.string.text_state_invalidate_transaction)
        } else {
            holder.stateTransaction.text = context.getString(R.string.text_state_approved_transaction)
        }
    }


    class TransactionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val idTransaction: TextView = view.findViewById(R.id.idTransaction)
        val valueTransaction: TextView = view.findViewById(R.id.valueTransaction)
        val numberCard: TextView = view.findViewById(R.id.numberCardTransaction)
        val idReceiptTransaction: TextView = view.findViewById(R.id.receiptIdTransaction)
        val stateTransaction: TextView = view.findViewById(R.id.stateTransaction)

    }


}