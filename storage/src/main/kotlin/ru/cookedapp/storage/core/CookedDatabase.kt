package ru.cookedapp.storage.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.cookedapp.storage.dao.CartDao
import ru.cookedapp.storage.dao.ProductDao
import ru.cookedapp.storage.dao.ProductUnitDao
import ru.cookedapp.storage.dao.RecipeCategoryDao
import ru.cookedapp.storage.dao.RecipeDao
import ru.cookedapp.storage.entity.CartItemEntity
import ru.cookedapp.storage.entity.ProductEntity
import ru.cookedapp.storage.entity.ProductUnitEntity
import ru.cookedapp.storage.entity.RecipeCategoryEntity
import ru.cookedapp.storage.entity.RecipeEntity

@Database(
    version = Config.VERSION,
    entities = [
        RecipeEntity::class,
        ProductEntity::class,
        RecipeCategoryEntity::class,
        ProductUnitEntity::class,
        CartItemEntity::class,
    ],
    exportSchema = true,
)
@TypeConverters(Converters::class)
internal abstract class CookedDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productUnitDao(): ProductUnitDao

    abstract fun recipeDao(): RecipeDao
    abstract fun recipeCategoryDao(): RecipeCategoryDao

    abstract fun cartDao(): CartDao
}
