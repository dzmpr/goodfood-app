package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.repository.ProductRepository

class ProductsViewModel : ViewModel() {
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
}