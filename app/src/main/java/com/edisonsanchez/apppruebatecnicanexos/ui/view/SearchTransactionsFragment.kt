package com.edisonsanchez.apppruebatecnicanexos.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.edisonsanchez.apppruebatecnicanexos.R
import com.edisonsanchez.apppruebatecnicanexos.data.EXTRA_TRANSACTION
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.data.hideKeyBoard
import com.edisonsanchez.apppruebatecnicanexos.data.showSimpleAlertDialog
import com.edisonsanchez.apppruebatecnicanexos.ui.viewModel.SearchTransactionsViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchTransactionsFragment : Fragment() {

    private val viewModel: SearchTransactionsViewModel by viewModels()
    private lateinit var backgroundProgress: View
    private lateinit var progressBar: ProgressBar
    private lateinit var fieldNumberReceiptTransaction: TextInputEditText
    private lateinit var buttonSearchTransaction: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backgroundProgress = view.findViewById(R.id.backgroundProgress)
        progressBar = view.findViewById(R.id.progressBar)
        fieldNumberReceiptTransaction = view.findViewById(R.id.fieldNumberReceiptTransaction)
        buttonSearchTransaction = view.findViewById(R.id.buttonSearchTransaction)
        buttonSearchTransaction.setOnClickListener {
            showProgress()
            activity?.currentFocus?.let { view -> hideKeyBoard(requireContext(), view) }
            viewModel.searchTransaction(fieldNumberReceiptTransaction.text.toString())
        }
        configObservers()
    }

    private fun configObservers() {
        val observerTransactionFound = Observer<Transaction?> { transaction ->
           transaction?.let {
               val intent = Intent(requireContext(), DetailsTransactionActivity::class.java)
               intent.putExtra(EXTRA_TRANSACTION, it)
               startActivity(intent)
           } ?: run {
               showSimpleAlertDialog( requireContext(), requireContext()
                   .getString(R.string.message_not_found_transaction))
           }
            hideProgress()
        }
        val observerNumberReceipt = Observer<Boolean> { value ->
            if (!value) {
                fieldNumberReceiptTransaction.error = requireContext()
                    .getString(R.string.error_number_receipt)
            }
            hideProgress()
        }
        viewModel.idReceiptOk.observe(viewLifecycleOwner, observerNumberReceipt)
        viewModel.transactionFound.observe(viewLifecycleOwner, observerTransactionFound)
    }

    private fun showProgress() {
        backgroundProgress.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        backgroundProgress.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

}