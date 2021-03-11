package pw.prsk.goodfood.viewmodels

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.*
import pw.prsk.goodfood.data.local.RecipePreferences
import pw.prsk.goodfood.repository.RecipeCategoryRepository
import pw.prsk.goodfood.repository.RecipeRepository
import pw.prsk.goodfood.repository.ProductRepository
import pw.prsk.goodfood.repository.ProductUnitsRepository
import pw.prsk.goodfood.utils.SingleLiveEvent
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class EditRecipeViewModel : ViewModel() {
    @Inject lateinit var productRepository: ProductRepository
    @Inject lateinit var productUnitsRepository: ProductUnitsRepository
    @Inject lateinit var recipeRepository: RecipeRepository
    @Inject lateinit var recipeCategoryRepository: RecipeCategoryRepository
    @Inject lateinit var photoGateway: PhotoGateway
    @Inject lateinit var recipePreferences: RecipePreferences

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
    val recipeCategories: MutableLiveData<List<RecipeCategory>> by lazy {
        MutableLiveData<List<RecipeCategory>>()
    }

    private val photoDrawable = MutableLiveData<Drawable?>()
    val photo: LiveData<Drawable?>
        get() = photoDrawable
    val photoStatus: Boolean
        get() = photoDrawable.value != null
    private var photoUri: Uri? = null
    private var photoFilename: String? = null
    private var photoFromCamera: Boolean = false

    val saveStatus: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun setPhotoUri(photo: Uri) {
        photoUri = photo
        loadPhoto()
    }

    fun removePhoto() {
        if (photoFromCamera) {
            photoGateway.removePhoto(photoUri!!)
            photoFromCamera = false
        }
        photoDrawable.value = null
    }

    private fun loadPhoto() {
        viewModelScope.launch {
            photoDrawable.value = photoGateway.loadPhoto(photoUri!!)
        }
    }

    fun getPhotoUri(): Uri {
        photoFromCamera = true
        photoFilename = photoGateway.createNewPhotoFile()
        photoUri = photoGateway.getUriForPhoto(photoFilename!!)
        return photoUri!!
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

    fun loadCategories() {
        viewModelScope.launch {
            recipeCategories.value = recipeCategoryRepository.getCategories()
        }
    }

    fun addIngredient(item: IngredientWithMeta) {
        ingredientsList.add(item)
        ingredients.value = ingredientsList
    }

    fun saveRecipe(name: String, description: String?, servingsCount: Int, selectedCategory: RecipeCategory?) {
        viewModelScope.launch {
            // Copy photo to app folder if it was picked
            if (!photoFromCamera && photoStatus) {
                photoFilename = photoGateway.createNewPhotoFile()
                val internalUri = photoGateway.getUriForPhoto(photoFilename!!)
                val newPhoto = photoGateway.copyPhoto(photoUri!!, internalUri)
                Log.d(TAG, "Filename where chosen photo will be copied: '${newPhoto.toString()}'.")
            }

            val category = selectedCategory
                ?: RecipeCategory(recipePreferences.getValue(RecipePreferences.FIELD_NO_CATEGORY, 0), "")

            val recipe = RecipeWithMeta(
                null,
                name,
                description,
                photoFilename,
                servingsCount,
                false,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.systemDefault()),
                0,
                ingredientsList,
                category
            )
            recipeRepository.addRecipe(recipe)

            photoFromCamera = false // Prevent photo deletion on exit
            saveStatus.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        removePhoto()
    }

    companion object {
        private const val TAG = "EditMealViewModel"
    }
}