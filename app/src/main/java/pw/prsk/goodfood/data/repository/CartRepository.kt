package pw.prsk.goodfood.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.local.db.AppDatabase
import pw.prsk.goodfood.data.local.db.entity.CartItem
import pw.prsk.goodfood.data.local.db.entity.CartItemWithMeta
import pw.prsk.goodfood.data.local.db.entity.Ingredient

class CartRepository(private val dbInstance: AppDatabase) {
    suspend fun addIngredientsToCart(recipeId: Int, multiplier: Float) = withContext(Dispatchers.IO) {
        val recipe = dbInstance.recipeDao().getById(recipeId)
        val ingredientsList = recipe.ingredientsList
        ingredientsList.forEach {
            addProductToCart(it, multiplier)
        }
    }

    private fun addProductToCart(ingredient: Ingredient, multiplier: Float) {
        val cartItem = dbInstance.cartDao().getCartByProductIdAndUnitId(ingredient.productId, ingredient.amountUnitId)
        if (cartItem == null) {
            dbInstance.cartDao().insert(
                CartItem(
                    productId = ingredient.productId,
                    amount = ingredient.amount * multiplier,
                    unitId = ingredient.amountUnitId
                )
            )
        } else {
            cartItem.amount += (ingredient.amount * multiplier)
            dbInstance.cartDao().update(cartItem)
        }
    }

    suspend fun changeBoughtState(id: Int, state: Boolean) = withContext(Dispatchers.IO) {
        dbInstance.cartDao().changeBoughtState(id, state)
    }

    suspend fun loadCartList() = withContext(Dispatchers.IO) {
        dbInstance.cartDao().getCartList()
            .map { list ->
                list.map {
                    getItemWithMeta(it)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    private fun getItemWithMeta(item: CartItem): CartItemWithMeta {
        val product = dbInstance.productDao().getById(item.productId)
        val unit = dbInstance.productUnitsDao().getById(item.unitId)
        return CartItemWithMeta(
            item.id,
            item.isBought,
            product,
            item.amount,
            unit
        )
    }

    suspend fun removeFromCart(id: Int) = withContext(Dispatchers.IO) {
        dbInstance.cartDao().removeItem(id)
    }
}