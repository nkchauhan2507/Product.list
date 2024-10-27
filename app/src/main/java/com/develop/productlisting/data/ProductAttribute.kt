package com.develop.productlisting.data

data class ProductAttribute(
    val AllowedFileExtensions: String? = null,
    val AttributeControlType: String = "",
    val AttributeControlTypeId: Int = 0,
    val DefaultValue: String? = null,
    val Description: String? = null,
    val DisplayOrder: Int = 0,
    val Id: Int = 0,
    val IsRequired: Boolean = false,
    val Name: String = "",
    val ProductAttributeId: Int = 0,
    val TextPrompt: String? = null,
    val Values: List<Value> = listOf()
)