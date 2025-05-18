package com.meusprojetos.gamingstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.meusprojetos.gamingstore.remote.Product
import com.meusprojetos.gamingstore.utils.ProductCard
import com.meusprojetos.gamingstore.viewmodel.CartViewModel
import com.meusprojetos.gamingstore.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {

    val products by homeViewModel.products.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Gaming Store", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onClick = { /* Exibir detalhes do produto */ },
                    onToggleFavorite = { /* Adicionar à wishlist */ },
                    onAddToCart = { cartViewModel.addToCart(it) }
                )

                Button(
                    onClick = { cartViewModel.addToCart(product) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Adicionar ao Carrinho")
                }
            }
        }

        FloatingCartSummary(cartItems, totalPrice)
    }
}
@Composable
fun FloatingCartSummary(
    cartItems: List<Product>,
    totalPrice: Double,
    cartViewModel: CartViewModel = viewModel()

) {
    if (cartItems.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp).background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Total: R$ %.2f".format(totalPrice),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    @Composable
    fun ProductListScreen(products: List<Product>) {
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