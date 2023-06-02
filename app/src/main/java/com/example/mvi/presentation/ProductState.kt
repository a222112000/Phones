package com.example.mvi.presentation

import com.example.mvi.network.model.Product
import com.example.mvi.network.model.ProductData

data class ProductState(val progressBar: Boolean = false,
                        val products: ProductData? = null,

                        )
