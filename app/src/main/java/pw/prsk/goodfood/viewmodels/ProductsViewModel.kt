package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.repository.ProductRepository

class ProductsViewModel: ViewModel() {
    private var repository: ProductRepository? = null

    val productsList: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    fun loadProductsList() {
        viewModelScope.launch {
            productsList.postValue(repository?.getProducts())
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