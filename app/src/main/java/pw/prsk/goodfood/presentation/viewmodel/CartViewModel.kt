package pw.prsk.goodfood.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.local.db.entity.CartItemWithMeta
import pw.prsk.goodfood.data.repository.CartRepository
import pw.prsk.goodfood.utils.ItemTouchHelperAction
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