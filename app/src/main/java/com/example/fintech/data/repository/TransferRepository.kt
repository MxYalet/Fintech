package com.example.fintech.data.repository


import androidx.lifecycle.LiveData
import com.example.fintech.data.repository.local.Transaction
import com.example.fintech.data.repository.local.TransactionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }
}