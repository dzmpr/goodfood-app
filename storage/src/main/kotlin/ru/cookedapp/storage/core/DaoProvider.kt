package ru.cookedapp.storage.core

import android.content.Context
import javax.inject.Inject
import ru.cookedapp.storage.dao.CartDao
import ru.cookedapp.storage.dao.ProductDao
import ru.cookedapp.storage.dao.ProductUnitDao
import ru.cookedapp.storage.dao.RecipeCategoryDao
import ru.cookedapp.storage.dao.RecipeDao

internal class DaoProvider @Inject constructor(
    private val databaseProvider: CookedDatabaseProvider,
) {

    fun getRecipeDao(context: Context): RecipeDao =
        databaseProvider.getDatabase(context).recipeDao()

    fun getCartDao(context: Context): CartDao =
        databaseProvider.getDatabase(context).cartDao()

    fun getRecipeCategoryDao(context: Context): RecipeCategoryDao =
        databaseProvider.getDatabase(context).recipeCategoryDao()

    fun getProductUnitDao(context: Context): ProductUnitDao =
        databaseProvider.getDatabase(context).productUnitDao()

    fun getProductDao(context: Context): ProductDao =
        databaseProvider.getDatabase(context).productDao()
}
