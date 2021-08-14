package ru.cookedapp.cooked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.data.db.dao.CartDao
import ru.cookedapp.cooked.data.db.dao.ProductDao
import ru.cookedapp.cooked.data.db.dao.ProductUnitDao
import ru.cookedapp.cooked.data.db.dao.RecipeDao
import ru.cookedapp.cooked.data.db.entity.CartItem
import ru.cookedapp.cooked.data.db.entity.CartItemEntity
import ru.cookedapp.cooked.data.db.entity.Ingredient

class CartRepository(
    private val recipeDao: RecipeDao,
    private val cartDao: CartDao,
    private val productDao: ProductDao,
    private val productUnitDao: ProductUnitDao
) {
    suspend fun addIngredientsToCart(recipeId: Int, multiplier: Float) = withContext(Dispatchers.IO) {
        val recipe = recipeDao.getById(recipeId)
        val ingredientsList = recipe.ingredientsList
        ingredientsList.forEach {
            addProductToCart(it, multiplier)
        }
    }

    private fun addProductToCart(ingredient: Ingredient, multiplier: Float) {
        val cartItem = cartDao.getCartByProductIdAndUnitId(ingredient.productId, ingredient.amountUnitId)
        if (cartItem == null) {
            cartDao.insert(
                CartItemEntity(
                    productId = ingredient.productId,
                    amount = ingredient.amount * multiplier,
                    unitId = ingredient.amountUnitId
                )
            )
        } else {
            cartItem.amount += (ingredient.amount * multiplier)
            cartDao.update(cartItem)
        }
    }

    suspend fun changeBoughtState(id: Int, state: Boolean) = withContext(Dispatchers.IO) {
        cartDao.changeBoughtState(id, state)
    }

    suspend fun loadCartList() = withContext(Dispatchers.IO) {
        cartDao.getCartList()
            .map { list ->
                list.map {
                    getCartItem(it)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    private fun getCartItem(item: CartItemEntity): CartItem {
        val product = productDao.getById(item.productId)
        val unit = productUnitDao.getById(item.unitId)
        return CartItem(
            item.id,
            item.isBought,
            product,
            item.amount,
            unit
        )
    }

    suspend fun removeFromCart(id: Int) = withContext(Dispatchers.IO) {
        cartDao.removeItem(id)
    }

    suspend fun removePurchased() = withContext(Dispatchers.IO) {
        cartDao.removePurchasedItems()
    }

    suspend fun clearCart() = withContext(Dispatchers.IO) {
        cartDao.clearCart()
    }
}
