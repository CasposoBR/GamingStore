package com.meusprojetos.gamingstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meusprojetos.gamingstore.remote.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    private val _wishlistItems = MutableStateFlow<List<Product>>(emptyList())  // 🔹 Novo estado para wishlist
    val wishlistItems: StateFlow<List<Product>> = _wishlistItems

    fun addToCart(product: Product) {
        viewModelScope.launch {
            _cartItems.value = _cartItems.value + product
            updateTotalPrice()
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            _cartItems.value = _cartItems.value - product
            updateTotalPrice()
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            if (_wishlistItems.value.contains(product)) {
                _wishlistItems.value = _wishlistItems.value - product
            } else {
                _wishlistItems.value = _wishlistItems.value + product
            }
        }
    }

    private fun updateTotalPrice() {
        _totalPrice.value = _cartItems.value.sumOf { it.price }
    }
}