package com.example.mvi.network

import android.util.Log
import com.example.mvi.network.api.ProductAPI
import com.example.mvi.network.model.Product
import com.example.mvi.network.model.ProductData
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProductApiImpl(
    private val httpClient: HttpClient
): ProductAPI {

    @Throws(Exception::class)
    override suspend fun getProducts() :ProductData{
        return  httpClient.get<ProductData>{
                url("https://dummyjson.com/products")
        }
    }

    override suspend fun createProduct(product: Product): Product? {
        return try {
            httpClient.post<Product>(){
                url("https://dummyjson.com/products")
                contentType(ContentType.Application.Json)
                body = product
            }
        }catch (e: RedirectResponseException){
            Log.d("TAG","Error: ${e.localizedMessage}\n " +
                    "${e.response.status.description}")
            null
        }catch (e: ClientRequestException){
            Log.d("TAGClient","Error: ${e.localizedMessage}\\n "+
                    "\"${e.response.status.description}\"")
            null
        }catch (e: ServerResponseException){
            Log.d("TAGServer","Error: ${e.localizedMessage}" +
                    "\n ${e.response.status.description}")
            null
        }
    }
}