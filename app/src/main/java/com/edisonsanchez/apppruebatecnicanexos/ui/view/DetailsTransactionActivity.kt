package com.edisonsanchez.apppruebatecnicanexos.ui.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.edisonsanchez.apppruebatecnicanexos.R
import com.edisonsanchez.apppruebatecnicanexos.data.EXTRA_TRANSACTION
import com.edisonsanchez.apppruebatecnicanexos.data.Transaction
import com.edisonsanchez.apppruebatecnicanexos.data.showSimpleAlertDialog
import com.edisonsanchez.apppruebatecnicanexos.ui.viewModel.DetailsTransactionActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsTransactionActivity : AppCompatActivity() {

    private val viewModel: DetailsTransactionActivityViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    private lateinit var numberReceipt: TextView
    private lateinit var idTransaction: TextView
    private lateinit var idDevice: TextView
    private lateinit var valueTransaction: TextView
    private lateinit var numberCardTransaction: TextView
    private lateinit var buttonAnnulationTransaction: Button
    private lateinit var backgroundProgress: View
    private lateinit var progressBar: ProgressBar
    private var transaction: Transaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_transaction)
        toolbar = findViewById(R.id.toolBar)
        numberReceipt = findViewById(R.id.numberReceipt)
        idTransaction = findViewById(R.id.idTransaction)
        idDevice = findViewById(R.id.idDevice)
        valueTransaction = findViewById(R.id.valueTransaction)
        numberCardTransaction = findViewById(R.id.numberCardTransaction)
        buttonAnnulationTransaction = findViewById(R.id.buttonAnnulationTransaction)
        backgroundProgress = findViewById(R.id.backgroundProgress)
        progressBar = findViewById(R.id.progressBar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.title = getString(R.string.client_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        transaction = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TRANSACTION, Transaction::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_TRANSACTION)
        }
        buttonAnnulationTransaction.setOnClickListener {
            showProgress()
            transaction?.let { currentTransaction ->
                viewModel.annulationTransaction(numberReceipt.text.toString(),
                    idTransaction.text.toString(), currentTransaction)
            }
        }
        showInfoTransaction()
        configObserves()
    }

    private fun configObserves() {
        val observerAnnulationTransaction = Observer<Boolean> {
            hideProgress()
            if (it) {
                val alertDialog = AlertDialog.Builder(this)
                    .setPositiveButton(getString(R.string.text_button_ok)) { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }
                    .setCancelable(false)
                    .setMessage(getString(R.string.message_annulation_transaction_ok))
                    .create()
                alertDialog.show()
            } else {
                showSimpleAlertDialog(this, getString(R.string.message_annulation_transaction_fail))
            }
        }
        viewModel.canceledTransaction.observe(this, observerAnnulationTransaction)
    }

    private fun showInfoTransaction() {
        transaction?.let {
            numberReceipt.text = it.numberReceipt
            idTransaction.text = it.idTransaction
            idDevice.text = it.idDevice
            valueTransaction.text = it.valueTransaction
            numberCardTransaction.text = it.numberCard
            buttonAnnulationTransaction.isEnabled = !it.isAnnulation
        }
    }

    private fun showProgress() {
        backgroundProgress.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        backgroundProgress.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
}