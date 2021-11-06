package ru.cookedapp.cooked.ui.manageItems.manageProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.repository.ProductRepository
import ru.cookedapp.cooked.ui.manageItems.ManageItemsRowProvider
import ru.cookedapp.cooked.ui.manageItems.data.ManageItemsViewType
import ru.cookedapp.cooked.utils.listBase.data.Item

class ManageProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val rowProvider = ManageItemsRowProvider()

    private val _productsList: MutableLiveData<List<Item<ManageItemsViewType>>> by lazy {
        MutableLiveData<List<Item<ManageItemsViewType>>>()
    }
    val productsList: LiveData<List<Item<ManageItemsViewType>>>
        get() = _productsList

    init {
        viewModelScope.launch {
            productRepository.getProductsFlow().onEach { products ->
                _productsList.value = rowProvider.generateRowsFromProducts(products)
            }.launchIn(this)
        }
    }

    fun onProductRenamed(id: Long, name: String) {
        viewModelScope.launch {
            productRepository.renameProduct(id.toInt(), name)
        }
    }
}
