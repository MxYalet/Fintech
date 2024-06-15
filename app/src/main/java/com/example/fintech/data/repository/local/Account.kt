package com.example.fintech.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey val id: String,
    val name: String,
    val balance: Double
)