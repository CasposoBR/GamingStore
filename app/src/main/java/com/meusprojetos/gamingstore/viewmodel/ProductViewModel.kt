package com.meusprojetos.gamingstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meusprojetos.gamingstore.remote.ApiService
import com.meusprojetos.gamingstore.remote.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList()) // 🔹 Estado inicial vazio
    val products: StateFlow<List<Product>> = _products

    private val _loadingState = MutableStateFlow<Boolean>(true)  // 🔹 Estado de carregamento
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)  // 🔹 Estado de erro
    val errorState: StateFlow<String?> = _errorState

    init {
        fetchProducts()  // 🔹 Carrega produtos automaticamente ao iniciar
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                _products.value = ApiService.getAllProducts()
                _errorState.value = null  // 🔹 Limpa erro se sucesso
            } catch (e: Exception) {
                _errorState.value = "Erro ao carregar produtos: ${e.localizedMessage}"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun refreshProducts() {
        fetchProducts()  // 🔹 Função para atualizar lista manualmente
    }
}