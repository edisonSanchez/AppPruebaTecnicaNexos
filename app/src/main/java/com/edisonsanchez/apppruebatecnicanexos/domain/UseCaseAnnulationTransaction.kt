package com.edisonsanchez.apppruebatecnicanexos.domain

import com.edisonsanchez.apppruebatecnicanexos.data.RequestAnnulationTransaction
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.data.TransactionsRepository
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class UseCaseAnnulationTransaction @Inject constructor(private val transactionsRepository:
                                                       TransactionsRepository) {

    val canceledTransactionOk = transactionsRepository.transactionCanceled
    @OptIn(ExperimentalEncodingApi::class)
    fun invoke(requestAnnulationTransaction: RequestAnnulationTransaction, transaction: Transaction) {
        val stringToConvert = transaction.commerceCode +transaction.idDevice
        val headerBase64 = "Basic ${Base64.encode(stringToConvert.toByteArray(Charsets.UTF_8))}"
        transactionsRepository.annulationTransaction(headerBase64, requestAnnulationTransaction,
            transaction)
    }

}