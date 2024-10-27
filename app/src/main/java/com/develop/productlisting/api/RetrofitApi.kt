package com.develop.productlisting.api

import okhttp3.OkHttpClient
import okio.Timeout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitApi {
//    https://baseball-test.shopfast.com/api/v1/catalog/products/app-products
    const val BASE_URL = "https://baseball-test.shopfast.com"
    fun getRetroInstance(url : String) : ProductApi {

        val client = OkHttpClient.Builder()
            .callTimeout(60000,TimeUnit.MILLISECONDS)
            .readTimeout(60000,TimeUnit.MILLISECONDS)
            .writeTimeout(60000,TimeUnit.MILLISECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ProductApi::class.java)
    }
}