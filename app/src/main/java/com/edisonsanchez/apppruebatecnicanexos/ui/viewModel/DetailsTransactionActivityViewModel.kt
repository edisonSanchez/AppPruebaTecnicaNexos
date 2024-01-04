package com.edisonsanchez.apppruebatecnicanexos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.edisonsanchez.apppruebatecnicanexos.data.RequestAnnulationTransaction
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.domain.UseCaseAnnulationTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsTransactionActivityViewModel @Inject constructor(private val
                                                              useCaseAnnulationTransaction:
                                                              UseCaseAnnulationTransaction) :
    ViewModel() {

    val canceledTransaction: LiveData<Boolean> = useCaseAnnulationTransaction.canceledTransactionOk
    fun annulationTransaction(receiptId: String, rrn: String, transaction: Transaction) {
        val requestAnnulationTransaction = RequestAnnulationTransaction(receiptId, rrn)
        useCaseAnnulationTransaction.invoke(requestAnnulationTransaction, transaction)
    }

}