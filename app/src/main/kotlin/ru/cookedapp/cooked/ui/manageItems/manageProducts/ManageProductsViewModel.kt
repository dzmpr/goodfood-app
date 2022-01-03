package ru.cookedapp.cooked.ui.manageItems.manageProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.common.baseList.data.Items
import ru.cookedapp.cooked.data.repository.ProductRepository
import ru.cookedapp.cooked.ui.manageItems.ManageItemsProvider

class ManageProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val itemsProvider: ManageItemsProvider,
) : ViewModel() {

    private val _productsList: MutableLiveData<List<Items>> by lazy {
        MutableLiveData<List<Items>>()
    }
    val productsList: LiveData<List<Items>>
        get() = _productsList

    init {
        viewModelScope.launch {
            productRepository.getProductsFlow().onEach { products ->
                _productsList.value = itemsProvider.generateRowsFromProducts(products)
            }.launchIn(this)
        }
    }

    fun onProductRenamed(id: Long, name: String) {
        viewModelScope.launch {
            productRepository.renameProduct(id, name)
        }
    }
}
