package ru.cookedapp.cooked.data.db.entity

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    var productId: Int,
    var amount: Float,
    var amountUnitId: Int
)
