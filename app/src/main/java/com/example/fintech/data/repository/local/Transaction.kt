package com.example.fintech.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sourceAccountId: String,
    val destinationAccountId: String,
    val amount: Double,
    val timestamp: Long = System.currentTimeMillis()
)