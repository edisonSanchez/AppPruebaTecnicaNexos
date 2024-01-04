package com.edisonsanchez.apppruebatecnicanexos.data

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.parcelize.Parcelize


@Entity(NAME_TABLE_TRANSACTIONS)
@Parcelize
data class Transaction (
    @PrimaryKey
    val idTransaction: String,
    val numberReceipt: String,
    val idDevice: String,
    val commerceCode: String,
    val valueTransaction: String,
    val numberCard: String,
    var isAnnulation: Boolean
) : Parcelable

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM Transactions")
    fun getAllApprovedTransactions() : List<Transaction>

    @Query("SELECT * FROM transactions WHERE numberReceipt = :numberReceipt")
    fun searchTransaction(numberReceipt: String) : Transaction

    @Insert
    fun insertTransaction(transaction: Transaction)

    @Update
    fun update(transaction: Transaction)

}
