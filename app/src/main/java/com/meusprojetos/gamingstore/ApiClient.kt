package com.meusprojetos.gamingstore

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
    val price: Double
)

object ApiClient {
    private val client = HttpClient {
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens("SEU_TOKEN_JWT_AQUI", "SEU_REFRESH_TOKEN_AQUI")
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
            client.get("$BASE_URL/products")
                .body() //
        } catch (e: Exception) {
            println("Erro ao buscar produtos: ${e.localizedMessage}")
            emptyList()
        }
    }
}