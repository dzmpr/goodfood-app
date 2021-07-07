package ru.cookedapp.cooked.data.local.db.entity

data class IngredientWithMeta(
    val product: Product,
    var amount: Float,
    val unit: ProductUnit
)
