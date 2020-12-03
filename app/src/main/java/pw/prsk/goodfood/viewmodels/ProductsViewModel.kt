package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.repository.ProductRepository
import pw.prsk.goodfood.utils.ItemTouchHelperAction

class ProductsViewModel : ViewModel(), ItemTouchHelperAction {
    private var repository: ProductRepository? = null

    val productsList: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository!!.addProduct(product)
            loadProductsList()
        }
    }

    private fun loadProductsList() {
        viewModelScope.launch {
            productsList.postValue(repository!!.getProducts())
        }
    }

    fun injectRepository(repository: ProductRepository) {
        this.repository = repository
        loadProductsList()
    }

    override fun onCleared() {
        repository = null
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModelScope.launch {
            val item = productsList.value?.get(position)
            repository?.removeProduct(item!!)
            loadProductsList()
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}