package ru.cookedapp.cooked.data.repository

import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.data.db.AppDatabase
import ru.cookedapp.cooked.data.db.entity.Ingredient
import ru.cookedapp.cooked.data.db.entity.IngredientWithMeta
import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity
import ru.cookedapp.cooked.data.db.entity.RecipeEntity
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.data.prefs.RecipePreferences

class RecipeRepository(
    private val dbInstance: AppDatabase,
    private val photoGateway: PhotoGateway,
    private val recipePreferences: RecipePreferences
) {
    suspend fun markAsCooked(id: Int, date: LocalDateTime) = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().markAsCooked(id, date)
    }

    suspend fun changeFavoritesMark(id: Int, state: Boolean) = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().changeFavoriteMark(id, state)
    }

    suspend fun isDatabaseEmpty() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().isDatabaseEmpty().map { it != 0 }
    }

    suspend fun getAllRecipes() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getAllRecipes()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFrequentRecipes() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getFrequentRecipes()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFavoriteRecipes() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getFavoriteRecipes()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getAllRecipesPreview() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getAllRecipesPreview()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFrequentRecipesPreview() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getFrequentRecipesPreview()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFavoriteRecipesPreview() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getFavoriteRecipesPreview()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFlowById(recipeId: Int) = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getFlowableById(recipeId)
            .map {
                getRecipe(it)
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getRecipeById(recipeId: Int) = withContext(Dispatchers.IO) {
        val recipe = dbInstance.recipeDao().getById(recipeId)
        getRecipe(recipe)
    }

    private fun getRecipe(recipe: RecipeEntity): Recipe {
        val ingredientList = getIngredients(recipe.ingredientsList)
        val category = dbInstance.recipeCategoryDao().getById(recipe.categoryId)

        return Recipe(
            recipe.id,
            recipe.name,
            recipe.instructions,
            recipe.photoFilename,
            recipe.servingsNum,
            recipe.inFavorites,
            recipe.lastCooked,
            recipe.cookCount,
            ingredientList,
            category
        )
    }

    private fun getIngredients(ingredientList: List<Ingredient>): List<IngredientWithMeta> =
        ingredientList.map {
            val product = dbInstance.productDao().getById(it.productId)
            val amountUnit = dbInstance.productUnitsDao().getById(it.amountUnitId)
            IngredientWithMeta(product, it.amount, amountUnit)
        }

    suspend fun removeRecipe(recipe: RecipeEntity) = withContext(Dispatchers.IO) {
        removeRecipeById(recipe.id!!)
    }

    suspend fun removeRecipeById(id: Int) = withContext(Dispatchers.IO) {
        val recipe = dbInstance.recipeDao().getById(id)
        removeIngredients(recipe.ingredientsList)
        removeCategory(recipe.categoryId)
        if (recipe.photoFilename != null) {
            val uri = photoGateway.getUriForPhoto(recipe.photoFilename!!)
            photoGateway.removePhoto(uri)
        }
        dbInstance.recipeDao().deleteById(recipe.id!!)
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

    private fun removeCategory(categoryId: Int) {
        val category = dbInstance.recipeCategoryDao().getById(categoryId)
        // If this is the only recipe where this category is used - delete it
        if (category.referenceCount == 1) {
            if (categoryId != recipePreferences.getValue(RecipePreferences.FIELD_NO_CATEGORY,1)) {
                dbInstance.recipeCategoryDao().delete(category)
            } else {
                dbInstance.recipeCategoryDao().decreaseUsages(categoryId)
            }
        } else {
            dbInstance.recipeCategoryDao().decreaseUsages(categoryId)
        }
    }

    suspend fun addRecipe(recipe: Recipe) = withContext(Dispatchers.IO) {
        val convertedIngredients = processIngredients(recipe.ingredientsList)
        processCategory(recipe.category)
        dbInstance.recipeDao().insert(
            RecipeEntity(
                recipe.id,
                recipe.name,
                recipe.description,
                recipe.photoFilename,
                recipe.servingsNum,
                recipe.inFavorites,
                recipe.lastCooked,
                recipe.cookCount,
                convertedIngredients,
                recipe.category.id!!
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

    private fun processCategory(category: RecipeCategoryEntity?) {
        if (category != null) {
            // Create category if it is not exists
            if (category.id == null) {
                val newId = dbInstance.recipeCategoryDao().insert(category)
                category.id = newId.toInt()
            }
            // Increase category usages
            dbInstance.recipeCategoryDao().increaseUsages(category.id!!)
        }
    }
}
