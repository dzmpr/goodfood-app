package ru.cookedapp.cooked.ui.editRecipe

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.data.repository.ProductRepository
import ru.cookedapp.cooked.data.repository.ProductUnitsRepository
import ru.cookedapp.cooked.data.repository.RecipeCategoryRepository
import ru.cookedapp.cooked.data.repository.RecipeRepository
import ru.cookedapp.cooked.utils.SingleLiveEvent
import ru.cookedapp.storage.entity.IngredientWithMeta
import ru.cookedapp.storage.entity.ProductEntity
import ru.cookedapp.storage.entity.ProductUnitEntity
import ru.cookedapp.storage.entity.Recipe
import ru.cookedapp.storage.entity.RecipeCategoryEntity
import ru.cookedapp.storage.recipePreferences.RecipePreferences

class EditRecipeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val productUnitsRepository: ProductUnitsRepository,
    private val recipeRepository: RecipeRepository,
    private val recipeCategoryRepository: RecipeCategoryRepository,
    private val photoGateway: PhotoGateway,
    private val recipePreferences: RecipePreferences
) : ViewModel() {
    private val ingredientsList: MutableList<IngredientWithMeta> = mutableListOf()

    val ingredients: MutableLiveData<List<IngredientWithMeta>> by lazy {
        MutableLiveData<List<IngredientWithMeta>>(ingredientsList)
    }
    val productsList: MutableLiveData<List<ProductEntity>> by lazy {
        MutableLiveData<List<ProductEntity>>()
    }
    val unitsList: MutableLiveData<List<ProductUnitEntity>> by lazy {
        MutableLiveData<List<ProductUnitEntity>>()
    }
    val recipeCategories: MutableLiveData<List<RecipeCategoryEntity>> by lazy {
        MutableLiveData<List<RecipeCategoryEntity>>()
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

    init {
        viewModelScope.launch {
            productsList.value = productRepository.getProducts()
            unitsList.value = productUnitsRepository.getUnits()
            recipeCategories.value = recipeCategoryRepository.getCategories()
        }
    }

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

    fun addIngredient(item: IngredientWithMeta) {
        ingredientsList.add(item)
        ingredients.value = ingredientsList
    }

    fun saveRecipe(name: String, description: String?, servingsCount: Int, selectedCategory: RecipeCategoryEntity?) {
        viewModelScope.launch {
            // Copy photo to app folder if it was picked
            if (!photoFromCamera && photoStatus) {
                photoFilename = photoGateway.createNewPhotoFile()
                val internalUri = photoGateway.getUriForPhoto(photoFilename!!)
                val newPhoto = photoGateway.copyPhoto(photoUri!!, internalUri)
                Log.d(TAG, "Filename where chosen photo will be copied: '${newPhoto.toString()}'.")
            }

            val category = selectedCategory ?: RecipeCategoryEntity(recipePreferences.recipeNoCategoryId, "")

            val recipe = Recipe(
                id = 0,
                name,
                description,
                photoFilename,
                servingsCount,
                inFavorites = false,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.systemDefault()),
                cookCount = 0,
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
