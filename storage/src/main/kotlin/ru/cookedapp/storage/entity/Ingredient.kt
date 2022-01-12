package ru.cookedapp.storage.entity

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    var productId: Long,
    var amount: Float,
    var amountUnitId: Long,
)
