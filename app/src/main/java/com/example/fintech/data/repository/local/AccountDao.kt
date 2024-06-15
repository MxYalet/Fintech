package com.example.fintech.data.repository.local

import android.accounts.Account
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// AccountDao.kt
@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: com.example.fintech.data.repository.local.Account)

    @Update
    suspend fun updateAccount(account: com.example.fintech.data.repository.local.Account)
}

// TransactionDao.kt

