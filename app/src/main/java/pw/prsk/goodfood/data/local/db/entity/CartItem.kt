package pw.prsk.goodfood.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart",
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("product_id"),
        onDelete = CASCADE
    )]
)
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "is_bought") var isBought: Boolean = false,
    @ColumnInfo(name = "product_id") var productId: Int,
    @ColumnInfo(name = "amount") var amount: Float,
    @ColumnInfo(name = "unit_id") var unitId: Int
)
