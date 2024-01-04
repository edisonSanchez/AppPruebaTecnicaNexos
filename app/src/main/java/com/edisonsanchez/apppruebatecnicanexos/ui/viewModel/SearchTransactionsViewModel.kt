package com.edisonsanchez.apppruebatecnicanexos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.domain.UseCaseSearchTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchTransactionsViewModel @Inject constructor(private val useCaseSearchTransaction:
                                                      UseCaseSearchTransaction) : ViewModel() {

    val transactionFound : LiveData<Transaction?> = useCaseSearchTransaction.transactionFound
    private val _idReceiptOk = MutableLiveData<Boolean>()
    val idReceiptOk: LiveData<Boolean> = _idReceiptOk
    fun searchTransaction(numberReceipt: String) {
        _idReceiptOk.value = numberReceipt.isNotEmpty()
        if (_idReceiptOk.value == true) {
            useCaseSearchTransaction.invoke(numberReceipt)
        }
    }

}