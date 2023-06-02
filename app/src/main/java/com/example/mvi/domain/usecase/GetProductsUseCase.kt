package com.example.mvi.domain.usecase

import android.util.Log
import com.example.mvi.presentation.DataState
import com.example.mvi.presentation.UiComponent
import com.example.mvi.network.api.ProductAPI
import com.example.mvi.network.model.Product
import com.example.mvi.network.model.ProductData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductsUseCase(
    private val productAPI: ProductAPI
) {

    fun execute(): Flow<DataState<ProductData>>{
        return flow {
            emit(DataState.Loading(true))
            try {
                val product = productAPI.getProducts()
                emit(DataState.Success(product))
            }catch (e: Exception){
                e.printStackTrace()
                emit(DataState.Error(UiComponent.Toast(e.localizedMessage ?: "Something went wrong")))
            }finally {
                emit(DataState.Loading(false))
            }
        }
    }
}