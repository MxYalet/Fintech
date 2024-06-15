package com.example.fintech.ui.fragment.auth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintech.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: AuthRepository) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val name = MutableLiveData<String>()

    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> get() = _signUpResult

    private val _signUpError = MutableLiveData<String?>()
    val signUpError: LiveData<String?> get() = _signUpError

    fun signUpUser() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            _signUpError.value = "Email and password are required"
            return
        }

        viewModelScope.launch {
            val result = userRepository.signUpUser(emailValue, passwordValue)
            if (result.isSuccess) {
                _signUpResult.value = true
            } else {
                _signUpResult.value = false
                _signUpError.value = when (result.exceptionOrNull()?.message) {
                    "The email address is already in use by another account." -> "Email address already registered"
                    "The email address is badly formatted." -> "Invalid email address format"
                    else -> "Sign-up failed"
                }
            }
        }
    }

    fun resetError() {
        _signUpError.value = null
    }
}
