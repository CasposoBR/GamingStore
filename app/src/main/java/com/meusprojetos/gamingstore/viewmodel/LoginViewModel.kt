package com.meusprojetos.gamingstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meusprojetos.gamingstore.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = ApiService.loginUser(email, password)
                if (response != null) {
                    ApiService.setAccessToken(response.token)  // 🔹 Armazena o token JWT
                    _loginState.value = "success"
                } else {
                    _loginState.value = "error"
                }
            } catch (e: Exception) {
                _loginState.value = "error"
            }
        }
    }
}