package com.edisonsanchez.apppruebatecnicanexos.domain

import androidx.lifecycle.MutableLiveData
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.data.TransactionsRepository
import javax.inject.Inject

class UseCaseGetAllTransactions @Inject constructor(private val transactionsRepository:
                                                    TransactionsRepository) {

    val transactions: MutableLiveData<List<Transaction>> = transactionsRepository.transactions
    fun invoke() {
        transactionsRepository.getAllTransactionsApproved()
    }

}