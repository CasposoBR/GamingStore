package com.meusprojetos.gamingstore.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meusprojetos.gamingstore.viewmodel.CartViewModel
import com.meusprojetos.gamingstore.viewmodel.ProductViewModel

@Composable
fun ProductFragment(
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {

    val products by productViewModel.products.collectAsState()
    val isLoading by productViewModel.loadingState.collectAsState()
    val errorMessage by productViewModel.errorState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Produtos", style = MaterialTheme.typography.headlineMedium)

        if (isLoading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onClick = { /* Ação ao clicar */ },
                        onToggleFavorite = { /* Alternar favorito */ },
                        onAddToCart = { cartViewModel.addToCart(it) }
                    )
                }
            }
        }
    }
}