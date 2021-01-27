package pw.prsk.goodfood.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = ProductCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        ),
        ForeignKey(
            entity = ProductUnit::class,
            parentColumns = ["id"],
            childColumns = ["unitId"]
        )
    ]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "unitId", index = true) var unitId: Int,
    @ColumnInfo(name = "categoryId", index = true) var categoryId: Int
)