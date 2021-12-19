package ru.cookedapp.cooked.data.db.entity

data class CartItem(
    val id: Long,
    var isBought: Boolean = false,
    val product: ProductEntity,
    var amount: Float,
    val unit: ProductUnitEntity,
)
