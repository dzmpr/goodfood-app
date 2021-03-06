package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.utils.ItemTouchHelperAction
import pw.prsk.goodfood.data.Recipe
import pw.prsk.goodfood.repository.RecipeCategoryRepository
import pw.prsk.goodfood.repository.RecipeRepository
import pw.prsk.goodfood.utils.SingleLiveEvent
import javax.inject.Inject


class RecipesOverviewViewModel : ViewModel(), ItemTouchHelperAction {
    val recipeList: MutableLiveData<List<Recipe>> by lazy {
        MutableLiveData<List<Recipe>>()
    }

    val deleteSnack = SingleLiveEvent<String>()

    @Inject lateinit var recipeRepository: RecipeRepository
    @Inject lateinit var recipeCategoryRepository: RecipeCategoryRepository

    fun addMeal(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.addRecipe(recipe)
            loadMealsList()
        }
    }

    fun loadMealsList() {
        viewModelScope.launch {
            recipeList.postValue(recipeRepository.getRecipes())
        }
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModelScope.launch {
            val item = recipeList.value?.get(position)
            deleteSnack.value = item?.name // Show snackbar with deleted item name
            recipeRepository.removeRecipe(item!!)
            loadMealsList()
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}