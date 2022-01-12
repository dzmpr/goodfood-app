package ru.cookedapp.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_units")
data class ProductUnitEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    @ColumnInfo(name = "name")
    val name: String,
) : Identifiable {

    constructor(name: String) : this(id = 0, name = name)

    override fun toString() = name
}
