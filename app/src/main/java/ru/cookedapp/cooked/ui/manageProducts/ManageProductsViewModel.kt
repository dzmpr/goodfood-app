package ru.cookedapp.cooked.ui.manageProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.db.entity.ProductEntity
import ru.cookedapp.cooked.data.repository.ProductRepository

class ManageProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _productsList : MutableLiveData<List<ProductEntity>> by lazy {
        MutableLiveData<List<ProductEntity>>()
    }
    val productsList: LiveData<List<ProductEntity>>
        get() = _productsList

    init {
        viewModelScope.launch {
            productRepository.getProductsFlow()
                .onEach {
                    _productsList.value = it
                }
                .launchIn(this)
        }
    }

    fun onProductRename(id: Int, name: String) {
        viewModelScope.launch {
            productRepository.renameProduct(id, name)
        }
    }
}
