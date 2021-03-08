package pw.prsk.goodfood.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.*

class RecipeRepository(
    private val dbInstance: AppDatabase,
    private val photoGateway: PhotoGateway
) {
    suspend fun addRecipe(recipe: Recipe) = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().insert(recipe)
    }

    suspend fun changeFavoritesMark(id: Int, state: Boolean) = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().changeFavoriteMark(id, state)
    }

    suspend fun getAllRecipes() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getAllRecipes()
            .map {
                it.map { recipe ->
                    getRecipeWithMeta(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFrequentRecipes() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getFrequentRecipes()
            .map {
                it.map { recipe ->
                    getRecipeWithMeta(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getFavoriteRecipes() = withContext(Dispatchers.IO) {
        dbInstance.recipeDao().getFavoriteRecipes()
            .map {
                it.map { recipe ->
                    getRecipeWithMeta(recipe)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    private fun getRecipeWithMeta(recipe: Recipe): RecipeWithMeta {
        val ingredientList = getIngredients(recipe.ingredientsList)
        val category = if (recipe.category_id != null) {
            dbInstance.recipeCategoryDao().getById(recipe.category_id!!)
        } else {
            null
        }
        return RecipeWithMeta(
            recipe.id,
            recipe.name,
            recipe.description,
            recipe.photoFilename,
            recipe.servingsNum,
            recipe.inFavorites,
            recipe.last_eaten,
            recipe.eat_count,
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

    suspend fun removeRecipe(recipe: Recipe) = withContext(Dispatchers.IO) {
        removeRecipeById(recipe.id!!)
    }

    suspend fun removeRecipeById(id: Int) = withContext(Dispatchers.IO) {
        val recipe = dbInstance.recipeDao().getById(id)
        removeIngredients(recipe.ingredientsList)
        removeCategory(recipe.category_id)
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

    private fun removeCategory(category_id: Int?) {
        if (category_id != null) {
            val category = dbInstance.recipeCategoryDao().getById(category_id)
            // If this is the only recipe where this category is used - delete it
            if (category.referenceCount == 1) {
                dbInstance.recipeCategoryDao().delete(category)
            } else {
                dbInstance.recipeCategoryDao().decreaseUsages(category_id)
            }
        }
    }

    suspend fun addRecipe(recipe: RecipeWithMeta) = withContext(Dispatchers.IO) {
        val convertedIngredients = processIngredients(recipe.ingredientsList)
        processCategory(recipe.category)
        dbInstance.recipeDao().insert(
            Recipe(
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

    private fun processCategory(category: RecipeCategory?) {
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