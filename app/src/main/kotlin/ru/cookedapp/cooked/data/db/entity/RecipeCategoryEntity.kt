package ru.cookedapp.cooked.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_categories")
data class RecipeCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "reference_count") var referenceCount: Int = 0,
) : Identifiable
