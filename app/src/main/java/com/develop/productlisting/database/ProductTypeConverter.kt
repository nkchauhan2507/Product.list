package com.develop.productlisting.database

import androidx.room.TypeConverter
import com.develop.productlisting.data.ProductAttribute
import com.develop.productlisting.data.ProductFullSizePicture
import com.develop.productlisting.data.ProductPicture
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromProductAttributeList(value: List<ProductAttribute>?): String {
        return gson.toJson(value ?: listOf<ProductAttribute>())
//        return if(value==null){
//            ""
//        }else{
//            gson.toJson(value)
//        }
    }

    @TypeConverter
    fun toProductAttributeList(value: String): List<ProductAttribute>? {
        return if (value.isEmpty()) null else gson.fromJson(value, object : TypeToken<List<ProductAttribute>>() {}.type)
//        return if(value.isEmpty()){
//            null
//        }else{
//            val listType = object : TypeToken<List<ProductAttribute>>() {}.type
//            gson.fromJson(value, listType)
//        }
    }

    @TypeConverter
    fun fromProductFullSizePictureList(value: List<ProductFullSizePicture>?): String {
        return gson.toJson(value ?: listOf<ProductFullSizePicture>())

//        return if(value==null){
//            ""
//        }else{
//            gson.toJson(value)
//        }
    }

    @TypeConverter
    fun toProductFullSizePictureList(value: String): List<ProductFullSizePicture>? {
        return if (value.isEmpty()) null else gson.fromJson(value, object : TypeToken<List<ProductFullSizePicture>>() {}.type)
//        return if(value.isEmpty()){
//            null
//        }else{
//            val listType = object : TypeToken<List<ProductFullSizePicture>>() {}.type
//            return gson.fromJson(value, listType)
//        }

    }

    @TypeConverter
    fun fromProductPictureList(value: List<ProductPicture>?): String {
        return gson.toJson(value ?: listOf<ProductPicture>())

//        return if(value==null){
//            ""
//        }else{
//            gson.toJson(value)
//        }
    }

    @TypeConverter
    fun toProductPictureList(value: String): List<ProductPicture>? {
        return if (value.isEmpty()) null else gson.fromJson(value, object : TypeToken<List<ProductPicture>>() {}.type)
//        return if(value.isEmpty()){
//            null
//        }else{
//            val listType = object : TypeToken<List<ProductPicture>>() {}.type
//            return gson.fromJson(value, listType)
//        }
    }

    // New converters for ProductSpecificationAttributes
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value ?: listOf<String>())
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        return if (value.isEmpty()) null else gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }
}