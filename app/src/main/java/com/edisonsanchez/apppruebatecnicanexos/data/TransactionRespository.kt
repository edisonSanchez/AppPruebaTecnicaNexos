package com.edisonsanchez.apppruebatecnicanexos.data


import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TransactionsRepository @Inject constructor(private val endPointsApi: EndPointsApi,
    private val transactionsDao: TransactionsDao) {

    val transactionApproved = MutableLiveData<Boolean>()
    val transactionCanceled = MutableLiveData<Boolean>()
    val transactions = MutableLiveData<List<Transaction>>()
    val transactionFound = MutableLiveData<Transaction?>()
    @OptIn(DelicateCoroutinesApi::class)
    fun authorizeTransaction(headerBase64: String, requestAuthorizationTransaction:
    RequestAuthorizationTransaction) {
        endPointsApi.authorizeTransaction(headerBase64, requestAuthorizationTransaction)
            .enqueue(object: Callback<AuthorizationResponse> {
            override fun onResponse(call: Call<AuthorizationResponse>,
                response: Response<AuthorizationResponse>) {
                response.body()?.let {
                    if (it.statusCode == RESPONSE_OK) {
                        val transaction = Transaction(it.rrn, it.receiptId,
                            requestAuthorizationTransaction.terminalCode,
                            requestAuthorizationTransaction.commerceCode,
                            requestAuthorizationTransaction.amount,
                            requestAuthorizationTransaction.card, false)
                        GlobalScope.launch(Dispatchers.IO) {
                            transactionsDao.insertTransaction(transaction)
                            withContext(Dispatchers.Main) {
                                transactionApproved.value = true
                            }
                        }
                    } else {
                        transactionApproved.value = false
                    }
                } ?: run {
                    transactionApproved.value = false
                }
            }

            override fun onFailure(call: Call<AuthorizationResponse>, t: Throwable) {
                transactionApproved.value = false
            }

        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllTransactionsApproved(){
        GlobalScope.launch(Dispatchers.IO) {
            val list = transactionsDao.getAllApprovedTransactions()
            withContext(Dispatchers.Main) {
                transactions.value = list
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun searchTransaction(receiptId: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val transaction = transactionsDao.searchTransaction(receiptId)
            withContext(Dispatchers.Main) {
                transactionFound.value = transaction
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun annulationTransaction (headerBase64: String, requestAnnulationTransaction:
    RequestAnnulationTransaction, transaction: Transaction) {
        endPointsApi.invalidateTransaction(headerBase64, requestAnnulationTransaction).enqueue(
            object : Callback<AnnulationResponse> {
            override fun onResponse(call: Call<AnnulationResponse>,
                response: Response<AnnulationResponse>) {
                response.body()?.let {
                    if (it.statusCode == RESPONSE_OK) {
                        GlobalScope.launch(Dispatchers.IO) {
                            transaction.isAnnulation = true
                            transactionsDao.update(transaction)
                            withContext(Dispatchers.Main) {
                                transactionCanceled.value = true
                            }
                        }
                    } else {
                        transactionCanceled.value = false
                    }
                } ?: run {
                    transactionCanceled.value = false
                }
            }

            override fun onFailure(call: Call<AnnulationResponse>, t: Throwable) {
                transactionCanceled.value = false
            }

        })
    }

}