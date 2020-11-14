package pw.prsk.goodfood.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.data.Meal

class MealRepository(private val dbInstance: AppDatabase) {
    suspend fun addMeal(meal: Meal) = withContext(Dispatchers.IO) {
        dbInstance.mealDao().add(meal)
    }

    suspend fun getMeals(): List<Meal> = withContext(Dispatchers.IO) {
        dbInstance.mealDao().getAll()
    }
}