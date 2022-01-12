package ru.cookedapp.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_units")
data class ProductUnitEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    @ColumnInfo(name = "name") var name: String,
) : Identifiable {
    override fun toString() = name
}
