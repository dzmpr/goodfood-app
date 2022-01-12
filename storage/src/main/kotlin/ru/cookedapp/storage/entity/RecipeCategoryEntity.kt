package ru.cookedapp.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_categories")
data class RecipeCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "reference_count")
    val referenceCount: Int,
) : Identifiable {

    constructor(
        name: String,
        referenceCount: Int = 0,
    ) : this(
        id = 0,
        name = name,
        referenceCount = referenceCount,
    )
}
