package com.develop.productlisting.data

data class Value(
    val AllowOutOfStock: Boolean = false,
    val ColorSquaresRgb: String? = null,
    val FullSizePictureUrl: String? = null,
    val Id: Int = 0,
    val IsPreSelected: Boolean = false,
    val Name: String = "",
    val PictureUrl: String? = null,
    val PriceAdjustment: String? = null,
    val PriceAdjustmentValue: Double = 0.0,
    val StockQuanity: Int = 0
)