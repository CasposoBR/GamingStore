package com.meusprojetos.gamingstore.domain

import com.meusprojetos.gamingstore.remote.ApiClient
import com.meusprojetos.gamingstore.remote.Product

//REVER DUVIDA: IMPORTO O PRODUCT DO LOCAL OU DO REMOTE???
class Repository(private val productDao: ProductDao) {
    suspend fun getProducts(): List<Product> {
        val products = ApiClient.getProducts()
        productDao.insertAll(products) // 🔹 Salva no banco local
        return products
    }
}