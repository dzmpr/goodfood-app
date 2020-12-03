package pw.prsk.goodfood.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name="name") var name: String,
    @ColumnInfo(name="unitId") var unitId: Int,
    @ColumnInfo(name="categoryId") var categoryId: Int
)