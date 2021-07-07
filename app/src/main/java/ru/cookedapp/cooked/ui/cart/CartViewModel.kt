package ru.cookedapp.cooked.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.db.entity.CartItemWithMeta
import ru.cookedapp.cooked.data.repository.CartRepository
import ru.cookedapp.cooked.utils.ItemTouchHelperAction
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
): ViewModel(), ItemTouchHelperAction {
    private val cart by lazy{
        MutableLiveData<List<CartItemWithMeta>>()
    }
    val cartList: LiveData<List<CartItemWithMeta>>
        get() = cart

    init {
        viewModelScope.launch {
            cartRepository.loadCartList()
                .onEach {
                    cart.value = it
                }
                .launchIn(this)
        }
    }

    fun changeBoughtState(id: Int, state: Boolean) {
        viewModelScope.launch {
            cartRepository.changeBoughtState(id, state)
        }
    }

    fun onRemovePurchasedClick() {
        viewModelScope.launch {
            cartRepository.removePurchased()
        }
    }

    fun onClearCartClick() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModelScope.launch {
            cart.value?.let {
                cartRepository.removeFromCart(it[position].id!!)
            }
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}
