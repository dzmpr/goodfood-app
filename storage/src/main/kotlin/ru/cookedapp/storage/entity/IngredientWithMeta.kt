package ru.cookedapp.storage.entity

data class IngredientWithMeta(
    val product: ProductEntity,
    var amount: Float,
    val unit: ProductUnitEntity
)
