package ru.cookedapp.cooked.ui.recipesOverview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.repository.RecipeRepository
import ru.cookedapp.storage.entity.Recipe


class RecipesOverviewViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    val allRecipes by lazy {
        MutableLiveData<List<Recipe>>()
    }
    val favoriteRecipes by lazy {
        MutableLiveData<List<Recipe>>()
    }
    val frequentRecipes by lazy {
        MutableLiveData<List<Recipe>>()
    }

    val isDataPresence by lazy {
        MutableLiveData(true)
    }

    init {
        viewModelScope.launch {
            recipeRepository.getAllRecipesPreview()
                .onEach { allRecipes.postValue(it) }
                .launchIn(this)

            recipeRepository.getFavoriteRecipesPreview()
                .onEach { favoriteRecipes.postValue(it) }
                .launchIn(this)

            recipeRepository.getFrequentRecipesPreview()
                .onEach { frequentRecipes.postValue(it) }
                .launchIn(this)

            recipeRepository.isDatabaseEmpty()
                .onEach { isDataPresence.value = it }
                .launchIn(this)
        }
    }

    fun changeFavoriteState(recipeId: Long, state: Boolean) {
        viewModelScope.launch {
            recipeRepository.changeFavoritesMark(recipeId, state)
        }
    }
}
