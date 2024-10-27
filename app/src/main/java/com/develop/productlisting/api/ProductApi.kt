package com.develop.productlisting.api

import com.develop.productlisting.data.Product
import retrofit2.http.GET

interface ProductApi {

    @GET("catalog/products/app-products")
    suspend fun fetchProduct() : List<Product>
}