package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.repository.ProductCategoryRepository
import pw.prsk.goodfood.repository.ProductRepository
import pw.prsk.goodfood.utils.ItemTouchHelperAction
import pw.prsk.goodfood.utils.SingleLiveEvent
import javax.inject.Inject

class ProductsViewModel : ViewModel(), ItemTouchHelperAction {
    @Inject lateinit var productRepository: ProductRepository
    @Inject lateinit var productCategoryRepository: ProductCategoryRepository

    val deleteSnack = SingleLiveEvent<String>()

    val productsList: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productRepository.addProduct(product)
            loadProductsList()
        }
    }

    fun loadProductsList() {
        viewModelScope.launch {
            productsList.postValue(productRepository.getProducts())
        }
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModelScope.launch {
            val item = productsList.value?.get(position)
            deleteSnack.value = item?.name // Show snackbar with deleted item name
            productRepository.removeProduct(item!!)
            loadProductsList()
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}