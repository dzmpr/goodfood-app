package pw.prsk.goodfood.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.local.db.AppDatabase
import pw.prsk.goodfood.data.local.db.entity.RecipeCategory

class RecipeCategoryRepository(private val dbInstance: AppDatabase) {
    suspend fun getCategories(): List<RecipeCategory> = withContext(Dispatchers.IO) {
        dbInstance.recipeCategoryDao().getAll()
    }
}