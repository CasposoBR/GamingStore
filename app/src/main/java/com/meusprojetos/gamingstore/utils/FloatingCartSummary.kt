package com.meusprojetos.gamingstore.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meusprojetos.gamingstore.viewmodel.CartViewModel

@Composable
fun FloatingCartSummary(cartViewModel: CartViewModel = viewModel()) {
    val totalPrice by cartViewModel.totalPrice.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    if (cartItems.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Total: R$ %.2f".format(totalPrice),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}