package pw.prsk.goodfood.data

import androidx.room.ColumnInfo

data class ProductWithMeta(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "unit_name") val unitName: String
)