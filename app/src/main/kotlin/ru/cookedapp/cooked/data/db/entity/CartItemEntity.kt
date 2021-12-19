package ru.cookedapp.cooked.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
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
    @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "is_bought") var isBought: Boolean = false,
    @ColumnInfo(name = "product_id") var productId: Long,
    @ColumnInfo(name = "amount") var amount: Float,
    @ColumnInfo(name = "unit_id") var unitId: Long,
) : Identifiable
