package pw.prsk.goodfood.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.*

class MealRepository(private val dbInstance: AppDatabase) {
    suspend fun addMeal(meal: Meal) = withContext(Dispatchers.IO) {
        dbInstance.mealDao().insert(meal)
    }

    suspend fun getMeals(): List<Meal> = withContext(Dispatchers.IO) {
        dbInstance.mealDao().getAll()
    }

    suspend fun removeMeal(meal: Meal) = withContext(Dispatchers.IO) {
        dbInstance.mealDao().delete(meal)
    }

    suspend fun addRecipe(recipe: MealWithMeta) = withContext(Dispatchers.IO) {
        val convertedIngredients = processIngredients(recipe.ingredientsList)
        processCategory(recipe.category)
        dbInstance.mealDao().insert(
            Meal(
                recipe.id,
                recipe.name,
                recipe.description,
                recipe.photoFilename,
                recipe.servingsNum,
                recipe.inFavorites,
                recipe.last_eaten,
                recipe.eat_count,
                convertedIngredients,
                recipe.category?.id
            )
        )
    }

    private fun processIngredients(ingredients: List<IngredientWithMeta>): List<Ingredient> {
        for (ingredient in ingredients) {
            handleIngredient(ingredient)
        }
        return ingredients.map {
            Ingredient(it.product.id!!, it.amount, it.unit.id!!)
        }
    }

    private fun handleIngredient(ingredient: IngredientWithMeta) {
        // Create product if it is not exists
        if (ingredient.product.id == null) {
            val newId = dbInstance.productDao().insert(ingredient.product)
            ingredient.product.id = newId.toInt()
        }
        // Increase product usage count
        dbInstance.productDao().increaseUsages(ingredient.product.id!!)
    }

    private fun processCategory(category: MealCategory?) {
        if (category != null) {
            // Create category if it is not exists
            if (category.id == null) {
                val newId = dbInstance.mealCategoryDao().insert(category)
                category.id = newId.toInt()
            }
            // Increase category usages
            dbInstance.mealCategoryDao().increaseUsages(category.id!!)
        }
    }
}