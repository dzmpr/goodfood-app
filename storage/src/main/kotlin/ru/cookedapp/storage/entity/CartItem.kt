package ru.cookedapp.storage.entity

data class CartItem(
    val id: Long,
    var isBought: Boolean = false,
    val product: ProductEntity,
    var amount: Float,
    val unit: ProductUnitEntity,
)
