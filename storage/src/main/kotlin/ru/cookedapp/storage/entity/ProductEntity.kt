package ru.cookedapp.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "reference_count") var referenceCount: Int = 0,
) : Identifiable
