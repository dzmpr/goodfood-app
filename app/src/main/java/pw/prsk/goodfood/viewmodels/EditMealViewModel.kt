package pw.prsk.goodfood.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.*
import pw.prsk.goodfood.repository.ProductRepository
import pw.prsk.goodfood.repository.ProductUnitsRepository
import javax.inject.Inject

class EditMealViewModel : ViewModel() {
    @Inject lateinit var productRepository: ProductRepository
    @Inject lateinit var productUnitsRepository: ProductUnitsRepository
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
//        val recipe = Meal(
//            null,
//            name,
//            description,
//            photoUri.toString(),
//            0,
//            false,
//            LocalDateTime.now(),
//            0,
//            ingredientsList,
//
//        )
        // TODO: Handle situation when recipe saved and photo shouldn't be deleted
    }

    override fun onCleared() {
        super.onCleared()
        removePhoto()
    }
}