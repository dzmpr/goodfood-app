package pw.prsk.goodfood.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.local.db.entity.Product
import pw.prsk.goodfood.data.repository.ProductRepository
import javax.inject.Inject

class ManageProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _productsList : MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }
    val productsList: LiveData<List<Product>>
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