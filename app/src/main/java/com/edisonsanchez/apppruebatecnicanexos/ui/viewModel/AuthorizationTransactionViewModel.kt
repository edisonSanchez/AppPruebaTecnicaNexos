package com.edisonsanchez.apppruebatecnicanexos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edisonsanchez.apppruebatecnicanexos.data.RequestAuthorizationTransaction
import com.edisonsanchez.apppruebatecnicanexos.domain.UseCaseAuthorizeTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationTransactionViewModel @Inject constructor(private val useCaseAuthorizeTransaction:
                                                            UseCaseAuthorizeTransaction):
    ViewModel() {

    val approvedTransaction: LiveData<Boolean> = useCaseAuthorizeTransaction.approvedTransaction
    private val _idTransactionOk = MutableLiveData<Boolean>()
    val idTransactionOk: LiveData<Boolean> = _idTransactionOk
    private val _commerceCodeOk = MutableLiveData<Boolean>()
    val commerceCodeOk: LiveData<Boolean> = _commerceCodeOk
    private val _idDeviceOk = MutableLiveData<Boolean>()
    val idDeviceOk: LiveData<Boolean> = _idDeviceOk
    private val _amountOk = MutableLiveData<Boolean>()
    val amountOk: LiveData<Boolean> = _amountOk
    private val _numberCardOk = MutableLiveData<Boolean>()
    val numberCardOk: LiveData<Boolean> = _numberCardOk
    fun authorizeTransaction(idTransaction: String, commerceCode: String, idDevice: String,
                             amount: String, numberCard: String) {
        var requestOk: Boolean
        if (idTransaction.isNotEmpty()) {
            requestOk = true
           _idTransactionOk.value = true
        } else {
            requestOk = false
            _idTransactionOk.value = false
        }
        if (commerceCode.isNotEmpty()) {
            _commerceCodeOk.value = true
        } else {
            _commerceCodeOk.value = false
            requestOk = false
        }
        if (idDevice.isNotEmpty()) {
            _idDeviceOk.value = true
        } else {
            _idDeviceOk.value = false
            requestOk = false
        }
        if (amount.isNotEmpty()) {
            _amountOk.value = true
        } else {
            _amountOk.value = false
            requestOk = false
        }
        if (numberCard.isNotEmpty()) {
            _numberCardOk.value = true
        } else {
            _numberCardOk.value = false
            requestOk = false
        }
        if (requestOk) {
            val requestAuthorizationTransaction = RequestAuthorizationTransaction(idTransaction,
                commerceCode, idDevice, amount, numberCard)
            useCaseAuthorizeTransaction.invoke(requestAuthorizationTransaction)
        }
    }

}