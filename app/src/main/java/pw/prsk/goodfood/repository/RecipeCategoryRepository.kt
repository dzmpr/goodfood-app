package pw.prsk.goodfood.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.data.RecipeCategory

class RecipeCategoryRepository(private val dbInstance: AppDatabase) {
    suspend fun getCategories(): List<RecipeCategory> = withContext(Dispatchers.IO) {
        dbInstance.recipeCategoryDao().getAll()
    }
}