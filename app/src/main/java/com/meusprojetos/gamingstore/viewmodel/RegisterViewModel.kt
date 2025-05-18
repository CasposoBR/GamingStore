package com.meusprojetos.gamingstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meusprojetos.gamingstore.remote.ApiService
import com.meusprojetos.gamingstore.remote.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registrationState = MutableStateFlow<String?>(null)
    val registrationState: StateFlow<String?> = _registrationState

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val newUser = User(id = "", name = name, email = email, password = password, createdAt = "")
                ApiService.registerUser(newUser)
                _registrationState.value = "success"  // 🔹 Indica que o cadastro foi bem-sucedido
            } catch (e: Exception) {
                _registrationState.value = "error"  // 🔹 Indica que houve falha no cadastro
            }
        }
    }
}