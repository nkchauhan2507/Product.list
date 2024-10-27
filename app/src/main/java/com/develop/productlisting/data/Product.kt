package com.develop.productlisting.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.develop.productlisting.database.ProductTypeConverter

@Entity(tableName = "products")
data class Product(
    var CustomerEntersPrice: Boolean = false,
    var DiscountAmount: Double = 0.0,
    var DiscountPercentage: Double = 0.0,
    var DisplayOrder: Int = 0,
    var DisplayStockAvailability: Boolean = false,
    var DisplayStockQuantity: Boolean = false,
    var FullDescription: String? = null,
    var Gtin: String? = null,

    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0,
    var IsGiftCard: Boolean = false,
    var ManageInventoryMethodId: Double = 0.0,
    var MaximumCustomerEnteredPrice: Double = 0.0,
    var MinimumCustomerEnteredPrice: Double = 0.0,
    var Name: String = "",
    var Price: Double = 0.0,

    @TypeConverters(ProductTypeConverter::class) // Ensure type converters are applied
    var ProductAttributes: List<ProductAttribute> = listOf(),
    var ProductCategories: String = "",

    @TypeConverters(ProductTypeConverter::class)
    var ProductFullSizePictures: List<ProductFullSizePicture> = listOf(),

    @TypeConverters(ProductTypeConverter::class)
    var ProductPictures: List<ProductPicture> = listOf(),

    @TypeConverters(ProductTypeConverter::class)
    var ProductSpecificationAttributes: List<String> = listOf(),

    var Published: Boolean = false,
    var ShortDescription: String? = null,
    var ShowOnKiosk: Boolean = false,
    var ShowOnPosMobile: Boolean = false,
    var ShowOnPosWeb: Boolean = false,
    var ShowOnWebsite: Boolean = false,
    var Sku: String? = null,
    var StockQuantity: Int = 0,
    var TaxRate: Double = 0.0,
    var UsePercentage: Boolean = false
)