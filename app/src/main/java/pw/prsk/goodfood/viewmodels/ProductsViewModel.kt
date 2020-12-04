package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.repository.ProductCategoryRepository
import pw.prsk.goodfood.repository.ProductRepository
import pw.prsk.goodfood.utils.ItemTouchHelperAction

class ProductsViewModel : ViewModel(), ItemTouchHelperAction {
    private var productRepository: ProductRepository? = null
    private var productCategoryRepository: ProductCategoryRepository? = null

    val productsList: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productRepository!!.addProduct(product)
            loadProductsList()
        }
    }

    private fun loadProductsList() {
        viewModelScope.launch {
            productsList.postValue(productRepository!!.getProducts())
        }
    }

    fun injectRepository(
        productRepository: ProductRepository,
        productCategoryRepository: ProductCategoryRepository
    ) {
        this.productRepository = productRepository
        this.productCategoryRepository = productCategoryRepository
        loadProductsList()
    }

    override fun onCleared() {
        productRepository = null
        productCategoryRepository = null
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModelScope.launch {
            val item = productsList.value?.get(position)
            productRepository?.removeProduct(item!!)
            loadProductsList()
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}