package com.example.fintech.data.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransferRepository {

    suspend fun makeTransfer(
        bank: String,
        transferFrom: String,
        accountNumber: String,
        amount: Double
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                // Simulate network call
                // Replace this with actual transfer logic
                if (amount > 0) {
                    Result.success(Unit)
                } else {
                    throw IllegalArgumentException("Invalid amount")
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
