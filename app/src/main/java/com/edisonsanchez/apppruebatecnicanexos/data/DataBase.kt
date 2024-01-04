package com.edisonsanchez.apppruebatecnicanexos.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Transaction::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {

    abstract fun transactionDao() : TransactionsDao
}