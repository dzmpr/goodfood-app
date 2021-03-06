package pw.prsk.goodfood.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.*

class MealRepository(
    private val dbInstance: AppDatabase,
    private val photoGateway: PhotoGateway
) {
    suspend fun addMeal(meal: Meal) = withContext(Dispatchers.IO) {
        dbInstance.mealDao().insert(meal)
    }

    suspend fun getMeals(): List<Meal> = withContext(Dispatchers.IO) {
        dbInstance.mealDao().getAll()
    }

    suspend fun removeMeal(meal: Meal) = withContext(Dispatchers.IO) {
        removeRecipeById(meal.id!!)
    }

    suspend fun removeRecipeById(id: Int) = withContext(Dispatchers.IO) {
        val recipe = dbInstance.mealDao().getById(id)
        removeIngredients(recipe.ingredientsList)
        removeCategory(recipe.category_id)
        if (recipe.photoFilename != null) {
            val uri = photoGateway.getUriForPhoto(recipe.photoFilename!!)
            photoGateway.removePhoto(uri)
        }
        dbInstance.mealDao().deleteById(recipe.id!!)
    }

    private fun removeIngredients(ingredients: List<Ingredient>) {
        ingredients.forEach {
            // Get product for each ingredient
            val product = dbInstance.productDao().getById(it.productId)
            // If this is the only recipe where this product is used - delete it
            if (product.referenceCount == 1) {
                dbInstance.productDao().delete(product)
            } else {
                dbInstance.productDao().decreaseUsages(it.productId)
            }
        }
    }

    private fun removeCategory(category_id: Int?) {
        if (category_id != null) {
            val category = dbInstance.mealCategoryDao().getById(category_id)
            // If this is the only recipe where this category is used - delete it
            if (category.referenceCount == 1) {
                dbInstance.mealCategoryDao().delete(category)
            } else {
                dbInstance.mealCategoryDao().decreaseUsages(category_id)
            }
        }
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