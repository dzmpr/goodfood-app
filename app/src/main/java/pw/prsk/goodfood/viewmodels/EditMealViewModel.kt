package pw.prsk.goodfood.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.*
import pw.prsk.goodfood.repository.MealRepository
import pw.prsk.goodfood.repository.ProductRepository
import pw.prsk.goodfood.repository.ProductUnitsRepository
import pw.prsk.goodfood.utils.SingleLiveEvent
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class EditMealViewModel : ViewModel() {
    @Inject lateinit var productRepository: ProductRepository
    @Inject lateinit var productUnitsRepository: ProductUnitsRepository
    @Inject lateinit var mealRepository: MealRepository
    @Inject lateinit var photoGateway: PhotoGateway

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

    private val photoUri = MutableLiveData<Uri?>()
    val photo: LiveData<Uri?>
        get() = photoUri
    val photoStatus: Boolean
        get() = photoUri.value != null
    private var photoWasTaken: Boolean = false

    val saveStatus: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun setPhotoUri(photo: Uri) {
        photoUri.value = photo
    }

    fun removePhoto() {
        if (photoWasTaken) {
            photoGateway.removePhoto(photoUri.value!!)
            photoWasTaken = false
        }
        photoUri.value = null
    }

    fun getPhotoUri(): Uri? {
        photoWasTaken = true
        return photoGateway.createNewPhotoUri()
    }

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

    fun saveRecipe(name: String, description: String) {
        viewModelScope.launch {
            val ingredients = handleIngredients()
            val recipe = Meal(
                null,
                name,
                description,
                photoUri.value.toString(),
                0,
                false,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.systemDefault()),
                0,
                ingredients,
                0
            )
            mealRepository.addMeal(recipe)

            photoWasTaken = false // Prevent photo deletion on exit
            saveStatus.value = true
        }
    }

    private suspend fun handleIngredients(): List<Ingredient> = withContext(Dispatchers.Default) {
        for (ingredient in ingredientsList) {
            handleIngredient(ingredient)
        }
        ingredientsList.map {
            Ingredient(it.product.id!!, it.amount, it.unit.id!!)
        }
    }

    private suspend fun handleIngredient(ingredient: IngredientWithMeta) {
        // Create product if it is not exists
        if (ingredient.product.id == null) {
            val newId = productRepository.addProduct(ingredient.product)
            ingredient.product.id = newId.toInt()
        }
        // Increase product usage count
        productRepository.increaseUsage(ingredient.product.id!!)
    }

    override fun onCleared() {
        super.onCleared()
        removePhoto()
    }
}