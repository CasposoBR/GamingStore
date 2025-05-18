package com.meusprojetos.gamingstore.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object ApiService {
    private var accessToken: String? = null  //

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

    fun setAccessToken(token: String) {
        accessToken = token  //
    }

    suspend fun getSessionState(userId: String): String {
        val response = client.get("$BASE_URL/app/session?userId=$userId").body<Map<String, Any>>()
        return response["screen"] as String
    }

    suspend fun getAllProducts(): List<Product> {
        return client.get("$BASE_URL/products").body()
    }

    suspend fun getProductById(id: String): Product? {
        return client.get("$BASE_URL/products/$id").body()
    }

    suspend fun createProduct(product: Product) {
        client.post("$BASE_URL/products") {
            setBody(product)
        }
    }

    suspend fun updateProduct(id: String, product: Product) {
        client.put("$BASE_URL/products/$id") {
            setBody(product)
        }
    }

    suspend fun deleteProduct(id: String) {
        client.delete("$BASE_URL/products/$id")
    }

    suspend fun loginUser(email: String, password: String): LoginResponse? {
        return try {
            client.post("$BASE_URL/users/login") {
                setBody(mapOf("email" to email, "password" to password))
            }.body<LoginResponse>()
        } catch (e: Exception) {
            println("Erro ao logar: ${e.localizedMessage}")
            null
        }
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val response = client.post("$BASE_URL/users/login") {
            setBody(mapOf("email" to email, "password" to password))
        }.body<LoginResponse>()

        setAccessToken(response.token)

        return response
    }

    suspend fun registerUser(user: User) {
        try {
            client.post("$BASE_URL/users/register") {
                setBody(user)
            }
        } catch (e: Exception) {
            println("Erro ao cadastrar usuário: ${e.localizedMessage}")
        }
    }
}

@Serializable
data class LoginResponse(
    val token: String
)