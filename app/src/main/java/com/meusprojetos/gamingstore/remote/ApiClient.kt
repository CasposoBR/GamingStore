package com.meusprojetos.gamingstore.remote

import io.ktor.client.plugins.auth.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUri: String,
    val price: Double,
    val stock: Int,
    val isOnPromotion: Boolean,
    val promotionPrice: Double?,
    val brand: String,
    val available: Boolean
)

object ApiClient {
    private var accessToken: String? = null  // 🔹 Guarda o token JWT obtido

    private val client = HttpClient {
        install(Auth) {
            bearer {
                loadTokens {
                    accessToken?.let { BearerTokens(it, "") } ?: BearerTokens("", "")
                }
            }
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private const val BASE_URL = "http://10.0.2.2:8080"

    suspend fun getProducts(): List<Product> {
        return try {
            client.get("$BASE_URL/products").body<List<Product>>()  // 🔹 Corrigido!
        } catch (e: Exception) {
            println("Erro ao buscar produtos: ${e.localizedMessage}")
            emptyList()
        }
    }
}