package ru.cookedapp.cooked.data.repository

import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.data.db.dao.ProductDao
import ru.cookedapp.cooked.data.db.dao.ProductUnitDao
import ru.cookedapp.cooked.data.db.dao.RecipeCategoryDao
import ru.cookedapp.cooked.data.db.dao.RecipeDao
import ru.cookedapp.cooked.data.db.entity.Ingredient
import ru.cookedapp.cooked.data.db.entity.IngredientWithMeta
import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity
import ru.cookedapp.cooked.data.db.entity.RecipeEntity
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.data.prefs.RecipePreferences

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val recipeCategoryDao: RecipeCategoryDao,
    private val productDao: ProductDao,
    private val productUnitDao: ProductUnitDao,
    private val photoGateway: PhotoGateway,
    private val recipePreferences: RecipePreferences
) {
    suspend fun markAsCooked(id: Long, date: LocalDateTime) = withContext(Dispatchers.IO) {
        recipeDao.markAsCooked(id, date)
    }

    suspend fun changeFavoritesMark(id: Long, state: Boolean) = withContext(Dispatchers.IO) {
        recipeDao.changeFavoriteMark(id, state)
    }

    suspend fun isDatabaseEmpty() = withContext(Dispatchers.IO) {
        recipeDao.isDatabaseEmpty().map { it != 0 }
    }

    suspend fun getAllRecipes() = withContext(Dispatchers.IO) {
        recipeDao.getAllRecipes()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFrequentRecipes() = withContext(Dispatchers.IO) {
        recipeDao.getFrequentRecipes()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFavoriteRecipes() = withContext(Dispatchers.IO) {
        recipeDao.getFavoriteRecipes()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getAllRecipesPreview() = withContext(Dispatchers.IO) {
        recipeDao.getAllRecipesPreview()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFrequentRecipesPreview() = withContext(Dispatchers.IO) {
        recipeDao.getFrequentRecipesPreview()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFavoriteRecipesPreview() = withContext(Dispatchers.IO) {
        recipeDao.getFavoriteRecipesPreview()
            .map {
                it.map { recipe ->
                    getRecipe(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFlowById(recipeId: Long) = withContext(Dispatchers.IO) {
        recipeDao.getFlowableById(recipeId).map {
            getRecipe(it)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRecipeById(recipeId: Long) = withContext(Dispatchers.IO) {
        val recipe = recipeDao.getById(recipeId)
        getRecipe(recipe)
    }

    private fun getRecipe(recipe: RecipeEntity): Recipe {
        val ingredientList = getIngredients(recipe.ingredientsList)
        val category = recipeCategoryDao.getById(recipe.categoryId)

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
            val product = productDao.getById(it.productId)
            val amountUnit = productUnitDao.getById(it.amountUnitId)
            IngredientWithMeta(product, it.amount, amountUnit)
        }

    suspend fun removeRecipe(recipe: RecipeEntity) = withContext(Dispatchers.IO) {
        removeRecipeById(recipe.id)
    }

    suspend fun removeRecipeById(id: Long) = withContext(Dispatchers.IO) {
        val recipe = recipeDao.getById(id)
        removeIngredients(recipe.ingredientsList)
        removeCategory(recipe.categoryId)
        if (recipe.photoFilename != null) {
            val uri = photoGateway.getUriForPhoto(recipe.photoFilename!!)
            photoGateway.removePhoto(uri)
        }
        recipeDao.deleteById(recipe.id)
    }

    private fun removeIngredients(ingredients: List<Ingredient>) {
        ingredients.forEach {
            // Get product for each ingredient
            val product = productDao.getById(it.productId)
            // If this is the only recipe where this product is used - delete it
            if (product.referenceCount == 1) {
                productDao.delete(product)
            } else {
                productDao.decreaseUsages(it.productId)
            }
        }
    }

    private fun removeCategory(categoryId: Long) {
        val category = recipeCategoryDao.getById(categoryId)
        // If this is the only recipe where this category is used - delete it
        if (category.referenceCount == 1) {
            if (categoryId != recipePreferences.recipeNoCategoryId) {
                recipeCategoryDao.delete(category)
            } else {
                recipeCategoryDao.decreaseUsages(categoryId)
            }
        } else {
            recipeCategoryDao.decreaseUsages(categoryId)
        }
    }

    suspend fun addRecipe(recipe: Recipe) = withContext(Dispatchers.IO) {
        val convertedIngredients = processIngredients(recipe.ingredientsList)
        val recipeCategory = processCategory(recipe.category)
        recipeDao.insert(
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
                recipeCategory.id,
            )
        )
    }

    private fun processIngredients(
        ingredients: List<IngredientWithMeta>,
    ): List<Ingredient> = ingredients.map { ingredient ->
        processIngredient(ingredient)
    }

    private fun processIngredient(ingredient: IngredientWithMeta): Ingredient {
        // Create product if it is not exists
        val productId = if (ingredient.product.isNew()) {
            productDao.insert(ingredient.product)
        } else {
            ingredient.product.id
        }
        // Increase product usage count
        productDao.increaseUsages(productId)
        return Ingredient(productId, ingredient.amount, ingredient.unit.id)
    }

    private fun processCategory(category: RecipeCategoryEntity): RecipeCategoryEntity {
        // Create category if it is not exists
        val categoryId = if (category.isNew()) {
            recipeCategoryDao.insert(category)
        } else {
            category.id
        }
        // Increase category usages
        recipeCategoryDao.increaseUsages(categoryId)

        return category.copy(id = categoryId)
    }
}
