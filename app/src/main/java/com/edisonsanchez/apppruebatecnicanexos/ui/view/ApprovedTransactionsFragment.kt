package com.edisonsanchez.apppruebatecnicanexos.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.edisonsanchez.apppruebatecnicanexos.R
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.data.TransactionsAdapter
import com.edisonsanchez.apppruebatecnicanexos.ui.viewModel.ApprovedTransactionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApprovedTransactionsFragment : Fragment() {

    private val viewModel: ApprovedTransactionsViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageNotFoundTransactions: TextView
    private lateinit var backgroundProgress: View
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_approved_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        messageNotFoundTransactions = view.findViewById(R.id.messageNotFoundTransactions)
        backgroundProgress = view.findViewById(R.id.backgroundProgress)
        progressBar = view.findViewById(R.id.progressBar)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.getAllApprovedTransactions()
        }
        configObserver()
    }

    override fun onResume() {
        super.onResume()
        showProgress()
        viewModel.getAllApprovedTransactions()
    }

    private fun configObserver() {
        val observerTransactions = Observer<List<Transaction>> { transactions ->
            if (transactions.isEmpty()) {
                recyclerView.visibility = View.GONE
                messageNotFoundTransactions.visibility = View.VISIBLE
            } else {
                configRecyclerView(transactions)
            }
            if (swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.isRefreshing = false
            } else {
                hideProgress()
            }
        }
        viewModel.getApprovedTransactions.observe(viewLifecycleOwner, observerTransactions)
    }

    private fun configRecyclerView(transactions: List<Transaction>) {
        messageNotFoundTransactions.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        val adapter = TransactionsAdapter(transactions, requireContext())
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,
            false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
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