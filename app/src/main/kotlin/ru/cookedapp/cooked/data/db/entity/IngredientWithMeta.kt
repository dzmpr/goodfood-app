package ru.cookedapp.cooked.data.db.entity

data class IngredientWithMeta(
    val product: ProductEntity,
    var amount: Float,
    val unit: ProductUnitEntity
)
