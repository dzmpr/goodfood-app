package ru.cookedapp.cooked.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.repository.CartRepository
import ru.cookedapp.cooked.ui.cart.data.CartViewType
import ru.cookedapp.cooked.utils.ItemTouchHelperAction
import ru.cookedapp.cooked.utils.listBase.data.Item

class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
): ViewModel(), ItemTouchHelperAction {

    private val cart by lazy {
        MutableLiveData<List<Item<CartViewType>>>()
    }
    val cartList: LiveData<List<Item<CartViewType>>>
        get() = cart

    private val cartRowProvider = CartRowProvider()

    init {
        viewModelScope.launch {
            cartRepository.loadCartList().onEach {
                cart.value = cartRowProvider.generateItems(it)
            }.launchIn(this)
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
                cartRepository.removeFromCart(it[position].id.toInt())
            }
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}
