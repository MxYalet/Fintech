package com.example.fintech.data.repository

import androidx.lifecycle.LiveData
import com.example.fintech.data.repository.local.Account
import com.example.fintech.data.repository.local.AccountDao

class AccountRepository(private val accountDao: AccountDao) {
    val allAccounts: LiveData<List<android.accounts.Account>> = accountDao.getAllAccounts()

    suspend fun insert(account: Account) {
        accountDao.insertAccount(account)
    }

    suspend fun update(account: Account) {
        accountDao.updateAccount(account)
    }
}


