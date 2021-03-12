package pw.prsk.goodfood.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.IngredientWithMeta
import pw.prsk.goodfood.data.PhotoGateway
import pw.prsk.goodfood.data.RecipeWithMeta
import pw.prsk.goodfood.repository.RecipeRepository
import java.time.LocalDateTime
import javax.inject.Inject

class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val photoGateway: PhotoGateway
): ViewModel() {
    private val _recipe by lazy {
        MutableLiveData<RecipeWithMeta>()
    }
    val recipe: LiveData<RecipeWithMeta>
        get() = _recipe

    private val _recipePhoto by lazy {
        MutableLiveData<Bitmap?>()
    }
    val recipePhoto: LiveData<Bitmap?>
        get() = _recipePhoto

    private val _recipeIngredients by lazy {
        MutableLiveData<List<IngredientWithMeta>>()
    }
    val ingredientsList: LiveData<List<IngredientWithMeta>>
        get() = _recipeIngredients

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            _recipe.value = recipe
            if (recipe.photoFilename != null) {
                _recipePhoto.value = photoGateway.loadScaledPhoto(
                    photoGateway.getUriForPhoto(recipe.photoFilename!!),
                    200,
                    200
                )
            }
            _recipeIngredients.value = recipe.ingredientsList
        }
    }

    fun markAsCooked() {
        viewModelScope.launch {
            val currentTime = LocalDateTime.now()
            recipeRepository.markAsCooked(recipe.value?.id!!, currentTime)
        }
    }
}
