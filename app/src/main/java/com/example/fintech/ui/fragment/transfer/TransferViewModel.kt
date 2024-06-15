package com.example.fintech.ui.fragment.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintech.data.repository.TransferRepository
import kotlinx.coroutines.launch

class TransferViewModel(private val transferRepository: TransferRepository) : ViewModel() {

    private val _transferResult = MutableLiveData<Boolean>()
    val transferResult: LiveData<Boolean> get() = _transferResult

    private val _transferError = MutableLiveData<String?>()
    val transferError: LiveData<String?> get() = _transferError

    fun makeTransfer(bank: String, transferFrom: String, accountNumber: String, amount: Double) {
        viewModelScope.launch {
            val result = transferRepository.makeTransfer(bank, transferFrom, accountNumber, amount)
            if (result.isSuccess) {
                _transferResult.value = true
            } else {
                _transferResult.value = false
                _transferError.value = result.exceptionOrNull()?.message ?: "Transfer failed"
            }
        }
    }

    fun resetError() {
        _transferError.value = null
    }
}
