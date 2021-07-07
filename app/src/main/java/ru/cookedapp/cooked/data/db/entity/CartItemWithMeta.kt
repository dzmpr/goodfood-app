package ru.cookedapp.cooked.data.db.entity

data class CartItemWithMeta(
    val id: Int? = null,
    var isBought: Boolean = false,
    val product: Product,
    var amount: Float,
    val unit: ProductUnit
)
