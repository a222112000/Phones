package com.example.mvi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi.network.api.ProductAPI
import com.example.mvi.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class ProductViewModel: ViewModel(), ContainerHost<ProductState, UiComponent> {

    private val getProducts = GetProductsUseCase(ProductAPI.provideProductApi())

    override val container: Container<ProductState, UiComponent> = container(ProductState())

    init {
        getAllProducts()
    }

    fun getAllProducts(){
        intent {
            val products = getProducts.execute()
            products.onEach {
                when(it){
                    is DataState.Loading->{
                        reduce { state.copy(progressBar = it.isLoading) }
                    }
                    is DataState.Success->{
                        reduce {
                            state.copy(products = it.data)
                        }
                    }
                    is DataState.Error->{
                        when(it.uiComponent){
                            is UiComponent.Toast->{
                                postSideEffect(UiComponent.Toast(it.uiComponent.text))
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}