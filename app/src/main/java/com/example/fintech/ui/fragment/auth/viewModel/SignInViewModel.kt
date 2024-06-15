package com.example.fintech.ui.fragment.auth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintech.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> get() = _loginError

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signInUser(email, password)
            if (result.isSuccess) {
                _loginResult.value = true
            } else {
                _loginResult.value = false
                _loginError.value = when (result.exceptionOrNull()?.message) {
                    "There is no user record corresponding to this identifier. The user may have been deleted." -> "Email address not registered"
                    "The password is invalid or the user does not have a password." -> "Incorrect password"
                    "The email address is badly formatted." -> "Invalid email address format"
                    else -> "Authentication failed"
                }
            }
        }
    }

    fun resetError() {
        _loginError.value = null
    }
}
