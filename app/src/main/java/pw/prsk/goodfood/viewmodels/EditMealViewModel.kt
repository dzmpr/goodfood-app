package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.*
import pw.prsk.goodfood.repository.ProductRepository
import pw.prsk.goodfood.repository.ProductUnitsRepository
import javax.inject.Inject

class EditMealViewModel : ViewModel() {
    private val ingredientsList: MutableList<IngredientWithMeta> = mutableListOf()

    val ingredients: MutableLiveData<List<IngredientWithMeta>> by lazy {
        MutableLiveData<List<IngredientWithMeta>>(ingredientsList)
    }

    val productsList: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    val unitsList: MutableLiveData<List<ProductUnit>> by lazy {
        MutableLiveData<List<ProductUnit>>()
    }

    @Inject lateinit var productRepository: ProductRepository
    @Inject lateinit var productUnitsRepository: ProductUnitsRepository

    fun loadProducts() {
        viewModelScope.launch {
            productsList.value = productRepository.getProducts()
        }
    }

    fun loadUnits() {
        viewModelScope.launch {
            unitsList.value = productUnitsRepository.getUnits()
        }
    }

    fun addIngredient(item: IngredientWithMeta) {
        ingredientsList.add(item)
        ingredients.value = ingredientsList
    }
}