package com.example.mvi.network.api

import android.util.Log
import com.example.mvi.network.ProductApiImpl
import com.example.mvi.network.model.Product
import com.example.mvi.network.model.ProductData
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

interface ProductAPI {

    @Throws(Exception::class)
    suspend fun getProducts(): ProductData

    suspend fun createProduct(product: Product): Product?

    companion object{
         val httpClient = HttpClient(Android) {
            install(HttpTimeout){
                socketTimeoutMillis = 20000
                requestTimeoutMillis = 20000
                connectTimeoutMillis = 20000
            }

            install(Logging){
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("TAG", "$message")
                    }
                }
            }

            install(JsonFeature){
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
        fun provideProductApi(): ProductAPI = ProductApiImpl(httpClient)
    }
}