package com.example.fintech.ui.fragment.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransferViewModel : ViewModel() {

    private val _selectedBank = MutableLiveData<String>()
    val selectedBank: LiveData<String> get() = _selectedBank

    private val _selectedAccount = MutableLiveData<String>()
    val selectedAccount: LiveData<String> get() = _selectedAccount

    private val _accountNumber = MutableLiveData<String>()
    val accountNumber: LiveData<String> get() = _accountNumber

    private val _amount = MutableLiveData<Double>()
    val amount: LiveData<Double> get() = _amount

    fun setSelectedBank(bank: String) {
        _selectedBank.value = bank
    }

    fun setSelectedAccount(account: String) {
        _selectedAccount.value = account
    }

    fun setAccountNumber(accountNumber: String) {
        _accountNumber.value = accountNumber
    }

    fun setAmount(amount: Double) {
        _amount.value = amount
    }
}
