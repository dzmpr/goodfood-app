package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import pw.prsk.goodfood.data.RecipeCategory
import pw.prsk.goodfood.data.RecipeWithMeta
import pw.prsk.goodfood.repository.RecipeRepository
import pw.prsk.goodfood.utils.ItemTouchHelperAction
import pw.prsk.goodfood.utils.SingleLiveEvent
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

    init {
        viewModelScope.launch {
            recipes = recipeRepository.getAllRecipes()

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

    val deleteSnack = SingleLiveEvent<String>()

    fun onCategorySelected(categoryId: Int) {
        selectedCategory.value = categoryId
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