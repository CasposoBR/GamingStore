package com.meusprojetos.gamingstore.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.meusprojetos.gamingstore.R
import com.meusprojetos.gamingstore.remote.ApiClient
import kotlinx.coroutines.launch

class ProductFragment : Fragment(R.layout.fragment_product) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val products = ApiClient.getProducts()
            println("Produtos recebidos: $products") // 🔹 Exibe os produtos no log
        }
    }
}