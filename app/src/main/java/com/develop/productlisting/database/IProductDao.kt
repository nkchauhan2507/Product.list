package com.develop.productlisting.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.develop.productlisting.data.Product

@Dao
interface IProductDao {

    @Query("SELECT * FROM products ORDER BY Id ASC LIMIT :limit OFFSET :offset")
    fun getPagedList(limit: Int, offset: Int): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoDB(product: List<Product>)

    @Query("SELECT * FROM products ORDER By Id ASC")
    fun getAllProductExceptLive() : List<Product>


    // Filtered Queries...
    @Query("SELECT * FROM products ORDER BY Price Desc LIMIT :limit OFFSET :offset")
    fun getProductByPriceHighToLow(limit: Int, offset: Int) : List<Product>

    @Query("SELECT * FROM products ORDER BY Price Asc LIMIT :limit OFFSET :offset")
    fun getProductByPriceLowToHigh(limit: Int, offset: Int) : List<Product>


    @Query("SELECT * FROM products ORDER BY Name Asc LIMIT :limit OFFSET :offset")
    fun getProductByNameAtoZ(limit: Int, offset: Int) : List<Product>

    @Query("SELECT * FROM products ORDER BY Name Desc LIMIT :limit OFFSET :offset")
    fun getProductByNameZtoA(limit: Int, offset: Int) : List<Product>


    @Query("SELECT COUNT(*) FROM products")
    fun getItemCount(): Int
}