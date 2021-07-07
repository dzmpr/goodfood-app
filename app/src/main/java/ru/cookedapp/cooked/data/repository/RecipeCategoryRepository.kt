package ru.cookedapp.cooked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.data.local.db.AppDatabase
import ru.cookedapp.cooked.data.local.db.entity.RecipeCategory

class RecipeCategoryRepository(private val dbInstance: AppDatabase) {
    suspend fun getCategories(): List<RecipeCategory> = withContext(Dispatchers.IO) {
        dbInstance.recipeCategoryDao().getAll()
    }

    suspend fun getCategoriesFlow() = withContext(Dispatchers.IO) {
        dbInstance.recipeCategoryDao().getCategoriesFlow()
    }

    suspend fun renameCategory(id: Int, categoryName: String) = withContext(Dispatchers.IO) {
        dbInstance.recipeCategoryDao().renameCategory(id, categoryName)
    }
}
