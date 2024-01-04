package com.edisonsanchez.apppruebatecnicanexos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.domain.UseCaseGetAllTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApprovedTransactionsViewModel @Inject constructor(private val useCaseGetAllTransactions:
                                                        UseCaseGetAllTransactions): ViewModel() {

    val getApprovedTransactions: LiveData<List<Transaction>> = useCaseGetAllTransactions.transactions

    fun getAllApprovedTransactions() {
        useCaseGetAllTransactions.invoke()
    }

}