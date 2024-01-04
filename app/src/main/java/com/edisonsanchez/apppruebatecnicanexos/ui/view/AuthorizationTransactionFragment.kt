package com.edisonsanchez.apppruebatecnicanexos.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.edisonsanchez.apppruebatecnicanexos.R
import com.edisonsanchez.apppruebatecnicanexos.data.hideKeyBoard
import com.edisonsanchez.apppruebatecnicanexos.data.showSimpleAlertDialog
import com.edisonsanchez.apppruebatecnicanexos.ui.viewModel.AuthorizationTransactionViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationTransactionFragment : Fragment() {

    private val viewModel: AuthorizationTransactionViewModel by viewModels()
    private lateinit var fieldIdTransaction: TextInputEditText
    private lateinit var fieldCommerceCode: TextInputEditText
    private lateinit var fieldTerminalCode: TextInputEditText
    private lateinit var fieldAmount: TextInputEditText
    private lateinit var fieldCard: TextInputEditText
    private lateinit var buttonAuthorization: Button
    private lateinit var backgroundProgress: View
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_autorization_transaction, container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fieldIdTransaction = view.findViewById(R.id.fieldIdDeviceTransaction)
        fieldCommerceCode = view.findViewById(R.id.fieldCommerceCode)
        fieldTerminalCode = view.findViewById(R.id.fieldTerminalCode)
        fieldAmount = view.findViewById(R.id.fieldAmount)
        fieldCard = view.findViewById(R.id.fieldCard)
        buttonAuthorization = view.findViewById(R.id.buttonAuthorization)
        backgroundProgress = view.findViewById(R.id.backgroundProgress)
        progressBar = view.findViewById(R.id.progressBar)
        buttonAuthorization.setOnClickListener {
            showProgress()
            viewModel.authorizeTransaction(fieldIdTransaction.text.toString(),
                fieldCommerceCode.text.toString(), fieldTerminalCode.text.toString(),
                fieldAmount.text.toString(), fieldCard.text.toString())
        }
        configObserves()
    }

    private fun configObserves() {
        val observerApprovedTransaction = Observer<Boolean> {
            if (it) {
                fieldIdTransaction.setText("")
                fieldCard.setText("")
                fieldAmount.setText("")
                fieldTerminalCode.setText("")
                fieldCommerceCode.setText("")
                showSimpleAlertDialog(requireContext(), requireContext()
                    .getString(R.string.message_approved_transaction_ok))
            } else {
                showSimpleAlertDialog(requireContext(), requireContext()
                    .getString(R.string.message_approved_transaction_fail))
            }
            hideProgress()
        }
        val observerIdTransaction = Observer<Boolean> { value ->
            if (!value) {
                fieldIdTransaction.error = requireContext().getString(R.string.error_id_transaction)
                hideProgress()
            }
        }

        val observerCommerceCode = Observer<Boolean> { value ->
            if (!value) {
                fieldCommerceCode.error = requireContext().getString(R.string.error_commerce_code)
                hideProgress()
            }
        }

        val observerTerminalCode = Observer<Boolean> { value ->
            if (!value) {
                fieldTerminalCode.error = requireContext().getString(R.string.error_id_device)
                hideProgress()
            }
        }

        val observerAmount = Observer<Boolean> { value ->
            if (!value) {
                fieldAmount.error = requireContext().getString(R.string.error_amount)
                hideProgress()
            }
        }

        val observerCard = Observer<Boolean> { value ->
            if (!value) {
                fieldCard.error = requireContext().getString(R.string.error_number_card)
                hideProgress()
            }
        }

        viewModel.approvedTransaction.observe(viewLifecycleOwner, observerApprovedTransaction)
        viewModel.idTransactionOk.observe(viewLifecycleOwner, observerIdTransaction)
        viewModel.commerceCodeOk.observe(viewLifecycleOwner, observerCommerceCode)
        viewModel.idDeviceOk.observe(viewLifecycleOwner, observerTerminalCode)
        viewModel.numberCardOk.observe(viewLifecycleOwner, observerCard)
        viewModel.amountOk.observe(viewLifecycleOwner, observerAmount)
    }

    private fun showProgress() {
        activity?.currentFocus?.let { hideKeyBoard(requireContext(), it) }
        backgroundProgress.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        backgroundProgress.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

}