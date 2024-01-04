package com.edisonsanchez.apppruebatecnicanexos.domain


import com.edisonsanchez.apppruebatecnicanexos.data.RequestAuthorizationTransaction
import com.edisonsanchez.apppruebatecnicanexos.data.TransactionsRepository
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class UseCaseAuthorizeTransaction @Inject constructor(private val transactionsRepository:
                                                      TransactionsRepository) {

    val approvedTransaction = transactionsRepository.transactionApproved

    @OptIn(ExperimentalEncodingApi::class)
    fun invoke(requestAuthorizationTransaction: RequestAuthorizationTransaction) {
        val stringToConvert = requestAuthorizationTransaction.commerceCode +
                requestAuthorizationTransaction.terminalCode
        val headerBase64 = "Basic ${Base64.encode(stringToConvert.toByteArray(Charsets.UTF_8))}"
        transactionsRepository.authorizeTransaction(headerBase64, requestAuthorizationTransaction)
    }

}