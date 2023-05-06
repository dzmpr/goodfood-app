package ru.cookedapp.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("product_id"),
            onDelete = CASCADE
        ),
    ]
)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Long,

    @ColumnInfo(name = "is_bought")
    val isBought: Boolean,

    @ColumnInfo(name = "product_id")
    val productId: Long,

    @ColumnInfo(name = "amount")
    val amount: Float,

    @ColumnInfo(name = "unit_id")
    val unitId: Long,
) : Identifiable {

    constructor(
        isBought: Boolean = false,
        productId: Long,
        amount: Float,
        unitId: Long,
    ) : this(
        id = 0,
        isBought = isBought,
        productId = productId,
        amount = amount,
        unitId = unitId,
    )
}
