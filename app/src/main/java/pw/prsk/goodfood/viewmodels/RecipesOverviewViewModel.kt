package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.RecipeWithMeta
import pw.prsk.goodfood.repository.RecipeRepository
import javax.inject.Inject


class RecipesOverviewViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    val allRecipes by lazy {
        MutableLiveData<List<RecipeWithMeta>>()
    }
    val favoriteRecipes by lazy {
        MutableLiveData<List<RecipeWithMeta>>()
    }
    val frequentRecipes by lazy {
        MutableLiveData<List<RecipeWithMeta>>()
    }

    init {
        viewModelScope.launch {
            recipeRepository.getAllRecipes()
                .onEach { allRecipes.postValue(it) }
                .launchIn(this)

            recipeRepository.getFavoriteRecipes()
                .onEach { favoriteRecipes.postValue(it) }
                .launchIn(this)

            recipeRepository.getFrequentRecipes()
                .onEach { frequentRecipes.postValue(it) }
                .launchIn(this)
        }
    }
}