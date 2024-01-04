package com.edisonsanchez.apppruebatecnicanexos.domain

import androidx.lifecycle.MutableLiveData
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.data.TransactionsRepository
import javax.inject.Inject

class UseCaseSearchTransaction @Inject constructor(private val transactionsRepository:
                                                   TransactionsRepository) {

    val transactionFound: MutableLiveData<Transaction?> = transactionsRepository.transactionFound
    fun invoke(receiptId: String) {
        transactionsRepository.searchTransaction(receiptId)
    }

}