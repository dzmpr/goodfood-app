package ru.cookedapp.cooked.ui.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity
import ru.cookedapp.cooked.data.repository.RecipeRepository
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListItem
import ru.cookedapp.cooked.utils.ItemTouchHelperAction
import ru.cookedapp.cooked.utils.SingleLiveEvent
import ru.cookedapp.cooked.utils.listBase.data.Items

class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val itemsProvider: RecipeListItemsProvider,
) : ViewModel(), ItemTouchHelperAction {

    private lateinit var recipes: Flow<List<Recipe>>

    private val filteredRecipes = MutableLiveData<List<Items>>()
    val recipeList: LiveData<List<Items>>
        get() = filteredRecipes

    private val recipeCategories = MutableLiveData<Set<RecipeCategoryEntity>>()
    val categorySet: LiveData<Set<RecipeCategoryEntity>>
        get() = recipeCategories

    private val selectedCategory = MutableStateFlow(0)

    val deleteSnack = SingleLiveEvent<String>()

    fun setListSource(sourceKey: Int) {
        viewModelScope.launch {
            recipes = when (sourceKey) {
                RecipeListFragment.LIST_TYPE_ALL -> recipeRepository.getAllRecipes()
                RecipeListFragment.LIST_TYPE_FAVORITES -> recipeRepository.getFavoriteRecipes()
                RecipeListFragment.LIST_TYPE_FREQUENT -> recipeRepository.getFrequentRecipes()
                else -> throw IllegalStateException("Unknown list type $sourceKey.")
            }
            loadList()
        }
    }

    private fun loadList() {
        viewModelScope.launch {
            recipes.onEach { recipes ->
                val categorySet = recipes.asSequence().map { it.category }.toSet()
                if (recipeCategories.value != categorySet) {
                    recipeCategories.postValue(categorySet)
                }
            }.flowOn(Dispatchers.Default)
            .combine(selectedCategory) { recipes, selectedCategory ->
                recipes to selectedCategory
            }.map { (recipes, selectedCategory) ->
                if (selectedCategory == 0) {
                    recipes
                } else {
                    recipes.filter { recipe ->
                        recipe.category.id == selectedCategory
                    }
                }
            }.flowOn(Dispatchers.IO)
            .onEach { recipes ->
                filteredRecipes.postValue(itemsProvider.generateRows(recipes))
            }.launchIn(this)
        }
    }

    fun onCategorySelected(categoryId: Int) {
        selectedCategory.value = categoryId
    }

    fun changeFavoriteState(recipeId: Int, state: Boolean) {
        viewModelScope.launch {
            recipeRepository.changeFavoritesMark(recipeId, state)
        }
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModelScope.launch {
            val item = recipeList.value?.get(position) as RecipeListItem
            deleteSnack.value = item.recipeName // Show snackbar with deleted item name
            recipeRepository.removeRecipeById(item.id.toInt())
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}
