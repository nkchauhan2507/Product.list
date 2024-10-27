package com.develop.productlisting.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.develop.productlisting.data.Product

@Database(entities = [Product::class], version = 2)
@TypeConverters(ProductTypeConverter::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao() : IProductDao

    companion object {
        private var INSTANCE : ProductDatabase? = null

        fun getDatabase(context: Context) : ProductDatabase {
            return INSTANCE ?:  synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    ProductDatabase::class.java,
                    "product_database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

}