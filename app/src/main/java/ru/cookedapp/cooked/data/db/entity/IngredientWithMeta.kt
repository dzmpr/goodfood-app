package ru.cookedapp.cooked.data.db.entity

data class IngredientWithMeta(
    val product: Product,
    var amount: Float,
    val unit: ProductUnit
)
