package com.example.mvi.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductData(

    @SerialName("products")
    val products: List<Product>,
)

//object  ProductListSerializer  :  JsonTransformingSerializer<List<Product>>( ListSerializer ( Product.serializer ()))