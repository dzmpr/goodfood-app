package pw.prsk.goodfood.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.data.MealCategory

class MealCategoryRepository(private val dbInstance: AppDatabase) {
    suspend fun getCategories(): List<MealCategory> = withContext(Dispatchers.IO) {
        dbInstance.mealCategoryDao().getAll()
    }
}