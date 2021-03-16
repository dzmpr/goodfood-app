package pw.prsk.goodfood.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import pw.prsk.goodfood.presentation.ui.RecipeListFragment
import pw.prsk.goodfood.data.local.db.entity.RecipeCategory
import pw.prsk.goodfood.data.local.db.entity.RecipeWithMeta
import pw.prsk.goodfood.data.repository.RecipeRepository
import pw.prsk.goodfood.utils.ItemTouchHelperAction
import pw.prsk.goodfood.utils.SingleLiveEvent
import java.lang.IllegalStateException
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel(), ItemTouchHelperAction {
    private lateinit var recipes: Flow<List<RecipeWithMeta>>
    private val filteredRecipes = MutableLiveData<List<RecipeWithMeta>>()
    val recipeList: LiveData<List<RecipeWithMeta>>
        get() = filteredRecipes
    private val recipeCategories = MutableLiveData<Set<RecipeCategory>>()
    val categorySet: LiveData<Set<RecipeCategory>>
        get() = recipeCategories

    private val selectedCategory = MutableStateFlow(0)

    val deleteSnack = SingleLiveEvent<String>()

    fun setListSource(sourceKey: Int) {
        viewModelScope.launch {
            recipes = when (sourceKey) {
                RecipeListFragment.LIST_TYPE_ALL -> recipeRepository.getAllRecipes()
                RecipeListFragment.LIST_TYPE_FAVORITES -> recipeRepository.getFavoriteRecipes()
                RecipeListFragment.LIST_TYPE_FREQUENT -> recipeRepository.getFrequentRecipes()
                else -> {
                    throw IllegalStateException("Unknown list type $sourceKey.")
                }
            }
            loadList()
        }
    }

    private fun loadList() {
        viewModelScope.launch {
            recipes
                .onEach {
                    val categorySet = mutableSetOf<RecipeCategory>()
                    it.forEach { recipe ->
                        categorySet.add(recipe.category)
                    }
                    if (recipeCategories.value != categorySet) {
                        recipeCategories.postValue(categorySet)
                    }
                }
                .flowOn(Dispatchers.Default)
                .combine(selectedCategory) { list, category ->
                    list to category
                }
                .map { pair ->
                    if (pair.second == 0) {
                        pair.first
                    } else {
                        pair.first.filter {
                            it.category.id == pair.second
                        }
                    }
                }
                .flowOn(Dispatchers.IO)
                .onEach {
                    filteredRecipes.postValue(it)
                }
                .launchIn(this)
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
            val item = recipeList.value?.get(position)
            deleteSnack.value = item?.name // Show snackbar with deleted item name
            recipeRepository.removeRecipeById(item!!.id!!)
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}