package ru.cookedapp.cooked.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_categories")
data class RecipeCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "reference_count") var referenceCount: Int = 0
)